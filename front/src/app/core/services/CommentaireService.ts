import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Commentaire } from '../models/commentaire.model';
import { AuthService } from './AuthService';


@Injectable({
  providedIn: 'root',
})
export class CommentairesService {
  private pathService = 'http://localhost:8080/api/commentaire';

  constructor(private httpClient: HttpClient, 
    private authService: AuthService) {}

    private getAuthHeaders(): HttpHeaders {
      const token = this.authService.getToken();
      if (!token) {
        this.authService.logOut();
        throw new Error('Token manquant');
      }
      return new HttpHeaders({ Authorization: `Bearer ${token}` });
    }

  /**
   * Creates a new comment by sending a POST request to the backend.
   
   * 
   * @param commentaire - The comment details to be created.
   * @returns An Observable containing the created comment details.
   */
  public create(commentaire: Commentaire): Observable<Commentaire> {
    // Create the URL with the email as a query parameter
    const url = `${this.pathService}/create`;
    const headers = this.getAuthHeaders();
    
    // Send a POST request to create the comment, passing the commentaire object
    return this.httpClient.post<Commentaire>(url, commentaire, {
      headers});
  }


}
