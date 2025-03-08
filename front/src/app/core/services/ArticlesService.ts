import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { Article } from '../models/article.model';
import { ArticleRequest } from '../models/articleRequest.model';
import { AuthService } from './AuthService';

@Injectable({
  providedIn: 'root',
})
export class ArticlesService {
  private pathService = 'http://localhost:8080/api/article';

  constructor(
    private httpClient: HttpClient,
    private authService: AuthService
  ) {}

  public all(): Observable<Article[]> {
    const url = `${this.pathService}/all`;
    return this.httpClient.get<Article[]>(url);
  }

  public detail(id: number): Observable<Article> {
    return this.httpClient
      .get<Article>(`${this.pathService}/detail/${id}`)
      .pipe(
        catchError((error) => {
          console.error(`Error fetching article with ID ${id}:`, error);
          return throwError(() => error);
        })
      );
  }

  public create(article: ArticleRequest): Observable<Article> {
    return this.httpClient
      .post<Article>(`${this.pathService}/create`, article)
      .pipe(
        catchError((error) => {
        console.error('Error creating article:', error);
        console.error('Response error status:', error.status);
        console.error('Response error message:', error.message);
          return throwError(() => error);
        })
      );
  }
}
