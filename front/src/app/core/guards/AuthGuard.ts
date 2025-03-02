import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../services/AuthService';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    // Check if the user has a valid token
    const token = this.auth.getToken();
    if (token) {
      console.log('Authenticated user, access granted');
      return true; // Access granted
    } else {
      console.log('Unauthenticated user, redirecting to login page');
      this.router.navigateByUrl('/login'); // Redirect if not authenticated
      return false;
    }
  }
}
