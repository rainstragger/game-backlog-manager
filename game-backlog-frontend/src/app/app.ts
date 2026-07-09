import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { BacklogNavbarComponent } from '../../components/backlog-navbar/backlog-navbar';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, BacklogNavbarComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('game-backlog-frontend');
}
