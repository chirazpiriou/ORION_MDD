import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Theme } from "../models/theme.model";
import { Observable } from "rxjs";
import { AuthService } from "./AuthService";



@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private readonly apiUrl = 'http://localhost:8080/api/theme';

constructor(private http: HttpClient, private authService: AuthService) {}

private getAuthHeaders(): HttpHeaders {
  const token = this.authService.getToken();
  if (!token) {
    throw new Error('Token manquant');
  }
  return new HttpHeaders({ Authorization: `Bearer ${token}` });
}

getAllThemes(): Observable<Theme[]> {
  // Si cette route est aussi sécurisée, ajouter l'en-tête
  return this.http.get<Theme[]>(`${this.apiUrl}/all`, { headers: this.getAuthHeaders() });
}

getAllUserThemes(): Observable<Theme[]> {
  return this.http.get<Theme[]>(`${this.apiUrl}/user`, { headers: this.getAuthHeaders() });
}

}