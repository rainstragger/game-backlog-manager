import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { beforeEach, describe, expect, it } from 'vitest';
import { GamesService } from '../../services/games.service';
import { ProgressService } from '../../services/progress.service';

import { GamesComponent } from './games';

describe('GamesComponent', () => {
  let component: GamesComponent;
  let fixture: ComponentFixture<GamesComponent>;

  const gamesServiceMock = {
    getAllGames: () =>
      of([
        {
          id: 1,
          name: 'Elden Ring',
          developer: 'FromSoftware',
          publisher: 'Bandai Namco',
        },
      ]),
    getGameByTerm: () => of([]),
    createGame: () => of({ id: 2, name: 'New Game' }),
    updateGame: () => of({ id: 1, name: 'Updated Game' }),
    removeGame: () => of(void 0),
  };

  const progressServiceMock = {
    getProgressByGameId: () =>
      of({
        id: 1,
        gameId: 1,
        status: 'PLAYING',
        startedAt: '2026-07-15',
      }),
    createProgress: () => of({ id: 2, gameId: 1, status: 'BACKLOGGED' }),
    updateProgress: () => of({ id: 1, gameId: 1, status: 'COMPLETED' }),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GamesComponent],
      providers: [
        { provide: GamesService, useValue: gamesServiceMock },
        { provide: ProgressService, useValue: progressServiceMock },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(GamesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load games on init', () => {
    expect(component.games().length).toBe(1);
    expect(component.games()[0].name).toBe('Elden Ring');
  });

  it('should open details overlay and load progress', () => {
    component.openDetailsOverlay(component.games()[0]);

    expect(component.overlayMode()).toBe('details');
    expect(component.progressForm.getRawValue().status).toBe('PLAYING');
  });
});
