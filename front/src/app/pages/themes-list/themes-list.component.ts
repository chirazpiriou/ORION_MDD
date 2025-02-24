import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';

import { Theme } from 'src/app/core/models/theme.model';
import { ThemeService } from 'src/app/core/services/ThemeService';

@Component({
  selector: 'app-themes-list',
  templateUrl: './themes-list.component.html',
  styleUrls: ['./themes-list.component.scss']
})
export class ThemesListComponent implements OnInit, OnDestroy {
  themes: Theme[] = [];  // Initialize with an empty array to avoid undefined errors
  private destroy$ = new Subject<void>(); // Subject to manage unsubscriptions


  constructor(private themesService: ThemeService) { }

  ngOnInit(): void {
    

    this.themesService.getAllThemes().subscribe({
      next: (themes) => this.themes = themes,
      error: (error) => console.error('Erreur lors du chargement des thèmes', error)
    });
  }

// Method for reloading themes
  reloadThemes(): void {
    this.themesService.getAllThemes().subscribe({
      next: (themes) => {
        console.log('Thèmes rechargés:', themes);
        this.themes = themes;
      },
      error: (error) => console.error('Erreur lors du rechargement des thèmes', error)
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next(); // Signal the observable to complete
    this.destroy$.complete(); // Clean up resources to prevent memory leaks
  }

}
