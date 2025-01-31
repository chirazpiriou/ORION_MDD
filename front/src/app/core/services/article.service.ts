import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Article } from '../models/article.model';
import { ArticleRequest } from '../models/articleRequest.model';


@Injectable({
  providedIn: 'root',
})
export class ArticlesService {

  private pathService = 'http://localhost:8080/api/article';

  constructor(private httpClient: HttpClient) {}

  public all(userEmail: string): Observable<Article[]> {
    const url = `${this.pathService}/all?userEmail=${encodeURIComponent(userEmail)}`;
    return this.httpClient.get<Article[]>(url)
    .pipe(tap((response) => console.log('Get response: ', response)));
  }

  public detail(id: number): Observable<Article> {
    return this.httpClient.get<Article>(`${this.pathService}/detail/${id}`);
  }

  public create(article: ArticleRequest): Observable<Article> {
    return this.httpClient.post<Article>(`${this.pathService}/create`, article);
  }
}