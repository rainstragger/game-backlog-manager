import { Routes } from '@angular/router';
import { GamesComponent } from '../../pages/games/games';
import { HomeComponent } from '../../pages/home/home';
import { LibraryComponent } from '../../pages/library/library';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
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
