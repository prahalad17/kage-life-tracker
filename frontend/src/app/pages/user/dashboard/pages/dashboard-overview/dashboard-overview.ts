import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { FormConfig } from '../../../../../shared/models/form/form-config';
import { TableConfig } from '../../../../../shared/models/table/table-config.model';
import { DataTable } from '../../../../../shared/components/data-table/data-table';
import { CommonModule } from '@angular/common';
import { DashboardService } from '../../service/dashboard.service';
import { ActionEntry } from '../../../action-entry/models/action-entry.model';
import { UpdateActionEntryReq } from '../../../action-entry/models/update-action-entry-resuest';
type DialogType = 'info' | 'delete' | '';

@Component({
  selector: 'app-dashboard-overview',
  imports: [CommonModule,
    DataTable],
  templateUrl: './dashboard-overview.html',
  styleUrl: './dashboard-overview.scss',
})
export class DashboardOverview {

  constructor(private dashboardService : DashboardService) {}

  // ===== DATA =====
  tasksTodo$!: Observable<ActionEntry[]>;

  tasksCompleted$!: Observable<ActionEntry[]>;

  selectedRow: ActionEntry | null = null;
  formConfig: FormConfig | null = null;
  formErrorMessage = '';

  // ===== OVERLAY STATE =====
    overlayState = {
      open: false,
      title: '',
      closeOnBackdrop: true
    };

    // ===== DIALOG STATE =====
    dialogState = {
      open: false,
      title: '',
      message: '',
      type: '' as DialogType  
    };

    // ===== TABLE CONFIG =====
  toDoTableConfig: TableConfig = {
      tableName: 'Today\'s Tasks',
      columns: [
        { key: 'activityName', header: 'Activity Name' }
      ],
      actions: [
        // { type: 'view', label: 'View' },
        { type: 'edit', label: 'Done' },
        // { type: 'delete', label: 'Delete', confirm: true }
      ]
    };

  completedTableConfig: TableConfig = {
    tableName: 'Today\'s Tasks',
    columns: [
      { key: 'activityName', header: 'Activity Name' }
    ],
    actions: [
      // { type: 'edit', label: 'Undone'},
    ]
  };


  // ===== LIFECYCLE =====
    ngOnInit(): void {
      this.loadToDo();
      this.loadCompleted();
    }
  
     loadToDo() {
      this.tasksTodo$ = this.dashboardService.getToDo();
    }

    loadCompleted() {
      this.tasksCompleted$ = this.dashboardService.getDone();
    }


    // ===== TABLE ACTIONS =====
      onTableAction(event: { type: string; row: ActionEntry }) {

        console.log(event);
        switch (event.type) {
          
          
          case 'edit':
          const request: UpdateActionEntryReq = {
                      logId: event.row.activityDailyLogId,
                      activityId:  event.row.activityId,
                      actualValue:  event.row.actualValue,
                      completed: true,
                      notes: event.row.notes
                    };

          this.dashboardService.updateLog(request).subscribe({
            next: log  => {
              this.loadToDo();
              this.loadCompleted();
    
              this.dialogState = {
                open: true,
                title: 'Daily Log Updated',
                message: `Daily Log updated: ${log.activityName}`,
                type: 'info'
              };
            },
            error: err => {
              this.formErrorMessage = err?.message || 'Failed to update log ';
            }
          });

            break;
        }
      }


      scheduleTodayTasks(){

         const today = new Date().toISOString().split('T')[0];

         this.dashboardService.schedule(today).subscribe({
            next: log  => {
              this.loadToDo();
              this.loadCompleted();
    
              // this.dialogState = {
              //   open: true,
              //   title: 'Daily Log Updated',
              //   message: `Daily Log updated: ${log.activityName}`,
              //   type: 'info'
              // };
            },
            error: err => {
              this.formErrorMessage = err?.message || 'Failed to update log ';
            }
          });

      }
  

}
