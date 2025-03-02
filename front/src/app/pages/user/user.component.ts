import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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
    private fb: FormBuilder, private authService: AuthService, private router: Router  ) { }

  ngOnInit(): void {
    // Password pattern to ensure strong passwords (at least one lowercase, one uppercase, one digit, and one special character)
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$/;
    this.userForm = this.fb.group({
      name: [''],  // Name field (can be empty initially)
      email: ['', [Validators.email]],  // Email field with email validation
      password: ['', [Validators.minLength(8), Validators.pattern(passwordPattern)]],  // Password field with validation rules
    });
    this.loadThemes();  // Loading themes on component initialization
    this.authService.get_profile().subscribe(profile => { 
      console.log(profile);
      this.user = profile; // Récupérer le profil utilisateur
      console.log(this.user.name);
      this.userForm.patchValue({  // Mettre à jour le formulaire avec les données de l'utilisateur
        name: this.user.name,
        email: this.user.email
      });
    });
  }

    // Method to load themes associated with the user from the ThemeService
    loadThemes(): void {
     
      this.themesService.getAllUserThemes().subscribe({
        next: (themes) => this.themes = themes,  // On success, store themes in the 'themes' array
        error: (error) => console.error('Error loading themes', error),  // Handle error in loading themes
      });
      
    }

    onSubmit(): void {
      if (this.userForm.valid) {
        const user: User = this.userForm.value;
    
        this.authService.update(user)
          .pipe(takeUntil(this.destroy$))
          .subscribe({
            next: () => {
              console.log('Utilisateur mis à jour avec succès');
              this.authService.get_profile().subscribe(profile => { 
                this.user = profile; // Recharger les infos utilisateur
                this.loadThemes(); // Recharger les abonnements
              });
            },
            error: (error) => {
              this.errorStr = error || 'Une erreur est survenue lors de la mise à jour de votre profil.';
            }
          });
      }
    }

    logOut(): void {
      this.authService.logOut();
      // Rediriger l'utilisateur vers la page de connexion ou d'accueil
      this.router.navigate(['/login']);
    }

  // Cleanup logic when the component is destroyed (cancel any ongoing subscriptions)
  ngOnDestroy(): void {
    this.destroy$.next(true);  // Emit a value to indicate component destruction
    this.destroy$.complete();  // Complete the subject to release resources
  }
  
}
