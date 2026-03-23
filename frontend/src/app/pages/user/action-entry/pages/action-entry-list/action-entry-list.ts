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
            // {
            //   field: "actionEntryDate",
            //   direction: "DESC"
            // }
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
          tableName: 'Action Entries',
          columns: [
            { key: 'actionEntryName', header: 'Action Entry Name' },
            { key: 'actionEntryDate', header: 'Date' },
            { key: 'actionEntryStatus', header: 'Status' }
            
          ],
          actions: [
            { type: 'view', label: 'View' },
            { type: 'edit', label: 'Edit' },
            { type: 'delete', label: 'Delete', confirm: true }
          ],
          create: {
            enabled: true,
            label: 'Log New Action Entry'
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
            this.openForm('create', 'Add Action Entry', null, false);
          }
        
          openView(row: ActionEntry) {
            console.log(row);
            
            this.openForm('view', 'View Action Entry', row, true);
          }
        
          openEdit(row: ActionEntry) {
            this.openForm('edit', 'Edit Action Entry', row, false);
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
              title: 'Delete Action Entry',
              message: `Are you sure you want to delete action entry: ${row.actionEntryName} for : ${row.actionEntryDate}?`,
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
              title: 'Action Entry Deleted',
              message: `Action Entry deleted: ${row.email}`,
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

            actionEntryDate: data.actionEntryDate,
            actionEntryName: data.actionEntryName,
            actionEntryStatus: data.actionEntryStatus,
            actionEntryNature: data.actionEntryNature,
            actionEntryTrackingType: data.actionEntryTrackingType,
            activityId: data.activityId,
            pillarId: data.pillarName,
            actionEntryNotes: data.actionEntryNotes
            };
      
            this.actionEntryService.createLog(request).subscribe({
              next: actionEntry => {
                this.closeOverlay();
                this.loadActionEntrys();
      
                this.dialogState = {
                  open: true,
                  title: 'Action Entry Created',
                  message: `New log  created: ${actionEntry.actionEntryName}`,
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
              actionEntryId: data.actionEntryId,
               actionEntryDate: data.actionEntryDate,
            actionEntryName: data.actionEntryName,
            actionEntryStatus: data.actionEntryStatus,
            actionEntryNature: data.actionEntryNature,
            actionEntryTrackingType: data.actionEntryTrackingType,
            activityId: data.activityId,
            pillarId: data.pillarName,
            actionEntryNotes: data.actionEntryNotes
            };
      
            this.actionEntryService.updateLog(request).subscribe({
              next: actionEntry  => {
                this.closeOverlay();
                this.loadActionEntrys();
      
                this.dialogState = {
                  open: true,
                  title: 'Action Entry Updated',
                  message: `Action Entry updated: ${actionEntry.actionEntryName}`,
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