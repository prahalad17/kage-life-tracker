import { Component, OnInit } from '@angular/core';
import { UserPillarService } from '../../service/user.pillar.service';
import { UserPillar } from '../../model/user-pillar.model';
import { Observable } from 'rxjs';
import { FormConfig } from '../../../../../shared/models/form/form-config';
import { TableConfig } from '../../../../../shared/models/table/table-config.model';
import { buildUserPillarFormConfig } from '../../model/user-pillar-form-config';
import { CreateUserPillarRequest } from '../../model/create-user-pillar-request';
import { UpdateUserPillarRequest } from '../../model/update-user-pillar-request';
import { CommonModule } from '@angular/common';
import { DataTable } from '../../../../../shared/components/data-table/data-table';
import { Overlay } from '../../../../../shared/components/overlay/overlay';
import { DataForm } from '../../../../../shared/components/data-form/data-form';
import { ConfirmDialog } from '../../../../../shared/components/confirm-dialog/confirm-dialog';
import { Pillar } from '../../../../admin/pillars/models/pillar.model';
import { AuthStateService } from '../../../../../auth/services/auth-state.service';

type DialogType = 'info' | 'delete' | '';
@Component({
  selector: 'app-pillar-list',
  imports: [
    CommonModule,
    DataTable,
    Overlay,
    DataForm,
    ConfirmDialog
  ],
  templateUrl: './pillar-list.html',
  styleUrl: './pillar-list.css',
})
export class PillarList implements OnInit {

  constructor(private userPillarService: UserPillarService , private authStateService : AuthStateService) {}
  
    // ===== DATA =====
    userPillars$!: Observable<UserPillar[]>;

    userAvailablePillars$!: Observable<Pillar[]>;


  
    selectedRow: UserPillar | null = null;
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
  
    // ===== My Pillar TABLE CONFIG =====
    tableConfig: TableConfig = {
        tableName: 'My Pillars',
        columns: [
          { key: 'pillarName', header: 'Pillar' },
          { key: 'description', header: 'Description' }
        ],
        actions: [
          { type: 'view', label: 'View' },
          { type: 'edit', label: 'Edit' },
          { type: 'delete', label: 'Delete', confirm: true }
        ],
        create: {
          enabled: true,
          label: 'Add New Pillar'
        }
      };

   
  
    // ===== LIFECYCLE =====
    ngOnInit(): void {
      this.loadPillars();
       this.loadUserAvailablePillars();
    }
  
     loadPillars() {
      this.userPillars$ = this.userPillarService.getAll();
    }

     loadUserAvailablePillars() {
      this.userPillars$ = this.userPillarService.getAll();
    }
  
    
       // ===== OVERLAY HANDLING =====
      openForm(
        mode: 'create' | 'view' | 'edit',
        title: string,
        row: UserPillar | null,
        closeOnBackdrop: boolean
      ) {
        this.formConfig = buildUserPillarFormConfig(mode);
        this.selectedRow = row;
    
        this.overlayState = {
          open: true,
          title,
          closeOnBackdrop
        };
      }
    
    
  
    // ===== TABLE ACTIONS =====
      onTableAction(event: { type: string; row: UserPillar }) {
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
          this.openForm('create', 'Add Pillar', null, false);
        }
      
        openView(row: UserPillar) {
          this.openForm('view', 'View Pillar', row, true);
        }
      
        openEdit(row: UserPillar) {
          this.openForm('edit', 'Edit Pillar', row, false);
        }
  
        closeOverlay() {
        this.overlayState.open = false;
        this.formConfig = null;
        this.selectedRow = null;
        this.formErrorMessage = '';
      }
  
       // ===== DIALOG HANDLING =====
        openDelete(row: UserPillar) {
          this.selectedRow = row;
          this.dialogState = {
            open: true,
            title: 'Delete Pillar',
            message: `Are you sure you want to delete pillar: ${row.pillarName}?`,
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
  
      this.userPillarService.deletePillar(row.id).subscribe({
        next: () => {
          this.closeDialog();
          this.loadPillars();
  
          this.dialogState = {
            open: true,
            title: 'Pillar Deleted',
            message: `Pillar deleted: ${row.pillar}`,
            type: 'info'
          };
        },
        error: () => {
          this.closeDialog();
          this.dialogState = {
            open: true,
            title: 'Error',
            message: `Failed to delete pillar: ${row.pillar}`,
            type: 'info'
          };
        }
      });
    }
  
    // ===== FORM SUBMIT =====
      onFormSubmit(data: any) {
        if (!this.formConfig) return;
    
        if (this.formConfig.mode === 'create') {
          const request: CreateUserPillarRequest = {
            name : data.name,
            description : data.description,
            pillarTemplateId: data.pillar
          };
    
          this.userPillarService.createPillar(request).subscribe({
            next: pillar => {
              this.closeOverlay();
              this.loadPillars();
    
              this.dialogState = {
                open: true,
                title: 'Pillar Created',
                message: `New pillar created: ${pillar.pillarName}`,
                type: 'info'
              };
            },
            error: err => {
              this.formErrorMessage = err?.message || 'Failed to create pillar';
            }
          });
        }
    
        if (this.formConfig.mode === 'edit' && this.selectedRow) {

          console.log(data);
          
          const request: UpdateUserPillarRequest = {
            id: data.id,
            name : data.name,
            description : data.description,
            pillarTemplateId: data.pillarName
          };
    
          this.userPillarService.updatePillar(request).subscribe({
            next: pillar => {
              this.closeOverlay();
              this.loadPillars();
    
              this.dialogState = {
                open: true,
                title: 'Pillar Updated',
                message: `Pillar updated: ${pillar.pillarName}`,
                type: 'info'
              };
            },
            error: err => {
              this.formErrorMessage = err?.message || 'Failed to update pillar';
            }
          });
        }
      }

}
