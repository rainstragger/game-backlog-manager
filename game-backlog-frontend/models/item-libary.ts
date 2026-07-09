import { Games } from "./games";
import { Library } from "./library";

export interface ItemLibary {
    id?: number;
    libary?: Library;
    libraryId?: number;
    games?: Games;
    gameId?: number;
    createdAt?: string;
}
