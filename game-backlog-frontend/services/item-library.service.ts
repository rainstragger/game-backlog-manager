import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Enviroments } from '../environments/enviroments';
import { ItemLibrary } from '../models/item-libary';

@Injectable({
    providedIn: 'root',
})
export class ItemLibraryService {
    private readonly http = inject(HttpClient);
    private readonly apiUrl = `${Enviroments.apiBaseUrl}/item-library`;

    getItemsByLibraryId(libraryId: number): Observable<ItemLibrary[]> {
        return this.http.get<ItemLibrary[]>(`${this.apiUrl}/library/${libraryId}`);
    }

    createItemLibrary(itemLibrary: Pick<ItemLibrary, 'libraryId' | 'gameId'>): Observable<ItemLibrary> {
        return this.http.post<ItemLibrary>(this.apiUrl, itemLibrary);
    }

    removeItemLibrary(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}
