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
    // Create the URL with the email as a query parameter
    const url = `${this.pathService}/create`;
    
    
    // Send a POST request to create the comment, passing the commentaire object
    return this.httpClient.post<Commentaire>(url, commentaire);
  }


}
