import { Component, OnInit } from '@angular/core';
import { UserActivityService } from '../../service/user-activity.service';
import { UserActivity } from '../../models/user-activity.model';
import { Observable } from 'rxjs';
import { FormConfig } from '../../../../../shared/models/form/form-config';
import { TableConfig } from '../../../../../shared/models/table/table-config.model';
import { buildUserActivityFormConfig } from '../../models/user-activity-form-config';
import { CreateUserActivityRequest } from '../../models/create-user-activity-request';
import { UpdateUserActivityRequest } from '../../models/update-user-activity-request';
import { CommonModule } from '@angular/common';
import { DataTable } from '../../../../../shared/components/data-table/data-table';
import { Overlay } from '../../../../../shared/components/overlay/overlay';
import { DataForm } from '../../../../../shared/components/data-form/data-form';
import { ConfirmDialog } from '../../../../../shared/components/confirm-dialog/confirm-dialog';

type DialogType = 'info' | 'delete' | '';

@Component({
  selector: 'app-activity-list',
  imports: [
     CommonModule,
        DataTable,
        Overlay,
        DataForm,
        ConfirmDialog
  ],
  templateUrl: './activity-list.html',
  styleUrl: './activity-list.css',
})
export class ActivityList implements OnInit{

  constructor(private userActivityService: UserActivityService) {}
  
    // ===== DATA =====
    userActivity$! : Observable<UserActivity[]>;
  
    selectedRow: UserActivity | null = null;
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
        tableName: 'User Activity',
        columns: [
          { key: 'name', header: 'User Activity' },
          { key: 'description', header: 'Description' }
        ],
        actions: [
          { type: 'view', label: 'View' },
          { type: 'edit', label: 'Edit' },
          { type: 'delete', label: 'Delete', confirm: true }
        ],
        create: {
          enabled: true,
          label: 'Add New User Activity'
        }
      };
  
    // ===== LIFECYCLE =====
    ngOnInit(): void {
      this.loadActivity();
    }
  
     loadActivity() {
      this.userActivity$ = this.userActivityService.getAll();
    }
  
  
       // ===== OVERLAY HANDLING =====
        openForm(
          mode: 'create' | 'view' | 'edit',
          title: string,
          row: UserActivity | null,
          closeOnBackdrop: boolean
        ) {
          this.formConfig = buildUserActivityFormConfig(mode);
          this.selectedRow = row;
      
          this.overlayState = {
            open: true,
            title,
            closeOnBackdrop
          };
        }
      
      
    
      // ===== TABLE ACTIONS =====
        onTableAction(event: { type: string; row: UserActivity }) {
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
            this.openForm('create', 'Add User Activity', null, false);
          }
        
          openView(row: UserActivity) {
            this.openForm('view', 'View User Activity', row, true);
          }
        
          openEdit(row: UserActivity) {
            this.openForm('edit', 'Edit User Activity', row, false);
          }
    
          closeOverlay() {
          this.overlayState.open = false;
          this.formConfig = null;
          this.selectedRow = null;
          this.formErrorMessage = '';
        }
    
         // ===== DIALOG HANDLING =====
          openDelete(row: UserActivity) {
            this.selectedRow = row;
            this.dialogState = {
              open: true,
              title: 'Delete User Activity',
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
    
        this.userActivityService.deleteActivity(row.id).subscribe({
          next: () => {
            this.closeDialog();
            this.loadActivity();
    
            this.dialogState = {
              open: true,
              title: 'User Activity Deleted',
              message: `User Activity deleted: ${row.email}`,
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
            const request: CreateUserActivityRequest     = {
             name: data.name,
            description: data.description,
            activityNature: data.activityNature,
            pillarId: data.pillar,
            defaultTrackingType: data.defaultTrackingType,
            defaultUnit: data.defaultUnit
            };
      
            this.userActivityService.createActivity(request).subscribe({
              next: activity => {
                this.closeOverlay();
                this.loadActivity();
      
                this.dialogState = {
                  open: true,
                  title: 'User Activity Created',
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
            const request: UpdateUserActivityRequest = {
              activityId: data.activityId,
            name: data.name,
            description: data.description,
            activityNature: data.activityNature,
            pillarTemplateId: data.pillar,
            defaultTrackingType: data.defaultTrackingType,
            defaultUnit: data.defaultUnit
            };
      
            this.userActivityService.updateActivity(request).subscribe({
              next: activity => {
                this.closeOverlay();
                this.loadActivity();
      
                this.dialogState = {
                  open: true,
                  title: 'User Activity Updated',
                  message: `User Activity updated: ${activity.name}`,
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
