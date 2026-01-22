import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoaderService } from '../../../core/services/loader.service';

@Component({
  standalone: true,
  selector: 'app-loader',
  imports: [CommonModule],
  template: `
    <div class="loader-backdrop" *ngIf="loaderService.loading$ | async">
      <div class="spinner"></div>
    </div>
  `,
  styleUrls: ['./loader.component.css']
})
export class LoaderComponent {
  constructor(public loaderService: LoaderService) {}
}
