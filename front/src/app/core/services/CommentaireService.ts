import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Commentaire } from '../models/commentaire.model';


@Injectable({
  providedIn: 'root',
})
export class CommentairesService {
  private pathService = 'http://localhost:8080/api/commentaire';

  constructor(private httpClient: HttpClient) {}

  /**
   * Creates a new comment by sending a POST request to the backend.
   
   * 
   * @param commentaire - The comment details to be created.
   * @returns An Observable containing the created comment details.
   */
  public create(commentaire: Commentaire, userEmail: string): Observable<Commentaire> {
    // Create the URL with the email as a query parameter
    const url = `${this.pathService}/create?userEmail=${encodeURIComponent(userEmail)}`;
   

    // Send a POST request to create the comment, passing the commentaire object
    return this.httpClient.post<Commentaire>(url, commentaire);
  }
}
