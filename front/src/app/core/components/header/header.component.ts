import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent  {
  
  constructor(private router: Router) {}

  shouldHideBackButton(): boolean {
    return this.router.url === '/login' || this.router.url === '/register' || this.router.url === '/';
  }
  shouldHideHeader(): boolean {
    return this.router.url === '/';
  }
  
    

  back(): void {
    window.history.back();
  }
}
