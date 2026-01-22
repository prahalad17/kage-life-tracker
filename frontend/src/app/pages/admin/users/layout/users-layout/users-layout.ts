import { Component } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-users-layout',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './users-layout.html',
  styleUrl: './users-layout.css',
})
export class UsersLayoutComponent {

}
