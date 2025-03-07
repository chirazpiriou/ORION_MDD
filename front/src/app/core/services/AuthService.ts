import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { Token } from '../interfaces/token.interface';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private pathService = 'http://localhost:8080/api/auth';

  constructor(private httpClient: HttpClient, private router: Router) {}

  // Method for registration
  public register(registerRequest: RegisterRequest): Observable<Token> {
    return this.httpClient.post<Token>(
      `${this.pathService}/register`,
      registerRequest
    );
  }

  // Method for login
  public login(loginRequest:{ email?: string; name?: string; password: string }): Observable<Token> {
    return this.httpClient.post<Token>(
      `${this.pathService}/login`,
      loginRequest
    );
  }

  // Method to retrieve the token from localStorage
  public getToken(): string | null {
    return localStorage.getItem('token') || null;
  }

  // Method to log out (removes the token)
  public logOut(): void {
    localStorage.clear();
    this.router.navigate(['']);
  }
  public get_profile(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/profile`);
  }

  public update(user: User): Observable<any> {
    return this.httpClient
      .put<{ message: string; token?: string }>(
        `${this.pathService}/update`,
        user
      )
      .pipe(
        tap((response) => {
          if (response.token) {
            localStorage.setItem('token', response.token);
          }
        })
      );
  }
}
