import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Activity } from '../../models/activity.model';

@Component({
  selector: 'app-activity-form',
  imports: [],
  templateUrl: './activity-form.html',
  styleUrl: './activity-form.css',
})
export class ActivityForm {

    @Input() activity : Activity |null =null;

    @Output()
      save = new EventEmitter<Activity>();
    
      @Output()
      cancel = new EventEmitter<void>();


    activityName = '';

   ngOnInit(): void {
    if (this.activity) {
      this.activityName = this.activity.name;
    }
  }

   onSave(): void {
      const result: Activity = {
        id: this.activity?.id ?? Date.now(),
        name: this.activityName
      };
  
      this.save.emit(result);
    }


}
