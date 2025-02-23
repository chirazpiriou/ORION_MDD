import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { Token } from '../interfaces/token.interface';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = 'http://localhost:8080/api/auth'; 

  constructor(private httpClient: HttpClient, private router: Router) {}

  // Method for registration
  public register(registerRequest: RegisterRequest ): Observable<Token> {
    return this.httpClient.post<Token>(`${this.pathService}/register`, registerRequest).pipe(
      catchError((error) => {
        throw new Error('Registration failed');
      })
    );
  }

  // Method for login
  public  login( loginRequest: LoginRequest): Observable<Token> {
    return this.httpClient.post<Token>(`${this.pathService}/login`, loginRequest).pipe(
      tap((response) => {
        // Stocker le token dans le localStorage de manière sécurisée
        localStorage.setItem('token', response.token);
      }),
      catchError((error) => {
        throw new Error('Login failed');
      })
    );
  }

  // Method to retrieve the token from localStorage
  public  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // Method to check if the user is authenticated
  isAuthenticated(): boolean {
    return this.getToken() !== null;
  }

  // Method to log out (removes the token)
  public  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['']);
  }
  public get_profile(): Observable<User> {
    return this.httpClient
      .get<User>(`${this.pathService}/profile`);
  }

 
}
