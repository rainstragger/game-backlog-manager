import { CommonModule, DatePipe, DOCUMENT } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit, computed, effect, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { debounceTime, distinctUntilChanged, finalize } from 'rxjs';
import { GameDetailsOverlayComponent } from '../../components/game-details-overlay/game-details-overlay';
import { Games } from '../../models/games';
import { Progress, PROGRESS_STATUS_OPTIONS, ProgressStatus } from '../../models/progress';
import { GamesService } from '../../services/games.service';
import { ProgressService } from '../../services/progress.service';

@Component({
  selector: 'games',
  imports: [CommonModule, DatePipe, ReactiveFormsModule, GameDetailsOverlayComponent],
  templateUrl: './games.html',
  styleUrl: './games.css',
})
export class GamesComponent implements OnInit, OnDestroy {
  private readonly gamesService = inject(GamesService);
  private readonly progressService = inject(ProgressService);
  private readonly formBuilder = inject(FormBuilder);
  private readonly document = inject(DOCUMENT);

  readonly games = signal<Games[]>([]);
  readonly isLoadingGames = signal(true);
  readonly isSavingGame = signal(false);
  readonly isDeletingGame = signal(false);
  readonly isLoadingProgress = signal(false);
  readonly isSavingProgress = signal(false);
  readonly selectedGame = signal<Games | null>(null);
  readonly progressRecord = signal<Progress | null>(null);
  readonly overlayMode = signal<'details' | 'create' | 'edit' | null>(null);
  readonly statusMessage = signal<string | null>(null);
  readonly errorMessage = signal<string | null>(null);
  readonly placeholderCover = 'game-generic-cover.png';
  readonly progressStatusOptions = PROGRESS_STATUS_OPTIONS;
  readonly resultLabel = computed(() => {
    if (this.errorMessage() && !this.games().length) {
      return 'Catalog unavailable';
    }

    const total = this.games().length;
    return `${total} ${total === 1 ? 'game' : 'games'} found`;
  });

  readonly searchForm = this.formBuilder.nonNullable.group({
    term: [''],
  });

  readonly gameForm = this.formBuilder.nonNullable.group({
    name: ['', [Validators.required, Validators.maxLength(80)]],
    resume: ['', [Validators.maxLength(2000)]],
    genre: ['', [Validators.maxLength(60)]],
    developer: ['', [Validators.maxLength(100)]],
    publisher: ['', [Validators.maxLength(100)]],
    launchDate: [''],
    minReq: ['', [Validators.maxLength(500)]],
    recomReq: ['', [Validators.maxLength(500)]],
  });

  readonly progressForm = this.formBuilder.nonNullable.group({
    status: ['BACKLOGGED' as ProgressStatus, [Validators.required]],
    startedAt: [''],
    completedAt: [''],
  });

  constructor() {
    effect(() => {
      this.document.body.style.overflow = this.overlayMode() ? 'hidden' : '';
    });
  }

  ngOnInit(): void {
    this.loadAllGames();
    this.searchForm.controls.term.valueChanges
      .pipe(debounceTime(350), distinctUntilChanged())
      .subscribe((term) => {
        const normalizedTerm = term.trim();

        if (!normalizedTerm) {
          this.loadAllGames();
        }
      });
  }

  loadAllGames(): void {
    this.clearMessages();
    this.isLoadingGames.set(true);
    this.gamesService
      .getAllGames()
      .pipe(finalize(() => this.isLoadingGames.set(false)))
      .subscribe({
        next: (games) => {
          this.games.set(games);
        },
        error: (err) => {
          console.error("Games can't be loaded: ", err);
          this.errorMessage.set('Unable to load your games.');
        },
      });
  }

  submitSearch(): void {
    const term = this.searchForm.getRawValue().term.trim();

    if (!term) {
      this.loadAllGames();
      return;
    }

    this.clearMessages();
    this.isLoadingGames.set(true);
    this.gamesService
      .getGameByTerm(term)
      .pipe(finalize(() => this.isLoadingGames.set(false)))
      .subscribe({
        next: (games) => {
          this.games.set(games);
        },
        error: (err) => {
          console.error('Games search failed: ', err);
          this.errorMessage.set('Unable to search for games right now.');
        },
      });
  }

  openCreateOverlay(): void {
    this.gameForm.reset({
      name: '',
      resume: '',
      genre: '',
      developer: '',
      publisher: '',
      launchDate: '',
      minReq: '',
      recomReq: '',
    });
    this.selectedGame.set(null);
    this.resetProgressState();
    this.overlayMode.set('create');
  }

  openDetailsOverlay(game: Games): void {
    this.selectedGame.set(game);
    this.loadProgressForGame(game.id);
    this.overlayMode.set('details');
  }

  openEditOverlay(): void {
    const game = this.selectedGame();

    if (!game) {
      return;
    }

    this.gameForm.reset({
      name: game.name,
      resume: game.resume ?? '',
      genre: game.genre ?? '',
      developer: game.developer ?? '',
      publisher: game.publisher ?? '',
      launchDate: game.launchDate ?? '',
      minReq: game.minReq ?? '',
      recomReq: game.recomReq ?? '',
    });
    this.overlayMode.set('edit');
  }

  closeOverlay(): void {
    this.overlayMode.set(null);
    this.selectedGame.set(null);
    this.resetProgressState();
    this.gameForm.reset({
      name: '',
      resume: '',
      genre: '',
      developer: '',
      publisher: '',
      launchDate: '',
      minReq: '',
      recomReq: '',
    });
  }

