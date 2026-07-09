import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'games',
  imports: [],
  templateUrl: './games.html',
  styleUrl: './games.css',
})
export class GamesComponent {
games: Games[] = [];
data: any[] = [];

private constructor(
  private http: HttpClient,
  private router: Router,
) {}

getGames() {
  this.http.get<any[]>('/games').subscribe((res) => {
    this.data = res;
  });
}

}
