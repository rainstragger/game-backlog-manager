import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Enviroments } from '../environments/enviroments';
import { Library } from '../models/library';

@Injectable({
    providedIn: 'root',
})
export class LibraryService {
    private readonly http = inject(HttpClient);
    private readonly apiUrl = `${Enviroments.apiBaseUrl}/library`;

    getAllLibraries(): Observable<Library[]> {
        return this.http.get<Library[]>(this.apiUrl);
    }

    getLibraryById(id: number): Observable<Library> {
        return this.http.get<Library>(`${this.apiUrl}/${id}`);
    }

    getLibraryByTerm(term: string): Observable<Library[]> {
        return this.http.get<Library[]>(`${this.apiUrl}/search/${term}`);
    }

    createLibrary(library: Pick<Library, 'name' | 'description'>): Observable<Library> {
        return this.http.post<Library>(this.apiUrl, library);
    }

    updateLibrary(id: number, library: Pick<Library, 'name' | 'description'>): Observable<Library> {
        return this.http.put<Library>(`${this.apiUrl}/${id}`, library);
    }

    removeLibrary(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}
