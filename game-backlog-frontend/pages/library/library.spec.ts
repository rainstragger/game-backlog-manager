import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { beforeEach, describe, expect, it } from 'vitest';
import { GamesService } from '../../services/games.service';
import { ItemLibraryService } from '../../services/item-library.service';
import { LibraryService } from '../../services/library.service';
import { ProgressService } from '../../services/progress.service';

import { LibraryComponent } from './library';

describe('LibraryComponent', () => {
  let component: LibraryComponent;
  let fixture: ComponentFixture<LibraryComponent>;

  const libraryServiceMock = {
    getAllLibraries: () =>
      of([
        {
          id: 1,
          name: 'Main Collection',
          description: 'Primary library',
        },
      ]),
    createLibrary: () => of({ id: 2, name: 'New Library' }),
    updateLibrary: () => of({ id: 1, name: 'Main Collection' }),
    removeLibrary: () => of(void 0),
  };

  const itemLibraryServiceMock = {
    getItemsByLibraryId: () => of([]),
    createItemLibrary: () => of({ id: 2, libraryId: 1, gameId: 1 }),
    removeItemLibrary: () => of(void 0),
  };

  const gamesServiceMock = {
    getAllGames: () =>
      of([
        {
          id: 1,
          name: 'Game Example',
          genre: 'RPG',
        },
      ]),
    getGameById: () =>
      of({
        id: 1,
        name: 'Game Example',
        developer: 'Studio Example',
      }),
  };

  const progressServiceMock = {
    getProgressByGameId: () =>
      of({
        id: 1,
        gameId: 1,
        status: 'PLAYING',
      }),
    createProgress: () => of({ id: 2, gameId: 1, status: 'BACKLOGGED' }),
    updateProgress: () => of({ id: 1, gameId: 1, status: 'COMPLETED' }),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LibraryComponent],
      providers: [
        { provide: LibraryService, useValue: libraryServiceMock },
        { provide: ItemLibraryService, useValue: itemLibraryServiceMock },
        { provide: GamesService, useValue: gamesServiceMock },
        { provide: ProgressService, useValue: progressServiceMock },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(LibraryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load libraries on init', () => {
    expect(component.libraries().length).toBe(1);
    expect(component.libraries()[0].name).toBe('Main Collection');
  });

  it('should open add games overlay and load catalog entries', () => {
    component.openAddGamesOverlay(component.libraries()[0]);

    expect(component.isAddGamesOverlayVisible()).toBe(true);
    expect(component.availableGames().length).toBe(1);
    expect(component.availableGames()[0].name).toBe('Game Example');
  });

  it('should open library game overlay and load progress', () => {
    component.openLibraryGameOverlay(
      {
        itemLibraryId: 1,
        gameId: 1,
        title: 'Game Example',
        subtitle: 'RPG',
      },
      1
    );

    expect(component.isGameDetailsOverlayVisible()).toBe(true);
    expect(component.progressForm.getRawValue().status).toBe('PLAYING');
  });
});
