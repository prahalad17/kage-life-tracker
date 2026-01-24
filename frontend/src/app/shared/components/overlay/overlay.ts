import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-overlay',
  imports: [CommonModule],
  templateUrl: './overlay.html',
  styleUrl: './overlay.css',
})
export class Overlay {

  @Input() open = false;
  @Input() title? : string;
  @Input() width: string = '600px';

  @Output() close = new EventEmitter<void>();

  onClose(){
    this.close.emit();
  }



}
