import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './AuthService';

@Injectable({
  providedIn: 'root'
})
export class AbonnementService {
  pathService="http://localhost:8080/api/abonnement";
  constructor(private http: HttpClient, private authService: AuthService) { }

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    if (!token) {
      this.authService.logOut();
      throw new Error('Utilisateur non authentifié');
    }
    return new HttpHeaders({ Authorization: `Bearer ${token}` });
  }
  changeSubscriptionStatus(id: number): Observable<string> {
    const headers = this.getAuthHeaders();
    return this.http.get(`${this.pathService}/subscription/${id}`, { headers, responseType: 'text' });
  }
}