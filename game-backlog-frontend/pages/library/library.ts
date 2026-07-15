import { CommonModule, DOCUMENT } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit, effect, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { catchError, finalize, forkJoin, map, of, switchMap } from 'rxjs';
import { GameDetailsOverlayComponent } from '../../components/game-details-overlay/game-details-overlay';
import { Games } from '../../models/games';
import { ItemLibrary } from '../../models/item-libary';
import { LibraryGameCard, LibraryViewModel } from '../../models/library-view';
import { Progress, PROGRESS_STATUS_OPTIONS, ProgressStatus } from '../../models/progress';
import { GamesService } from '../../services/games.service';
import { ItemLibraryService } from '../../services/item-library.service';
import { LibraryService } from '../../services/library.service';
import { ProgressService } from '../../services/progress.service';

@Component({
  selector: 'library',
  imports: [CommonModule, ReactiveFormsModule, GameDetailsOverlayComponent],
  templateUrl: './library.html',
  styleUrl: './library.css',
})
export class LibraryComponent implements OnInit, OnDestroy {
  private readonly libraryService = inject(LibraryService);
  private readonly itemLibraryService = inject(ItemLibraryService);
  private readonly gamesService = inject(GamesService);
  private readonly progressService = inject(ProgressService);
  private readonly formBuilder = inject(FormBuilder);
  private readonly document = inject(DOCUMENT);

  readonly libraries = signal<LibraryViewModel[]>([]);
  readonly isLoadingLibraries = signal(true);
  readonly isSavingLibrary = signal(false);
  readonly isFormVisible = signal(false);
  readonly editingLibraryId = signal<number | null>(null);
  readonly formMode = signal<'create' | 'edit'>('create');
  readonly statusMessage = signal<string | null>(null);
  readonly errorMessage = signal<string | null>(null);
  readonly placeholderCover = 'game-generic-cover.png';
  readonly isAddGamesOverlayVisible = signal(false);
  readonly isLoadingAvailableGames = signal(false);
  readonly isLinkingGame = signal(false);
  readonly selectedLibraryForLink = signal<LibraryViewModel | null>(null);
  readonly availableGames = signal<Games[]>([]);
  readonly selectedGameId = signal<number | null>(null);
  readonly linkedGameIds = signal<number[]>([]);
  readonly isGameDetailsOverlayVisible = signal(false);
  readonly selectedLibraryGame = signal<Games | null>(null);
  readonly selectedItemLibraryId = signal<number | null>(null);
  readonly selectedLibraryIdForDetails = signal<number | null>(null);
  readonly isDeletingItemLibrary = signal(false);
  readonly isLoadingProgress = signal(false);
  readonly isSavingProgress = signal(false);
  readonly progressRecord = signal<Progress | null>(null);
  readonly progressStatusOptions = PROGRESS_STATUS_OPTIONS;

  readonly libraryForm = this.formBuilder.nonNullable.group({
    name: ['', [Validators.required, Validators.maxLength(60)]],
    description: ['', [Validators.maxLength(180)]],
  });

  readonly progressForm = this.formBuilder.nonNullable.group({
    status: ['BACKLOGGED' as ProgressStatus, [Validators.required]],
    startedAt: [''],
    completedAt: [''],
  });

  constructor() {
    effect(() => {
      this.document.body.style.overflow =
        this.isAddGamesOverlayVisible() || this.isGameDetailsOverlayVisible() ? 'hidden' : '';
    });
  }

  ngOnInit(): void {
    this.loadAllLibraries();
  }

  loadAllLibraries(): void {
    this.isLoadingLibraries.set(true);
    this.libraryService
      .getAllLibraries()
      .pipe(finalize(() => this.isLoadingLibraries.set(false)))
      .subscribe({
      next: (libraries) => {
        this.libraries.set(
          libraries.map((library) => ({
            ...library,
            items: [],
            itemsLoaded: false,
            isExpanded: false,
            isLoadingItems: false,
          }))
        );
      },
      error: (err) => {
        console.error("Libraries can't be loaded: ", err);
        this.errorMessage.set('Unable to load your libraries.');
      },
    });
  }

  openCreateForm(): void {
    this.isFormVisible.set(true);
    this.formMode.set('create');
    this.editingLibraryId.set(null);
    this.libraryForm.reset({
      name: '',
      description: '',
    });
  }

  openEditForm(library: LibraryViewModel): void {
    this.isFormVisible.set(true);
    this.formMode.set('edit');
    this.editingLibraryId.set(library.id ?? null);
    this.libraryForm.reset({
      name: library.name,
      description: library.description ?? '',
    });
  }

  closeForm(): void {
    this.isFormVisible.set(false);
    this.editingLibraryId.set(null);
    this.libraryForm.reset({
      name: '',
      description: '',
    });
  }

