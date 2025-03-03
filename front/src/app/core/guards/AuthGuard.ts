import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { AuthService } from '../services/AuthService';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    // Check if the user has a valid token
    const token = this.auth.getToken();
    if (token) {
      return true; // Access granted
    } else {
      this.router.navigateByUrl('/login'); // Redirect if not authenticated
      return false;
    }
  }
}
