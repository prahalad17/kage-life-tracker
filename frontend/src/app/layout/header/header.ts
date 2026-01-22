import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../auth/services/auth.service';
import { Router } from '@angular/router';

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
    private router: Router
  ) {}


   ngOnInit(): void {
    const name = this.authService.getUserName();
     if (name) {
      this.userName = name;
    }
  }

   

 logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
