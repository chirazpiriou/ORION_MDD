import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subject, takeUntil } from 'rxjs';
import { Article } from 'src/app/core/models/article.model';
import { ArticlesService } from 'src/app/core/services/ArticlesService';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.scss'],
})
export class ArticleListComponent implements OnInit, OnDestroy {
  articles!: Article[]; // Array to hold the articles
  articles$!: Observable<Article[]>; // Observable for articles
  private destroy$: Subject<boolean> = new Subject(); // Subject to manage lifecycle
  error_str!: string; // String to hold error messages
  // Flag to indicate current sort order: true for ascending, false for descending
  isAscending: boolean = true;
  constructor(private articleService: ArticlesService) {}

  ngOnInit(): void {
    // Fetch articles from the server
    this.articleService
      .all()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (articlesFromServer: Article[]) => {
          this.articles = articlesFromServer;
        },
        error: (error) => {
          this.error_str =
            error || "Une erreur est survenue lors du chargement de l'article";
        },
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next(true); // Notify that the component is being destroyed
    this.destroy$.complete(); // Complete the subject to prevent memory leaks
  }

  // Method to sort articles based on the current sort order flag
  sortArticles() {
    if (this.isAscending) {
      // Sort articles by creation date in ascending order
      this.articles.sort(
        (a, b) =>
          new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime()
      );
    } else {
      // Sort articles by creation date in descending order
      this.articles.sort(
        (a, b) =>
          new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
      );
    }
  }

  // Method called when the sort order button is clicked
  toggleSortOrder() {
    // Toggle the sort order flag
    this.isAscending = !this.isAscending;
    // Apply the sort with the new order
    this.sortArticles();
  }
}
