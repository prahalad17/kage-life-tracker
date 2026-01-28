import { Component, Input, Output, EventEmitter, OnChanges } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-confirm-dialog',
  imports: [CommonModule],
  templateUrl: './confirm-dialog.html',
  styleUrl: './confirm-dialog.css',
})
export class ConfirmDialog{

  @Input()
  title : string = '';

   @Input() 
   message: string = '';
   
   @Input() 
   dialogType: string = '';

   @Input() data: any;

  @Output()
  confirm = new EventEmitter<void>();

  @Output()
  cancel = new EventEmitter<void>();

  @Output()
  ok = new EventEmitter<void>();

}
