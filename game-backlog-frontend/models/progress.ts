import { Games } from "./games";

export const PROGRESS_STATUS_OPTIONS = ['BACKLOGGED', 'PLAYING', 'COMPLETED'] as const;
export type ProgressStatus = (typeof PROGRESS_STATUS_OPTIONS)[number];

export interface Progress {
    id?: number;
    games?: Games;
    gameId?: number;
    status?: ProgressStatus;
    startedAt?: string;
    completedAt?: string;
    createdAt?: string;
}
