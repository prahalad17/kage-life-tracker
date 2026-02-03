import { Component, OnInit } from '@angular/core';
import { ConfirmDialog } from '../../../../../shared/components/confirm-dialog/confirm-dialog';
import { DataForm } from '../../../../../shared/components/data-form/data-form';
import { CommonModule } from '@angular/common';
import { DataTable } from '../../../../../shared/components/data-table/data-table';
import { Overlay } from '../../../../../shared/components/overlay/overlay';
import { DailyLog } from '../../models/daily-log.model';
import { Observable } from 'rxjs';
import { DailyLogService } from '../../service/daily-log.service';
import { FormConfig } from '../../../../../shared/models/form/form-config';
import { TableConfig } from '../../../../../shared/models/table/table-config.model';
import { buildDailyLogFormConfig } from '../../models/daily-log-form-config';
import { CreateDailyLogReq } from '../../models/create-daily-log-request';
import { UpdateDailyLogReq } from '../../models/update-daily-log-resuest';

type DialogType = 'info' | 'delete' | '';

@Component({
  selector: 'app-daily-log-list',
  imports: [
     CommonModule,
    DataTable,
    Overlay,
    DataForm,
    ConfirmDialog
  ],
  templateUrl: './daily-log-list.html',
  styleUrl: './daily-log-list.css',
})

export class DailyLogList implements OnInit {

  
  
    constructor(private dailyLogService: DailyLogService) {}
  
    // ===== DATA =====
    dailyLogs$!: Observable<DailyLog[]>;
  
    selectedRow: DailyLog | null = null;
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
        tableName: 'Daily Log',
        columns: [
          { key: 'activityName', header: 'Activity Name' },
          { key: 'notes', header: 'Notes' },
          { key: 'completed', header: 'Completed' },
          { key: 'actualValue', header: 'Count' }
        ],
        actions: [
          { type: 'view', label: 'View' },
          { type: 'edit', label: 'Edit' },
          { type: 'delete', label: 'Delete', confirm: true }
        ],
        create: {
          enabled: true,
          label: 'Add New Daily Log'
        }
      };
  
    // ===== LIFECYCLE =====
    ngOnInit(): void {
      this.loadDailyLogs();
    }
  
     loadDailyLogs() {
      this.dailyLogs$ = this.dailyLogService.getAll();
    }
  
    
       // ===== OVERLAY HANDLING =====
      openForm(
        mode: 'create' | 'view' | 'edit',
        title: string,
        row: DailyLog | null,
        closeOnBackdrop: boolean
      ) {
        this.formConfig = buildDailyLogFormConfig(mode);
        this.selectedRow = row;
    
        this.overlayState = {
          open: true,
          title,
          closeOnBackdrop
        };
      }
    
    
  
    // ===== TABLE ACTIONS =====
      onTableAction(event: { type: string; row: DailyLog }) {
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
          this.openForm('create', 'Add Daily Log', null, false);
        }
      
        openView(row: DailyLog) {
          console.log(row);
          
          this.openForm('view', 'View Daily Log', row, true);
        }
      
        openEdit(row: DailyLog) {
          this.openForm('edit', 'Edit Daily Log', row, false);
        }
  
        closeOverlay() {
        this.overlayState.open = false;
        this.formConfig = null;
        this.selectedRow = null;
        this.formErrorMessage = '';
      }
  
       // ===== DIALOG HANDLING =====
        openDelete(row: DailyLog) {
          this.selectedRow = row;
          this.dialogState = {
            open: true,
            title: 'Delete Daily Log',
            message: `Are you sure you want to delete daily log: ${row.activityName}?`,
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
  
      this.dailyLogService.deletePillar(row.id).subscribe({
        next: () => {
          this.closeDialog();
          this.loadDailyLogs();
  
          this.dialogState = {
            open: true,
            title: 'Daily Log Deleted',
            message: `Daily Log deleted: ${row.email}`,
            type: 'info'
          };
        },
        error: () => {
          this.closeDialog();
          this.dialogState = {
            open: true,
            title: 'Error',
            message: `Failed to delete log : ${row.email}`,
            type: 'info'
          };
        }
      });
    }
  
    // ===== FORM SUBMIT =====
      onFormSubmit(data: any) {
        if (!this.formConfig) return;
    
        if (this.formConfig.mode === 'create') {
          const request: CreateDailyLogReq = {
            activityId: data.activityId,
            actualValue: data.actualValue,
            completed: data.completed,
            notes:data.notes
          };
    
          this.dailyLogService.createPillar(request).subscribe({
            next: dailyLog => {
              this.closeOverlay();
              this.loadDailyLogs();
    
              this.dialogState = {
                open: true,
                title: 'Daily Log Created',
                message: `New log  created: ${dailyLog.activityName}`,
                type: 'info'
              };
            },
            error: err => {
              this.formErrorMessage = err?.message || 'Failed to create log ';
            }
          });
        }
    
        if (this.formConfig.mode === 'edit' && this.selectedRow) {
          const request: UpdateDailyLogReq = {
            activityDailyLogId: data.activityDailyLogId,
            activityId: data.activityId,
            actualValue: data.actualValue,
            completed: data.completed,
            notes:data.notes
          };
    
          this.dailyLogService.updatePillar(request).subscribe({
            next: log  => {
              this.closeOverlay();
              this.loadDailyLogs();
    
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
        }
      }

}
