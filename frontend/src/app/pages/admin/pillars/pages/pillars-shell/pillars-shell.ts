import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-pillars-shell',
  imports: [RouterOutlet,RouterLink, RouterLinkActive],
  templateUrl: './pillars-shell.html',
  styleUrl: './pillars-shell.scss',
})
export class PillarsShellComponent {

}
