import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Enviroments } from '../environments/enviroments';
import { Observable } from 'rxjs';
import { Games } from '../models/games';

@Service()
export class GamesService {
    
    http = inject(HttpClient);
    apiUrl = `${Enviroments.apiBaseUrl}/games`;

    getAllGames():Observable<Games[]>{
        return this.http.get<Games[]>(this.apiUrl);
    }

    getGamebyId(id: number):Observable<Games[]>{
        return this.http.get<Games[]>(this.apiUrl + `/${id}`)
    }

    getGamebyTerm(term: string):Observable<Games[]>{
        return this.http.get<Games[]>(this.apiUrl + `/search/${term}`)
    }

    createGame(game: Games):Observable<Games[]>{
        return this.http.post<Games[]>(this.apiUrl, game);
    }

    updateGame(game: Games):Observable<Games[]>{
        return this.http.put<Games[]>(this.apiUrl + `/${game.id}`, game);
    }

    removeGame(id: number):Observable<Games[]>{
        return this.http.delete<Games[]>(this.apiUrl + `/${id}`)
    }
}
