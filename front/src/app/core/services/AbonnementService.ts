import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SubscriptionStatusDTO } from '../interfaces/subscriptionStatusDTO.interface';

@Injectable({
  providedIn: 'root',
})
export class AbonnementService {
  pathService = 'http://localhost:8080/api/abonnement';
  constructor(private http: HttpClient) {}

  changeSubscriptionStatus(id: number): Observable<SubscriptionStatusDTO> {
    return this.http.post<SubscriptionStatusDTO>(`${this.pathService}/change/${id}`, {}, {
      responseType: 'json',  
    });
  }
}