  saveProgress(): void {
    const game = this.selectedGame();

    if (!game?.id) {
      this.errorMessage.set('No game was selected for progress update.');
      return;
    }

    if (this.progressForm.invalid) {
      this.progressForm.markAllAsTouched();
      return;
    }

    this.clearMessages();
    this.isSavingProgress.set(true);

    const payload = this.buildProgressPayload(game.id);
    const existingProgress = this.progressRecord();
    const request$ = existingProgress?.id
      ? this.progressService.updateProgress(existingProgress.id, payload)
      : this.progressService.createProgress(payload);

    request$
      .pipe(finalize(() => this.isSavingProgress.set(false)))
      .subscribe({
        next: (progress) => {
          this.progressRecord.set(progress);
          this.progressForm.patchValue({
            status: progress.status ?? 'BACKLOGGED',
            startedAt: progress.startedAt ?? '',
            completedAt: progress.completedAt ?? '',
          });
          this.statusMessage.set('Progress updated successfully.');
        },
        error: (err) => {
          console.error('Progress save failed: ', err);
          this.errorMessage.set('Unable to save the progress right now.');
        },
      });
  }

  submitGame(): void {
    if (this.gameForm.invalid) {
      this.gameForm.markAllAsTouched();
      return;
    }

    if (this.overlayMode() === 'edit' && !this.selectedGame()?.id) {
      this.errorMessage.set('No game was selected for editing.');
      return;
    }

    this.clearMessages();
    this.isSavingGame.set(true);

    const formValue = this.gameForm.getRawValue();
    const payload: Games = {
      name: formValue.name.trim(),
      resume: formValue.resume.trim() || undefined,
      genre: formValue.genre.trim() || undefined,
      developer: formValue.developer.trim() || undefined,
      publisher: formValue.publisher.trim() || undefined,
      launchDate: formValue.launchDate || undefined,
      minReq: formValue.minReq.trim() || undefined,
      recomReq: formValue.recomReq.trim() || undefined,
      active: true,
    };

    const request$ =
      this.overlayMode() === 'create'
        ? this.gamesService.createGame(payload)
        : this.gamesService.updateGame(this.selectedGame()!.id!, payload);

    request$
      .pipe(finalize(() => this.isSavingGame.set(false)))
      .subscribe({
        next: (game) => {
          this.statusMessage.set(
            this.overlayMode() === 'create'
              ? 'Game created successfully.'
              : 'Game updated successfully.'
          );

          if (this.overlayMode() === 'create') {
            this.games.update((games) => [game, ...games]);
          } else {
            this.games.update((games) =>
              games.map((currentGame) => (currentGame.id === game.id ? game : currentGame))
            );
          }

          this.selectedGame.set(game);
          this.overlayMode.set('details');
        },
        error: (err) => {
          console.error('Game save failed: ', err);
          this.errorMessage.set('Unable to save the game.');
        },
      });
  }

  removeGame(): void {
    const game = this.selectedGame();

    if (!game?.id) {
      return;
    }

    const confirmed = window.confirm(`Do you want to delete "${game.name}"?`);

    if (!confirmed) {
      return;
    }

    this.clearMessages();
    this.isDeletingGame.set(true);

    this.gamesService
      .removeGame(game.id)
      .pipe(finalize(() => this.isDeletingGame.set(false)))
      .subscribe({
        next: () => {
          this.games.update((games) => games.filter((currentGame) => currentGame.id !== game.id));
          this.statusMessage.set('Game deleted successfully.');
          this.closeOverlay();
        },
        error: (err) => {
          console.error('Game delete failed: ', err);
          this.errorMessage.set('Unable to delete the game.');
        },
      });
  }

  clearSearch(): void {
    this.searchForm.reset({ term: '' });
    this.loadAllGames();
  }

  formatSummary(game: Games): string {
    const summary = [game.genre, game.publisher].filter(Boolean);
    return summary.length ? summary.join(' • ') : 'Open the card to see more details.';
  }

  coverLabel(game: Games): string {
    return game.coverId ? 'Cover linked' : 'Placeholder cover';
  }

  trackByGameId(_index: number, game: Games): number | string {
    return game.id ?? game.name;
  }

  ngOnDestroy(): void {
    this.document.body.style.overflow = '';
  }

  private loadProgressForGame(gameId?: number): void {
    this.resetProgressState();

    if (!gameId) {
      return;
    }

    this.isLoadingProgress.set(true);
    this.progressService
      .getProgressByGameId(gameId)
      .pipe(finalize(() => this.isLoadingProgress.set(false)))
      .subscribe({
        next: (progress) => {
          this.progressRecord.set(progress);
          this.progressForm.patchValue({
            status: progress.status ?? 'BACKLOGGED',
            startedAt: progress.startedAt ?? '',
            completedAt: progress.completedAt ?? '',
          });
        },
        error: (err: HttpErrorResponse) => {
          if (err.status !== 404) {
            console.error('Progress load failed: ', err);
            this.errorMessage.set('Unable to load the progress for this game.');
          }
        },
      });
  }

  private buildProgressPayload(gameId: number): Progress {
    const formValue = this.progressForm.getRawValue();

    return {
      id: this.progressRecord()?.id,
      gameId,
      status: formValue.status,
      startedAt: formValue.startedAt || undefined,
      completedAt: formValue.completedAt || undefined,
    };
  }

  private resetProgressState(): void {
    this.progressRecord.set(null);
    this.isLoadingProgress.set(false);
    this.isSavingProgress.set(false);
    this.progressForm.reset({
      status: 'BACKLOGGED',
      startedAt: '',
      completedAt: '',
    });
  }

  private clearMessages(): void {
    this.statusMessage.set(null);
    this.errorMessage.set(null);
  }
}
