import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-daily-log-shell',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './daily-log-shell.html',
  styleUrl: './daily-log-shell.scss',
})
export class DailyLogShell {

}