  submitLibrary(): void {
    if (this.libraryForm.invalid) {
      this.libraryForm.markAllAsTouched();
      return;
    }

    this.clearMessages();
    this.isSavingLibrary.set(true);

    const formValue = this.libraryForm.getRawValue();
    const payload = {
      name: formValue.name.trim(),
      description: formValue.description.trim() || undefined,
    };

    if (this.formMode() === 'edit' && !this.editingLibraryId()) {
      this.isSavingLibrary.set(false);
      this.errorMessage.set('No library was selected for editing.');
      return;
    }

    const request$ =
      this.formMode() === 'create'
        ? this.libraryService.createLibrary(payload)
        : this.libraryService.updateLibrary(this.editingLibraryId() as number, payload);

    request$
      .pipe(finalize(() => this.isSavingLibrary.set(false)))
      .subscribe({
        next: () => {
          this.statusMessage.set(
            this.formMode() === 'create'
              ? 'Library created successfully.'
              : 'Library updated successfully.'
          );
          this.closeForm();
          this.loadAllLibraries();
        },
        error: (err) => {
          console.error('Library save failed: ', err);
          this.errorMessage.set('Unable to save the library.');
        },
      });
  }

  openAddGamesOverlay(library: LibraryViewModel): void {
    if (!library.id) {
      return;
    }

    this.clearMessages();
    this.selectedLibraryForLink.set(library);
    this.selectedGameId.set(null);
    this.linkedGameIds.set([]);
    this.availableGames.set([]);
    this.isAddGamesOverlayVisible.set(true);
    this.isLoadingAvailableGames.set(true);

    forkJoin({
      games: this.gamesService.getAllGames(),
      libraryItems: this.itemLibraryService.getItemsByLibraryId(library.id),
    })
      .pipe(finalize(() => this.isLoadingAvailableGames.set(false)))
      .subscribe({
        next: ({ games, libraryItems }) => {
          this.availableGames.set(games);
          this.linkedGameIds.set(
            libraryItems
              .map((item) => item.gameId)
              .filter((gameId): gameId is number => typeof gameId === 'number')
          );
        },
        error: (err) => {
          console.error('Available games load failed: ', err);
          this.errorMessage.set('Unable to load the games available for this library.');
        },
      });
  }

  closeAddGamesOverlay(): void {
    this.isAddGamesOverlayVisible.set(false);
    this.selectedLibraryForLink.set(null);
    this.selectedGameId.set(null);
    this.availableGames.set([]);
    this.linkedGameIds.set([]);
  }

  selectGameForLink(gameId: number): void {
    if (this.isGameAlreadyLinked(gameId)) {
      return;
    }

    this.selectedGameId.set(gameId);
  }

  linkSelectedGame(): void {
    const selectedLibrary = this.selectedLibraryForLink();
    const gameId = this.selectedGameId();

    if (!selectedLibrary?.id || !gameId) {
      this.errorMessage.set('Select a game before linking it to this library.');
      return;
    }

    this.clearMessages();
    this.isLinkingGame.set(true);

    this.itemLibraryService
      .createItemLibrary({
        libraryId: selectedLibrary.id,
        gameId,
      })
      .pipe(finalize(() => this.isLinkingGame.set(false)))
      .subscribe({
        next: () => {
          this.statusMessage.set('Game linked to the library successfully.');
          this.updateLibraryState(selectedLibrary.id as number, (library) => ({
            ...library,
            isExpanded: true,
            itemsLoaded: false,
          }));
          this.loadLibraryItems(selectedLibrary.id as number);
          this.closeAddGamesOverlay();
        },
        error: (err) => {
          console.error('Item library create failed: ', err);
          this.errorMessage.set('Unable to link this game to the library.');
        },
      });
  }

  openLibraryGameOverlay(item: LibraryGameCard, libraryId: number): void {
    const fallbackGame: Games = {
      id: item.gameId,
      name: item.title,
      genre: item.genre,
      resume: item.subtitle,
    };

    this.clearMessages();
    this.selectedLibraryGame.set(fallbackGame);
    this.selectedItemLibraryId.set(item.itemLibraryId);
    this.selectedLibraryIdForDetails.set(libraryId);
    this.isGameDetailsOverlayVisible.set(true);

    if (!item.gameId) {
      this.resetProgressState();
      return;
    }

    this.loadProgressForGame(item.gameId);
    this.gamesService.getGameById(item.gameId).subscribe({
      next: (game) => {
        this.selectedLibraryGame.set(game);
      },
      error: (err) => {
        console.error('Game details load failed: ', err);
        this.errorMessage.set('Unable to load the full game details.');
      },
    });
  }

  closeLibraryGameOverlay(): void {
    this.isGameDetailsOverlayVisible.set(false);
    this.selectedLibraryGame.set(null);
    this.selectedItemLibraryId.set(null);
    this.selectedLibraryIdForDetails.set(null);
    this.isDeletingItemLibrary.set(false);
    this.resetProgressState();
  }

