import { Component } from '@angular/core';
import { RouterLinkActive, RouterModule, RouterOutlet } from "@angular/router";

@Component({
  selector: 'app-pillar-shell',
  imports: [RouterModule,RouterOutlet,RouterLinkActive],
  templateUrl: './pillar-shell.html',
  styleUrl: './pillar-shell.css',
})
export class PillarShell {

}
