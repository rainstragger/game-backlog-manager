import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Enviroments } from '../environments/enviroments';
import { Progress } from '../models/progress';

@Injectable({
    providedIn: 'root',
})
export class ProgressService {
    private readonly http = inject(HttpClient);
    private readonly apiUrl = `${Enviroments.apiBaseUrl}/progress`;

    getProgressByGameId(gameId: number): Observable<Progress> {
        return this.http.get<Progress>(`${this.apiUrl}/game/${gameId}`);
    }

    createProgress(progress: Progress): Observable<Progress> {
        return this.http.post<Progress>(this.apiUrl, progress);
    }

    updateProgress(id: number, progress: Progress): Observable<Progress> {
        return this.http.put<Progress>(`${this.apiUrl}/${id}`, progress);
    }
}
