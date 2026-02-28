import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-users-shell',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './users-shell.html',
  styleUrl: './users-shell.scss',
})
export class UsersShell {

}
