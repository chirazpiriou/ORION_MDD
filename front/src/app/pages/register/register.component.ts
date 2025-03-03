import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  FormGroup,
  FormControl,
  FormBuilder,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { RegisterRequest } from 'src/app/core/interfaces/registerRequest.interface';
import { Token } from 'src/app/core/interfaces/token.interface';
import { AuthService } from 'src/app/core/services/AuthService';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit, OnDestroy {
  private destroy$: Subject<boolean> = new Subject();
  errorStr: string = '';

  registerForm = new FormGroup({
    name: new FormControl(''),
    email: new FormControl(''),
    password: new FormControl(''),
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    const passwordPattern =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$/;
    this.registerForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      name: ['', [Validators.required]],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.pattern(passwordPattern),
        ],
      ],
    });
  }

  onSubmitForm(): void {
    if (this.registerForm.valid) {
      this.destroy$ = new Subject<boolean>();
      const registerRequest = this.registerForm.value as RegisterRequest;
      this.authService
        .register(registerRequest)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (response: Token) => {
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
