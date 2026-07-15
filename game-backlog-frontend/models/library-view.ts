import { Library } from './library';

export interface LibraryGameCard {
    itemLibraryId: number;
    gameId: number;
    title: string;
    subtitle: string;
    coverUrl?: string;
    genre?: string;
}

export interface LibraryViewModel extends Library {
    items: LibraryGameCard[];
    itemsLoaded: boolean;
    isExpanded: boolean;
    isLoadingItems: boolean;
}
