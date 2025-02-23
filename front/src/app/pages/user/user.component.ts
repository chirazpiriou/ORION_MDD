import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { Theme } from 'src/app/core/models/theme.model';
import { User } from 'src/app/core/models/user.model';
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
  userEmail: string = 'jun.wei@tech.com'; 

  constructor(private themesService: ThemeService,
    private fb: FormBuilder) { }

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
      if (this.userEmail) {  // Check if the user's email is defined
        this.themesService.getAllUserThemes(this.userEmail).subscribe({
          next: (themes) => this.themes = themes,  // On success, store themes in the 'themes' array
          error: (error) => console.error('Error loading themes', error),  // Handle error in loading themes
        });
      } else {
        console.error('User email is not defined.');  // Error if the email is not provided
      }
    }

    // Method to handle form submission
  onSubmit(): void {
    if (this.userForm.valid) {  // Check if the form is valid
      const userRequest = this.userForm.value;  // Extract form values as a plain object
      // Logic to handle form submission (currently just logging data)
      console.log('Form submitted', userRequest);  // Logging form data for now
    }
  }

  // Cleanup logic when the component is destroyed (cancel any ongoing subscriptions)
  ngOnDestroy(): void {
    this.destroy$.next(true);  // Emit a value to indicate component destruction
    this.destroy$.complete();  // Complete the subject to release resources
  }
  
}
