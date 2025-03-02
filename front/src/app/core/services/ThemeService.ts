import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Theme } from "../models/theme.model";
import { Observable } from "rxjs";
import { AuthService } from "./AuthService";



@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private readonly apiUrl = 'http://localhost:8080/api/theme';

constructor(private http: HttpClient) {}


getAllThemes(): Observable<Theme[]> {
  return this.http.get<Theme[]>(`${this.apiUrl}/all`);
}

getAllUserThemes(): Observable<Theme[]> {
  return this.http.get<Theme[]>(`${this.apiUrl}/user`);
}

}