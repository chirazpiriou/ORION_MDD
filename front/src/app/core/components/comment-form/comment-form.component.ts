import { Component, EventEmitter, Input, OnInit, Output, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subject, takeUntil } from 'rxjs';
import { CommentairesService } from '../../services/CommentaireService';
import { Commentaire } from '../../models/commentaire.model';

@Component({
  selector: 'app-comment-form',
  templateUrl: './comment-form.component.html',
  styleUrls: ['./comment-form.component.scss'],
})
export class CommentFormComponent implements OnInit, OnDestroy {
  public commentForm!: FormGroup;
  private destroy$ = new Subject<void>();
  errorStr: string = '';

  @Input() articleId!: number | null;
  @Output() newCommentPosted = new EventEmitter<void>();
  @Output() commentsRefreshed = new EventEmitter<Commentaire[]>(); // Rafraîchissement des commentaires

  constructor(
    private commentairesService: CommentairesService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.commentForm = this.fb.group({
      contenu: [
        '',
        [
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(2000),
        ],
      ],
    });
  }

  onSubmitForm(): void {
    if (this.commentForm.invalid) {
      return;
    }

    if (!this.articleId) {
      this.errorStr = 'Aucun article sélectionné.';
      return;
    }

    const commentaire: Commentaire = {
      ...this.commentForm.value,
      articleId: this.articleId
    };

  
    this.commentairesService
      .create(commentaire)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response) => {
          this.commentForm.reset(); 
          this.errorStr = ''; 

          
          this.reloadComments();
        },
        error: (error) => {
          this.errorStr = error?.message || 'Une erreur est survenue lors de l’envoi du commentaire.';
        },
      });
  }

  reloadComments(): void {
    if (this.articleId) {
      this.commentairesService.getCommentsByArticleId(this.articleId).subscribe({
        next: (comments) => {
          this.commentsRefreshed.emit(comments); 
        },
        error: (error) => {
          console.error('Erreur lors du rafraîchissement des commentaires:', error);
        }
      });
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
