import { Component } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {

  loginForm: FormGroup;
  submitted = false;
  loading = false;
  successMessage = '';
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {

    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    const { email, password } = this.loginForm.value;
     const credentials = this.loginForm.value;

    this.authService.login(credentials).subscribe({
     next: user => {
      // console.log(user)
      // UI decision only
      if (user.userRole === 'ADMIN') {
        this.router.navigate(['admin/dashboard']);
      } else {
        this.router.navigate(['/dashboard']);
      }
    },
    error: err => {
       this.errorMessage = err.message || 'Login failed';
       this.loading = false;
      // this.cdr.detectChanges()
    }
  });
  }
}
