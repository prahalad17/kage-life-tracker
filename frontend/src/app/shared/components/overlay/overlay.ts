import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-overlay',
  imports: [CommonModule],
  templateUrl: './overlay.html',
  styleUrl: './overlay.scss',
})
export class Overlay {

  @Input() open = false;
 @Input() title = '';
@Input() size: 'sm' | 'md' | 'lg' = 'md';
@Input() variant: 'drawer' | 'center' = 'drawer';
@Input() showFooter = false;

  @Input() closeOnBackdropClick = true;

  @Output() close = new EventEmitter<void>();

  onClose(){
    this.close.emit();
  }

   onBackdropClick() {
    if (this.closeOnBackdropClick) {
      this.close.emit();
    }
  }

}
