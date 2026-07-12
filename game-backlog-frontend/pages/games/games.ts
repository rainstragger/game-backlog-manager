import { HttpClient } from '@angular/common/http';
import { Component, OnInit, signal} from '@angular/core';
import { Router } from '@angular/router';
import { Games } from '../../models/games';
import { GamesService } from '../../services/games.service';

@Component({
  selector: 'games',
  imports: [],
  templateUrl: './games.html',
  styleUrl: './games.css',
})
export class GamesComponent implements OnInit {
  games = signal<Games[]>([]);

  constructor(
    private http: HttpClient,
    private router: Router,
    private gameService: GamesService
  ) {}

  ngOnInit(): void {
    this.loadAllGames();
  }

  loadAllGames(): void {
    this.gameService.getAllGames().subscribe({
      next: (games) => {
        this.games.set(games);
        console.log(this.games);
      },
      error: (err) => {
        console.error("Games can't be loaded: ", err);
      }
    });
  }
}
