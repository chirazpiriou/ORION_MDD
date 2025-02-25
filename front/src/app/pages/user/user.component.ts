import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subject, takeUntil } from 'rxjs';
import { Theme } from 'src/app/core/models/theme.model';
import { User } from 'src/app/core/models/user.model';
import { AuthService } from 'src/app/core/services/AuthService';
import { ThemeService } from 'src/app/core/services/ThemeService';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  public userForm!: FormGroup;  // Declaring the form group for the user form
  private destroy$: Subject<boolean> = new Subject();  // Subject to manage subscription lifecycle
  errorStr: string = '';  // Variable to store error messages
  themes!: Theme[];  // Array to store themes associated with the user
  public user!:User;
 

  constructor(private themesService: ThemeService,
    private fb: FormBuilder, private authService: AuthService) { }

  ngOnInit(): void {
    // Password pattern to ensure strong passwords (at least one lowercase, one uppercase, one digit, and one special character)
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$/;
    this.userForm = this.fb.group({
      name: [''],  // Name field (can be empty initially)
      email: ['', [Validators.email]],  // Email field with email validation
      password: ['', [Validators.minLength(8), Validators.pattern(passwordPattern)]],  // Password field with validation rules
    });
    this.loadThemes();  // Loading themes on component initialization
  }

    // Method to load themes associated with the user from the ThemeService
    loadThemes(): void {
     
      this.themesService.getAllUserThemes().subscribe({
        next: (themes) => this.themes = themes,  // On success, store themes in the 'themes' array
        error: (error) => console.error('Error loading themes', error),  // Handle error in loading themes
      });
      
    }

    onSubmit() {
      if (this.userForm.valid) {  
      this.destroy$ = new Subject<boolean>();
      const user = this.userForm.value as User;
      this.authService.update(user)
      .pipe(
        takeUntil(this.destroy$))
      .subscribe({
        next: (response) => {
        },
        error: (error) => {
          this.errorStr =
            error || '..................Une erreur est survenue lors de la connexion.';
        },
      });
    }}
  

  // Cleanup logic when the component is destroyed (cancel any ongoing subscriptions)
  ngOnDestroy(): void {
    this.destroy$.next(true);  // Emit a value to indicate component destruction
    this.destroy$.complete();  // Complete the subject to release resources
  }
  
}
