import { CommonModule, DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ReactiveFormsModule, FormGroup } from '@angular/forms';
import { Games } from '../../models/games';

@Component({
  selector: 'game-details-overlay',
  imports: [CommonModule, DatePipe, ReactiveFormsModule],
  templateUrl: './game-details-overlay.html',
  styleUrl: './game-details-overlay.css',
})
export class GameDetailsOverlayComponent {
  @Input({ required: true }) game: Games | null = null;
  @Input({ required: true }) progressForm!: FormGroup;
  @Input() progressStatuses: readonly string[] = [];
  @Input() placeholderCover = 'game-generic-cover.png';
  @Input() isSavingProgress = false;
  @Input() isDeletingGame = false;
  @Input() isDeletingItemLibrary = false;
  @Input() isLoadingProgress = false;
  @Input() showGameActions = false;
  @Input() showItemLibraryDelete = false;

  @Output() closeOverlay = new EventEmitter<void>();
  @Output() editGame = new EventEmitter<void>();
  @Output() deleteGame = new EventEmitter<void>();
  @Output() deleteItemLibrary = new EventEmitter<void>();
  @Output() saveProgress = new EventEmitter<void>();

  get statusControlInvalid(): boolean {
    const control = this.progressForm?.get('status');
    return !!control && control.invalid && control.touched;
  }
}
