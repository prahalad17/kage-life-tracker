import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { DataTable } from '../../../../../shared/components/data-table/data-table';
import { CommonModule } from '@angular/common';
import { DashboardService } from '../../service/dashboard.service';
import { WidgetEvent } from '../../../../../shared/models/dashboard/widget-event';
import { WidgetRenderer } from '../../../../../shared/components/dashboard-engine/widget-renderer/widget-renderer';
import { dashboardConfig } from './dashboard-overview-config';
import { DashboardConfig } from '../../../../../shared/models/dashboard/dashboard-config';
type DialogType = 'info' | 'delete' | '';

@Component({
  selector: 'app-dashboard-overview',
  imports: [CommonModule,
    WidgetRenderer],
  templateUrl: './dashboard-overview.html',
  styleUrl: './dashboard-overview.scss',
})
export class DashboardOverview {

  constructor(private dashboardService : DashboardService) {}

   dashboardConfig: DashboardConfig = {
    id: 'home',
  
    layout: {
      type: 'grid',
      columns: 2
    },
  
    widgets: [
      {
        id: 'tasks',
        type: 'interactive-list',
        title: "Today's Tasks",
        dataKey: 'tasks'
      },
      {
        id: 'actions',
        type: 'list',
        title: 'Completed Actions',
        dataKey: 'actions'
      },
      {
        id: 'sleep',
        type: 'metric',
        title: 'Sleep',
        dataKey: 'sleepData'
      },
      {
        id: 'note',
        type: 'text',
        title: 'Daily Note',
        dataKey: 'note'
      }
    ]
  };

 dashboardData : Record<string, any> = {
  tasks: {
    items: [
      { id: '1', title: 'Workout', completed: false },
      { id: '2', title: 'Study', completed: true }
    ]
  },

  actions: {
    items: [
      { id: '1', title: 'Ran 5km', subtitle: 'Morning' }
    ]
  },

  sleepData: {
    value: 6,
    unit: 'hrs',
    label: 'Last Night'
  },

  note: {
    content: 'Felt productive today. Keep going.'
  }
};

 handleWidgetEvent(event: WidgetEvent) {
    console.log('Widget Event:', event);

    if (event.actionType === 'TOGGLE') {
      console.log('Toggle item:', event.payload.itemId);

      // later → call API here
    }
  }
  

}
