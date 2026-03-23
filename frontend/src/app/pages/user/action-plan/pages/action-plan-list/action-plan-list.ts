import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { DataTable } from '../../../../../shared/components/data-table/data-table';
import { Overlay } from '../../../../../shared/components/overlay/overlay';
import { DataForm } from '../../../../../shared/components/data-form/data-form';
import { ConfirmDialog } from '../../../../../shared/components/confirm-dialog/confirm-dialog';
import { ActionPlanService } from '../../service/action-plan.service';
import { ActionPlan } from '../../models/action-plan.model';
import { PageResponse } from '../../../../../shared/models/api/page-response.model';
import { BehaviorSubject, finalize, Observable, shareReplay, switchMap } from 'rxjs';
import { FormConfig } from '../../../../../shared/models/form/form-config';
import { SearchRequestDto } from '../../../../../shared/models/api/search-request.model';
import { TableConfig } from '../../../../../shared/models/table/table-config.model';
import { buildActionPlanFormConfig } from '../../models/action-plan-form-config';
import { CreateActionPlanReq } from '../../models/create-action-plan-request';
import { UpdateActionPlanReq } from '../../models/update-action-plan-resuest';

type DialogType = 'info' | 'delete' | '';

@Component({
  selector: 'app-action-plan-list',
  imports: [
     CommonModule,
    DataTable,
    Overlay,
    DataForm,
    ConfirmDialog
  ],
  templateUrl: './action-plan-list.html',
  styleUrl: './action-plan-list.scss',
})
export class ActionPlanList implements OnInit  {
    
    constructor(private actionPlanService: ActionPlanService) {}
    
      // ===== DATA =====
    actionPlan$!: Observable<PageResponse<ActionPlan>>;
    loading = false;
    
      selectedRow: ActionPlan | null = null;
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
      //   field: "actionPlanDate",
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
          tableName: 'Action Plans',
          columns: [
            { key: 'actionPlanName', header: 'Action Plan Name' },
            { key: 'actionPlanDate', header: 'Date' },
            { key: 'actionPlanStatus', header: 'Status' }
            
          ],
          actions: [
            { type: 'view', label: 'View' },
            { type: 'edit', label: 'Edit' },
            { type: 'delete', label: 'Delete', confirm: true }
          ],
          create: {
            enabled: true,
            label: 'Plan New Action'
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
  
         this.actionPlan$ = this.searchRequestSubject.pipe(
      switchMap(request => {
        this.loading = true;
  
        return this.actionPlanService.search(request).pipe(
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
          row: ActionPlan | null,
          closeOnBackdrop: boolean
        ) {
          this.formConfig = buildActionPlanFormConfig(mode);
          this.selectedRow = row;
      
          this.overlayState = {
            open: true,
            title,
            closeOnBackdrop
          };
        }
      
      
    
      // ===== TABLE ACTIONS =====
        onTableAction(event: { type: string; row: ActionPlan }) {
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
        
          openView(row: ActionPlan) {
            console.log(row);
            
            this.openForm('view', 'View Action Entry', row, true);
          }
        
          openEdit(row: ActionPlan) {
            this.openForm('edit', 'Edit Action Entry', row, false);
          }
    
          closeOverlay() {
          this.overlayState.open = false;
          this.formConfig = null;
          this.selectedRow = null;
          this.formErrorMessage = '';
        }
    
         // ===== DIALOG HANDLING =====
          openDelete(row: ActionPlan) {
            this.selectedRow = row;
            this.dialogState = {
              open: true,
              title: 'Delete Action Entry',
              message: `Are you sure you want to delete action entry: ${row.actionPlanName} for : ${row.actionPlanDate}?`,
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
    
        this.actionPlanService.deleteLog(row.id).subscribe({
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
            const request: CreateActionPlanReq = {

            actionPlanDate: data.actionPlanDate,
            actionPlanName: data.actionPlanName,
            actionPlanStatus: data.actionPlanStatus,
            actionPlanNature: data.actionPlanNature,
            actionPlanTrackingType: data.actionPlanTrackingType,
            activityId: data.activityId,
            pillarId: data.pillarName,
            actionPlanNotes: data.actionPlanNotes
            };
      
            this.actionPlanService.createLog(request).subscribe({
              next: actionPlan => {
                this.closeOverlay();
                this.loadActionEntrys();
      
                this.dialogState = {
                  open: true,
                  title: 'Action Entry Created',
                  message: `New log  created: ${actionPlan.actionPlanName}`,
                  type: 'info'
                };
              },
              error: err => {
                this.formErrorMessage = err?.message || 'Failed to create log ';
              }
            });
          }
      
          if (this.formConfig.mode === 'edit' && this.selectedRow) {
            const request: UpdateActionPlanReq = {
              actionPlanId: data.actionPlanId,
               actionPlanDate: data.actionPlanDate,
            actionPlanName: data.actionPlanName,
            actionPlanStatus: data.actionPlanStatus,
            actionPlanNature: data.actionPlanNature,
            actionPlanTrackingType: data.actionPlanTrackingType,
            activityId: data.activityId,
            pillarId: data.pillarName,
            actionPlanNotes: data.actionPlanNotes
            };
      
            this.actionPlanService.updateLog(request).subscribe({
              next: actionPlan  => {
                this.closeOverlay();
                this.loadActionEntrys();
      
                this.dialogState = {
                  open: true,
                  title: 'Action Entry Updated',
                  message: `Action Entry updated: ${actionPlan.actionPlanName}`,
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
