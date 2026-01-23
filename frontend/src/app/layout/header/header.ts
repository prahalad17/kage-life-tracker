import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../auth/services/auth.service';
import { Router } from '@angular/router';
import { AuthStateService } from '../../auth/services/auth-state.service';

@Component({
  standalone: true,
  selector: 'app-header',
  imports: [],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class HeaderComponent {

  userName: string = 'Guest';

  constructor(
    private authService: AuthService,
    private router: Router,
    private authStateService : AuthStateService
  ) {}


   ngOnInit(): void {
    const name = this.authStateService.getUserName();
     if (name) {
      this.userName = name;
    }
  }

   

 logout() {
    this.authService.logout().subscribe({
    next: () => {
      this.authStateService.clear();
      this.router.navigate(['/']);
    },
    error: err => {
      console.error('Logout failed', err);
      //this.router.navigate(['/']); // still navigate if you want
    }
  });

  }
}
