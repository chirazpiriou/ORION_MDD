import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Commentaire } from '../models/commentaire.model';
import { AuthService } from './AuthService';

@Injectable({
  providedIn: 'root',
})
export class CommentairesService {
  private pathService = 'http://localhost:8080/api/commentaire';

  constructor(private httpClient: HttpClient) {}

  public create(commentaire: Commentaire): Observable<Commentaire> {
    const url = `${this.pathService}/create`;
    return this.httpClient.post<Commentaire>(url, commentaire);
  }

  public getCommentsByArticleId(articleId: number): Observable<Commentaire[]> {
    return this.httpClient.get<Commentaire[]>(`${this.pathService}/article/${articleId}`);
  }
}
