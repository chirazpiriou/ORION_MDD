import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { ArticleRequest } from 'src/app/core/models/articleRequest.model';
import { Theme } from 'src/app/core/models/theme.model';
import { ArticlesService } from 'src/app/core/services/ArticlesService';
import { ThemeService } from 'src/app/core/services/ThemeService';


@Component({
  selector: 'app-article-create',
  templateUrl: './article-create.component.html',
  styleUrls: ['./article-create.component.scss']
})
export class ArticleCreateComponent implements OnInit, OnDestroy {

  public articleForm!: FormGroup;
  private destroy$ = new Subject<void>(); // Used to clean up subscriptions when the component is destroyed
  public errorStr: string = '';
  public themes: Theme[] = []; // Stores the available themes

  constructor(
    private themesService: ThemeService,
    private articlesService: ArticlesService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initializeForm();
    this.loadThemes();
  }

  /**
   * Initializes the reactive form with validation rules
   */
  private initializeForm(): void {
    this.articleForm = this.fb.group({
      theme: ['', Validators.required],
      titre: ['', Validators.required],
      contenu: ['', Validators.required]
    });
  }

  /**
   * Loads available themes from the service
   */
  private loadThemes(): void {
    
    this.themesService.getAllThemes().subscribe({
      next: (themes) => (this.themes = themes),
      error: (error) => console.error('Error loading themes:', error)
    });
  }

  /**
   * Handles form submission, creates a new article if valid
   */
  public onSubmitForm(): void {
    if (this.articleForm.invalid) return; // Prevent submission if the form is invalid

    const articleRequest: ArticleRequest = this.articleForm.value;
   
    this.articlesService.create(articleRequest )
      .pipe(takeUntil(this.destroy$)) // Unsubscribe when the component is destroyed
      .subscribe({
        next: () => {
          this.errorStr = '';
          this.router.navigate(['article/all']); // Redirect after successful submission
        },
        error: (error) => {
          this.errorStr = error?.message || 'An error occurred while submitting the article.';
        }
      });
  }

  /**
   * Cleans up subscriptions to avoid memory leaks
   */
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

}
