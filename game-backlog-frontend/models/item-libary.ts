import { Games } from "./games";
import { Library } from "./library";

export interface ItemLibrary {
    id?: number;
    library?: Library;
    libraryId?: number;
    game?: Games;
    gameId?: number;
    createdAt?: string;
}

export type ItemLibary = ItemLibrary;
