import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subject, takeUntil } from 'rxjs';
import { CommentairesService } from '../../services/CommentaireService';
import { Router } from '@angular/router';
import { Commentaire } from '../../models/commentaire.model';

@Component({
  selector: 'app-comment-form',
  templateUrl: './comment-form.component.html',
  styleUrls: ['./comment-form.component.scss']
})
export class CommentFormComponent implements OnInit {
  // Reactive form group for managing the comment form
  public commentForm!: FormGroup;

  // Subject used for unsubscribing from observables to prevent memory leaks
  private destroy$: Subject<boolean> = new Subject();

  // Error message string to display any errors that occur
  errorStr: string = '';

  // Input to receive the article ID from the parent component
  @Input() articleId!: number | undefined;

  // Output event emitter to notify parent component when a new comment is posted
  @Output() newCommentPosted = new EventEmitter<void>();
  @Output() commentAdded = new EventEmitter<Commentaire>();

  constructor(
    private commentairesService: CommentairesService, // Service to interact with the backend for comments
    private fb: FormBuilder, // Angular's FormBuilder to create the form
  ) {}
  // Initialize the form with necessary validators
  ngOnInit(): void {
    this.commentForm = this.fb.group({
      contenu: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(2000)]],
    });
  }

  // Handles form submission
  onSubmitForm(): void {
    if (this.commentForm.valid) { // Check if form is valid before submitting
      // Create a new subject for managing the lifecycle of the subscription
      const commentaire = this.commentForm.value as Commentaire;
      commentaire.article_id = this.articleId; // Set the article ID from input

      console.log('Article ID récupéré:', commentaire.article_id);

     
      // Call the service to submit the comment
      this.commentairesService.create(commentaire)
        .pipe(takeUntil(this.destroy$)) // Ensures unsubscription when the component is destroyed
        .subscribe({
          next: (response) => {
            this.commentForm.reset(); // Reset the form on successful submission
            this.newCommentPosted.emit(); // Emit event to notify parent component
            this.commentAdded.emit(commentaire);
          },
          error: (error) => {
            this.errorStr = error || 'An error occurred while posting the comment.'; // Handle any errors
          },
        });
    }
  }
  // Cleanup logic when the component is destroyed
  ngOnDestroy(): void {
    this.destroy$.next(true); // Notify the observable to unsubscribe
    this.destroy$.complete(); // Complete the subject to avoid memory leaks
  }

  
}
