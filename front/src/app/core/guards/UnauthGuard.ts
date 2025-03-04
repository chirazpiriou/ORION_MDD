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
export class UnauthGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    // Check if the user does not have a valid token
    const token = this.auth.getToken();
    if (!token) {
      return true; // Access granted for unauthenticated users
    } else {
      this.router.navigateByUrl('/article/all'); // Redirect if already authenticated
      return false;
    }
  }
}
