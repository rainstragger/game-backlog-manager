import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Enviroments } from '../environments/enviroments';
import { Games } from '../models/games';

@Injectable({
    providedIn: 'root',
})
export class GamesService {
    private readonly http = inject(HttpClient);
    private readonly apiUrl = `${Enviroments.apiBaseUrl}/games`;

    getAllGames(): Observable<Games[]> {
        return this.http.get<Games[]>(this.apiUrl);
    }

    getGameById(id: number): Observable<Games> {
        return this.http.get<Games>(`${this.apiUrl}/${id}`);
    }

    getGameByTerm(term: string): Observable<Games[]> {
        return this.http.get<Games[]>(`${this.apiUrl}/search/${term}`);
    }

    createGame(game: Games): Observable<Games> {
        return this.http.post<Games>(this.apiUrl, game);
    }

    updateGame(id: number, game: Games): Observable<Games> {
        return this.http.put<Games>(`${this.apiUrl}/${id}`, game);
    }

    removeGame(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}
