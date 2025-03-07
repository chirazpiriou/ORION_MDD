import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/AuthService';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent  {
  
  constructor(private router: Router, private authService: AuthService) {}

  shouldHideNavItems(): boolean {
   
    return this.router.url === '/login' || this.router.url === '/register';
  }

  shouldHideBackButton(): boolean {
    return  this.router.url === '/';
  }
  shouldHideHeader(): boolean {
    return this.router.url === '/';
  }

  back(): void {
    window.history.back();
  }

  logOut(): void {
    this.authService.logOut(); 
    this.router.navigate(['/login']); 
  }
}