  saveProgress(): void {
    const game = this.selectedLibraryGame();

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

  removeItemLibraryFromOverlay(): void {
    const itemLibraryId = this.selectedItemLibraryId();
    const libraryId = this.selectedLibraryIdForDetails();

    if (!itemLibraryId || !libraryId) {
      return;
    }

    const confirmed = window.confirm('Do you want to remove this game from the library?');

    if (!confirmed) {
      return;
    }

    this.clearMessages();
    this.isDeletingItemLibrary.set(true);

    this.itemLibraryService
      .removeItemLibrary(itemLibraryId)
      .pipe(finalize(() => this.isDeletingItemLibrary.set(false)))
      .subscribe({
        next: () => {
          this.updateLibraryState(libraryId, (library) => ({
            ...library,
            items: library.items.filter((item) => item.itemLibraryId !== itemLibraryId),
          }));
          this.statusMessage.set('Game removed from the library successfully.');
          this.closeLibraryGameOverlay();
        },
        error: (err) => {
          console.error('Item library delete failed: ', err);
          this.errorMessage.set('Unable to remove this game from the library.');
        },
      });
  }

  removeLibrary(library: LibraryViewModel): void {
    if (!library.id) {
      return;
    }

    const confirmed = window.confirm(`Do you want to delete the "${library.name}" library?`);

    if (!confirmed) {
      return;
    }

    this.clearMessages();

    this.libraryService.removeLibrary(library.id).subscribe({
      next: () => {
        this.libraries.update((libraries) =>
          libraries.filter((currentLibrary) => currentLibrary.id !== library.id)
        );
        this.statusMessage.set('Library deleted successfully.');

        if (this.editingLibraryId() === library.id) {
          this.closeForm();
        }
      },
      error: (err) => {
        console.error('Library remove failed: ', err);
        this.errorMessage.set('Unable to delete the library.');
      },
    });
  }

  toggleLibrary(library: LibraryViewModel): void {
    if (!library.id) {
      return;
    }

    const shouldExpand = !library.isExpanded;

    this.libraries.update((libraries) =>
      libraries.map((currentLibrary) => ({
        ...currentLibrary,
        isExpanded: currentLibrary.id === library.id ? shouldExpand : false,
      }))
    );

    if (shouldExpand && !library.itemsLoaded) {
      this.loadLibraryItems(library.id);
    }
  }

  private loadLibraryItems(libraryId: number): void {
    this.updateLibraryState(libraryId, (library) => ({
      ...library,
      isLoadingItems: true,
    }));

    this.itemLibraryService
      .getItemsByLibraryId(libraryId)
      .pipe(
        switchMap((items) => {
          if (!items.length) {
            return of([] as LibraryGameCard[]);
          }

          return forkJoin(
            items.map((item) =>
              this.resolveGameCard(item).pipe(
                catchError(() => of(this.createFallbackGameCard(item)))
              )
            )
          );
        }),
        finalize(() =>
          this.updateLibraryState(libraryId, (library) => ({
            ...library,
            isLoadingItems: false,
            itemsLoaded: true,
          }))
        )
      )
      .subscribe({
        next: (items) => {
          this.updateLibraryState(libraryId, (library) => ({
            ...library,
            items,
          }));
        },
        error: (err) => {
          console.error('Library items load failed: ', err);
          this.errorMessage.set('Unable to load the games in this library.');
        },
      });
  }

  private resolveGameCard(item: ItemLibrary) {
    if (!item.gameId) {
      return of(this.createFallbackGameCard(item));
    }

    return this.gamesService.getGameById(item.gameId).pipe(
      map((game) => this.mapGameCard(item, game))
    );
  }

  private mapGameCard(item: ItemLibrary, game: Games): LibraryGameCard {
    return {
      itemLibraryId: item.id ?? game.id ?? 0,
      gameId: item.gameId ?? game.id ?? 0,
      title: game.name,
      subtitle: this.buildGameSubtitle(game),
      coverUrl: undefined,
      genre: game.genre ?? undefined,
    };
  }

  private createFallbackGameCard(item: ItemLibrary): LibraryGameCard {
    return {
      itemLibraryId: item.id ?? item.gameId ?? 0,
      gameId: item.gameId ?? 0,
      title: item.gameId ? `Game #${item.gameId}` : 'Game without identifier',
      subtitle: 'Game details are currently unavailable.',
    };
  }

  private buildGameSubtitle(game: Games): string {
    const metadata = [game.genre, game.publisher].filter(Boolean);

    if (metadata.length) {
      return metadata.join(' • ');
    }

    return 'This library is ready to receive the full game data.';
  }

  private updateLibraryState(
    libraryId: number,
    updater: (library: LibraryViewModel) => LibraryViewModel
  ): void {
    this.libraries.update((libraries) =>
      libraries.map((library) => (library.id === libraryId ? updater(library) : library))
    );
  }

  private clearMessages(): void {
    this.statusMessage.set(null);
    this.errorMessage.set(null);
  }

  isGameAlreadyLinked(gameId: number): boolean {
    return this.linkedGameIds().includes(gameId);
  }

  trackByLibraryId(_index: number, library: LibraryViewModel): number | string {
    return library.id ?? library.name;
  }

  trackByGameId(_index: number, item: LibraryGameCard): number {
    return item.itemLibraryId;
  }

  trackByAvailableGameId(_index: number, game: Games): number | string {
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
}
