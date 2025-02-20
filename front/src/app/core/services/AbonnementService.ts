import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AbonnementService {
  pathService="http://localhost:8080/api/abonnement";
  constructor(private http: HttpClient) { }


  changeSubscriptionStatus(id: number , userEmail: string): Observable<string> {
    return this.http.get(`${this.pathService}/subscription/${id}?userEmail=${encodeURIComponent(userEmail)}`, {responseType: 'text'});
  }
}