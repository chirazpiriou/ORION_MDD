import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subject, switchMap, takeUntil } from 'rxjs';
import { Article } from 'src/app/core/models/article.model';
import { Commentaire } from 'src/app/core/models/commentaire.model';
import { ArticlesService } from 'src/app/core/services/ArticlesService';
import { CommentairesService } from 'src/app/core/services/CommentaireService';

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.scss'],
})
export class ArticleDetailComponent implements OnInit, OnDestroy {
  article!: Article;
  commentaires: Commentaire[] = [];
  article_id!: number | null;
  private destroy$: Subject<boolean> = new Subject();
  error_str!: string;

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticlesService,
    private commentairesService: CommentairesService
  ) {}

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        switchMap((params) => {
          const id = Number(params.get('id'));
          this.article_id = id;
          return this.articleService.detail(id);
        }),
        takeUntil(this.destroy$)
      )
      .subscribe({
        next: (articleResponse) => {
          this.article = articleResponse;
          this.loadComments();
        },
        error: (error) => {
          this.error_str = error || 'Une erreur est survenue lors du chargement de l’article';
        },
      });
  }

  loadComments(): void {
    if (this.article_id) {
      this.commentairesService.getCommentsByArticleId(this.article_id)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (comments) => {
            this.commentaires = comments;
          },
          error: () => {
            this.commentaires = [];
          },
        });
    }
  }


  updateCommentsList(updatedComments: Commentaire[]): void {
    this.commentaires = updatedComments;
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.complete();
  }
}
