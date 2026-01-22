import { Component, Input ,Output, EventEmitter } from '@angular/core';
import { Activity } from '../../models/activity.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-activity-table',
  imports: [CommonModule],
  templateUrl: './activity-table.html',
  styleUrl: './activity-table.css',
})
export class ActivityTable {

  @Input() activities : Activity[] =[];

 @Output()
  edit = new EventEmitter<Activity>();
   onEdit(activity: Activity): void {
    this.edit.emit(activity);
  }

@Output()
  delete = new EventEmitter<Activity>();
  onDelete(activity: Activity): void {
    this.delete.emit(activity);
  }

}
