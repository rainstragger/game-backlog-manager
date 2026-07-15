import { Routes } from '@angular/router';
import { GamesComponent } from '../../pages/games/games';
import { LibraryComponent } from '../../pages/library/library';

export const routes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        redirectTo: 'games',
    },

    {
        path: 'games',
        component: GamesComponent,
    },

    {
        path: 'library',
        component: LibraryComponent,
    },

    
];
