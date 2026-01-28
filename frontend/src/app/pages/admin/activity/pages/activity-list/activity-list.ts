import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Activity } from '../../models/activity.model';
import { ActivityService } from '../../service/activity.service';
import { CommonModule } from '@angular/common';
import { ConfirmDialog } from '../../../../../shared/components/confirm-dialog/confirm-dialog';
import { DataTable } from '../../../../../shared/components/data-table/data-table';
import { Overlay } from '../../../../../shared/components/overlay/overlay';
import { DataForm } from '../../../../../shared/components/data-form/data-form';
import { FormConfig } from '../../../../../shared/models/form/form-config';
import { TableConfig } from '../../../../../shared/models/table/table-config.model';
import { buildActivityFormConfig } from '../../models/activity-form-config';
import { CreateActivityRequest } from '../../models/create-activity-request';
import { UpdateActivityRequest } from '../../models/update-activity-request';
type DialogType = 'info' | 'delete' | '';

@Component({
  standalone: true,
  selector: 'app-activity-list',
  imports: [
    CommonModule,
        DataTable,
        Overlay,
        DataForm,
        ConfirmDialog],
  templateUrl: './activity-list.html',
  styleUrl: './activity-list.css',
})
export class ActivityListComponent  implements OnInit{

  constructor(private activityService: ActivityService) {}

  // ===== DATA =====
  activity$! : Observable<Activity[]>;

  selectedRow: Activity | null = null;
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
  tableConfig: TableConfig = {
      tableName: 'Activity',
      columns: [
        { key: 'name', header: 'Activity' },
        { key: 'description', header: 'Description' }
      ],
      actions: [
        { type: 'view', label: 'View' },
        { type: 'edit', label: 'Edit' },
        { type: 'delete', label: 'Delete', confirm: true }
      ],
      create: {
        enabled: true,
        label: 'Add New Activity'
      }
    };

  // ===== LIFECYCLE =====
  ngOnInit(): void {
    this.loadActivity();
  }

   loadActivity() {
    this.activity$ = this.activityService.getAll();
  }


     // ===== OVERLAY HANDLING =====
      openForm(
        mode: 'create' | 'view' | 'edit',
        title: string,
        row: Activity | null,
        closeOnBackdrop: boolean
      ) {
        this.formConfig = buildActivityFormConfig(mode);
        this.selectedRow = row;
    
        this.overlayState = {
          open: true,
          title,
          closeOnBackdrop
        };
      }
    
    
  
    // ===== TABLE ACTIONS =====
      onTableAction(event: { type: string; row: Activity }) {
        switch (event.type) {
          case 'view':
            this.openView(event.row);
            break;
    
          case 'edit':
            this.openEdit(event.row);
            break;
    
          case 'delete':
            this.openDelete(event.row);
            break;
        }
      }
  
       openCreate() {
          this.openForm('create', 'Add Activity', null, false);
        }
      
        openView(row: Activity) {
          this.openForm('view', 'View Activity', row, true);
        }
      
        openEdit(row: Activity) {
          this.openForm('edit', 'Edit Activity', row, false);
        }
  
        closeOverlay() {
        this.overlayState.open = false;
        this.formConfig = null;
        this.selectedRow = null;
        this.formErrorMessage = '';
      }
  
       // ===== DIALOG HANDLING =====
        openDelete(row: Activity) {
          this.selectedRow = row;
          this.dialogState = {
            open: true,
            title: 'Delete Activity',
            message: `Are you sure you want to delete activity: ${row.name}?`,
            type: 'delete'
          };
        }
      
        closeDialog() {
          this.dialogState = {
            open: false,
            title: '',
            message: '',
            type: ''
          };
        }
  
        onDialogConfirm(row: any) {
      if (this.dialogState.type !== 'delete') return;
  
      this.activityService.deleteActivity(row.id).subscribe({
        next: () => {
          this.closeDialog();
          this.loadActivity();
  
          this.dialogState = {
            open: true,
            title: 'Activity Deleted',
            message: `Activity deleted: ${row.email}`,
            type: 'info'
          };
        },
        error: () => {
          this.closeDialog();
          this.dialogState = {
            open: true,
            title: 'Error',
            message: `Failed to delete activity: ${row.email}`,
            type: 'info'
          };
        }
      });
    }
  
    // ===== FORM SUBMIT =====
      onFormSubmit(data: any) {
        if (!this.formConfig) return;
    
        if (this.formConfig.mode === 'create') {
          const request: CreateActivityRequest     = {
            name: data.name,
            description : data.description
          };
    
          this.activityService.createActivity(request).subscribe({
            next: activity => {
              this.closeOverlay();
              this.loadActivity();
    
              this.dialogState = {
                open: true,
                title: 'Activity Created',
                message: `New activity created: ${activity.name}`,
                type: 'info'
              };
            },
            error: err => {
              this.formErrorMessage = err?.message || 'Failed to create activity';
            }
          });
        }
    
        if (this.formConfig.mode === 'edit' && this.selectedRow) {
          const request: UpdateActivityRequest = {
            name: data.name,
            description : data.description,
            id: data.id
          };
    
          this.activityService.updateActivity(request).subscribe({
            next: activity => {
              this.closeOverlay();
              this.loadActivity();
    
              this.dialogState = {
                open: true,
                title: 'Activity Updated',
                message: `Activity updated: ${activity.name}`,
                type: 'info'
              };
            },
            error: err => {
              this.formErrorMessage = err?.message || 'Failed to update activity';
            }
          });
        }
      }

}
