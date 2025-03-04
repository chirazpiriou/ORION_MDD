import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil, tap } from 'rxjs';
import { AuthService } from 'src/app/core/services/AuthService';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit, OnDestroy {
  public loginForm!: FormGroup;
  private destroy$: Subject<boolean> = new Subject();
  errorStr: string = '';

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      identifier: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  onSubmitForm(): void {
    if (this.loginForm.valid) {
      this.destroy$ = new Subject<boolean>();
      const loginRequest = this.loginForm.value;

      this.authService
        .login(loginRequest)
        .pipe(takeUntil(this.destroy$))

        .subscribe({
          next: (response) => {
            localStorage.setItem('token', response.token);
            this.router.navigate(['article/all']);
          },
          error: (error) => {
            this.errorStr =
              error.error ||
              '..................Une erreur est survenue lors de la connexion.';
          },
        });
    }
  }
  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.complete();
  }
}
