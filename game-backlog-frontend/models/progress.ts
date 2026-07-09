import { Games } from "./games";

export interface Progress {
    id?: number;
    games?: Games;
    gameId?: number;
    status?: string;
    startedAt?: string;
    completedAt?: string;
    createdAt?: string;
}
