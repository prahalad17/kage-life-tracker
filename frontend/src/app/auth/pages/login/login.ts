import { ChangeDetectionStrategy, ChangeDetectorRef, Component } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { LoginResponse } from '../../models/login-response';
import { AuthStateService } from '../../services/auth-state.service';
import { AuthUser } from '../../models/auth-user';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './login.html',
  styleUrls: ['./login.scss']
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
    private authState: AuthStateService,
    private router: Router,
    private cdr : ChangeDetectorRef
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


    //  ✅ store auth state (in memory)
        this.authState.setAccessToken(user.accessToken);

         const { accessToken, name, userRole } = user;

          const authUser: AuthUser= {accessToken, name, userRole };

        this.authState.setUser(authUser);


         // ✅ UI-only decision
        if (user.userRole === 'ROLE_ADMIN') {
          this.router.navigate(['/admin/dashboard']);
        } else {
          this.router.navigate(['/user/dashboard']);
        }

        this.loading = false;
    },
    error: err => {
      console.log(err)
       this.errorMessage = err.message || 'Login failed';
       this.loading = false;
      this.cdr.detectChanges()
    }
  });
  }
}
