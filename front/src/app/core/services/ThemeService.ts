import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Theme } from "../models/theme.model";
import { Observable } from "rxjs";



@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private readonly apiUrl = 'http://localhost:8080/api/theme';

constructor(private http: HttpClient) {}

getAllThemes(userEmail: string): Observable<Theme[]> {
  // Fetch all themes from the API
  return this.http.get<Theme[]>(`${this.apiUrl}/all?userEmail=${encodeURIComponent(userEmail)}`);
}

getAllUserThemes(userEmail: string): Observable<Theme[]> {
  // Fetch themes for a specific user based on their email
  return this.http.get<Theme[]>(`${this.apiUrl}/user?userEmail=${encodeURIComponent(userEmail)}`);
}

}