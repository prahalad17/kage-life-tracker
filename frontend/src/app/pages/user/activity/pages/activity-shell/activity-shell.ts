import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';


@Component({
  selector: 'app-activity-shell',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './activity-shell.html',
  styleUrl: './activity-shell.css',
})
export class ActivityShell {

}
