import { Component, OnInit } from '@angular/core';
import { ConfirmDialog } from '../../../../../shared/components/confirm-dialog/confirm-dialog';
import { DataForm } from '../../../../../shared/components/data-form/data-form';
import { CommonModule } from '@angular/common';
import { DataTable } from '../../../../../shared/components/data-table/data-table';
import { Overlay } from '../../../../../shared/components/overlay/overlay';
import { BehaviorSubject, finalize, Observable, shareReplay, switchMap } from 'rxjs';
import { FormConfig } from '../../../../../shared/models/form/form-config';
import { TableConfig } from '../../../../../shared/models/table/table-config.model';
import { SearchRequestDto } from '../../../../../shared/models/api/search-request.model';
import { PageResponse } from '../../../../../shared/models/api/page-response.model';
import { ActionEntryService } from '../../service/action-entry.service';
import { ActionEntry } from '../../models/action-entry.model';
import { buildActionEntryFormConfig } from '../../models/action-entry-form-config';
import { CreateActionEntryReq } from '../../models/create-action-entry-request';
import { UpdateActionEntryReq } from '../../models/update-action-entry-resuest';

type DialogType = 'info' | 'delete' | '';


@Component({
  selector: 'app-action-entry-list',
  imports: [
    CommonModule,
    DataTable,
    Overlay,
    DataForm,
    ConfirmDialog
  ],
  templateUrl: './action-entry-list.html',
  styleUrl: './action-entry-list.scss',
})
export class ActionEntryList implements OnInit {
    
    constructor(private actionEntryService: ActionEntryService) {}
    
      // ===== DATA =====
    actionEntry$!: Observable<PageResponse<ActionEntry>>;
    loading = false;
    
      selectedRow: ActionEntry | null = null;
      formConfig: FormConfig | null = null;
      formErrorMessage = '';
  
      // ===== PAGINATION =====
  
      totalElements=0
      pageIndex=0
      pageSize=0
  
      private searchRequestSubject = new BehaviorSubject<SearchRequestDto>({
    page: 0,
    size: 10,
    sort: [
      {
        field: "logDate",
        direction: "DESC"
      }
    ],
    filters: []
  });
  
    
      
      // ===== OVERLAY STATE =====
      overlayState = {
        open: false,
        title: '',
        closeOnBackdrop: true,
        
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
            { key: 'completed', header: 'Completed' },
            { key: 'logDate', header: 'Date' }
          ],
          actions: [
            { type: 'view', label: 'View' },
            { type: 'edit', label: 'Edit' },
            { type: 'delete', label: 'Delete', confirm: true }
          ],
          create: {
            enabled: true,
            label: 'Add New Daily Log'
          },
          pagination:{
            enabled:true
          }
          // ,trackBy: "activityActionEntryId"
        };
    
      // ===== LIFECYCLE =====
      ngOnInit(): void {
        this.loadActionEntrys();
      }
  
    onPageChange(event: { pageIndex: number; pageSize: number }) {
  
      console.log('Parent received:', event);
      
  
    const current = this.searchRequestSubject.value;
  
    this.searchRequestSubject.next({
      ...current,
      page: event.pageIndex,
      size: event.pageSize
    });
  
  }
    
       loadActionEntrys() {
  
         this.actionEntry$ = this.searchRequestSubject.pipe(
      switchMap(request => {
        this.loading = true;
  
        return this.actionEntryService.search(request).pipe(
          finalize(() => this.loading = false)
        );
      }),
      shareReplay(1)
    );
  
  
       }
    
      
         // ===== OVERLAY HANDLING =====
        openForm(
          mode: 'create' | 'view' | 'edit',
          title: string,
          row: ActionEntry | null,
          closeOnBackdrop: boolean
        ) {
          this.formConfig = buildActionEntryFormConfig(mode);
          this.selectedRow = row;
      
          this.overlayState = {
            open: true,
            title,
            closeOnBackdrop
          };
        }
      
      
    
      // ===== TABLE ACTIONS =====
        onTableAction(event: { type: string; row: ActionEntry }) {
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
        
          openView(row: ActionEntry) {
            console.log(row);
            
            this.openForm('view', 'View Daily Log', row, true);
          }
        
          openEdit(row: ActionEntry) {
            this.openForm('edit', 'Edit Daily Log', row, false);
          }
    
          closeOverlay() {
          this.overlayState.open = false;
          this.formConfig = null;
          this.selectedRow = null;
          this.formErrorMessage = '';
        }
    
         // ===== DIALOG HANDLING =====
          openDelete(row: ActionEntry) {
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
    
        this.actionEntryService.deleteLog(row.id).subscribe({
          next: () => {
            this.closeDialog();
            this.loadActionEntrys();
    
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
            const request: CreateActionEntryReq = {
              activityId: data.activityId,
              actualValue: data.actualValue,
              completed: data.completed,
              notes:data.notes
            };
      
            this.actionEntryService.createLog(request).subscribe({
              next: dailyLog => {
                this.closeOverlay();
                this.loadActionEntrys();
      
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
            const request: UpdateActionEntryReq = {
              logId: data.activityActionEntryId,
              activityId: data.activityId,
              actualValue: data.actualValue,
              completed: data.completed,
              notes:data.notes
            };
      
            this.actionEntryService.updateLog(request).subscribe({
              next: log  => {
                this.closeOverlay();
                this.loadActionEntrys();
      
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