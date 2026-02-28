import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

@Component({
  standalone : true,
  selector: 'app-register',
  imports: [ReactiveFormsModule, CommonModule,RouterModule],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register implements OnInit{

  registerForm!: FormGroup;
  submitted = false;
  loading = false;
  successMessage = '';
  errorMessage = '';

  

   constructor(
    private fb: FormBuilder,
    private authService: AuthService,
     private router: Router,
      private cdr: ChangeDetectorRef  
  ) {}

   ngOnInit(): void {
    this.registerForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      name : ['',[Validators.required]],
      confirmPassword: ['', Validators.required]
    }, {
      validators: this.passwordMatchValidator
    });
  }

   passwordMatchValidator(form: FormGroup) {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { passwordMismatch: true };
  }

  onSubmit() {
    this.submitted = true;
    this.successMessage = '';
    this.errorMessage = '';

    if (this.registerForm.invalid) {
      return;
    }

    this.loading = true;



    const registerRequest = {
      email: this.registerForm.value.email,
      password: this.registerForm.value.password,
      name : this.registerForm.value.name
    };

    this.authService.register(registerRequest).subscribe({
      next: (response) => {
        this.successMessage = response.message;
        this.loading = false;
        this.registerForm.reset();
        this.submitted = false;
        this.router.navigate(['/check-email']);
      },
      error: (err) => {

       this.errorMessage = err.error?.message || 'Registration failed';
        this.loading = false;
        this.cdr.detectChanges()
      }
    });
  }

}
