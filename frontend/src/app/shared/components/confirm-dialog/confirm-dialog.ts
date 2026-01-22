import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-confirm-dialog',
  imports: [CommonModule],
  templateUrl: './confirm-dialog.html',
  styleUrl: './confirm-dialog.css',
})
export class ConfirmDialog {

  @Input()
  title = 'Confirm';

  @Input()
  message = 'Are you sure?';

  @Output()
  confirm = new EventEmitter<void>();

  @Output()
  cancel = new EventEmitter<void>();

}
