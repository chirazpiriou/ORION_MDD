import { HttpClient, HttpHeaders } from '@angular/common/http';
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

  constructor(private httpClient: HttpClient, 
    private authService: AuthService) {}

  public all(): Observable<Article[]> {
    const token = this.authService.getToken();
    if (!token) {
      this.authService.logOut();
      // Retourner une erreur ou un Observable vide selon ta logique
    }
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });
    const url = `${this.pathService}/all`;
    return this.httpClient.get<Article[]>(url, { headers }).pipe(
      tap((response) => console.log('Get response: ', response))
    );
  }
  

  public detail(id: number): Observable<Article> {
    const token = this.authService.getToken();
    if (!token) {
      this.authService.logOut();
      return throwError(() => new Error('Unauthorized: No valid token'));
    }
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });
    return this.httpClient.get<Article>(`${this.pathService}/detail/${id}`, { headers }).pipe(
      catchError(error => {
        console.error(`Error fetching article with ID ${id}:`, error);
        return throwError(() => error);
      })
    );
  }

  public create(article: ArticleRequest): Observable<Article> {
    const headers = this.getAuthHeaders();
    if (!headers) {
      return throwError(() => new Error('Unauthorized: No valid token'));
    }

    return this.httpClient.post<Article>(`${this.pathService}/create`, article, { headers }).pipe(
      catchError(error => {
        console.error('Error creating article:', error);
        return throwError(() => error);
      })
    );
  }

  private getAuthHeaders(): HttpHeaders | null {
    const token = this.authService.getToken(); // Si AuthService a une méthode `getToken()`, utilise-la
    if (!token) {
      this.authService.logOut();
      return null;
    }
    return new HttpHeaders({ Authorization: `Bearer ${token}` });
  }
}