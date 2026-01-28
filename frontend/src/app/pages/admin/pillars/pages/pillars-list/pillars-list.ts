import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Pillar } from '../../models/pillar.model';
import { PillarService } from '../../service/pillar.service';
import { Observable } from 'rxjs';
import { DataTable } from '../../../../../shared/components/data-table/data-table';
import { TableConfig } from '../../../../../shared/models/table/table-config.model';
import { FormConfig } from '../../../../../shared/models/form/form-config';
import { buildPillarFormConfig } from '../../models/pillar-form-config';
import { CreatePillarRequest } from '../../models/create-pillar-request';
import { UpdatePillarRequest } from '../../models/update-pillar-resuest';
import { Overlay } from '../../../../../shared/components/overlay/overlay';
import { DataForm } from '../../../../../shared/components/data-form/data-form';
import { ConfirmDialog } from '../../../../../shared/components/confirm-dialog/confirm-dialog';

type DialogType = 'info' | 'delete' | '';

@Component({
  standalone: true,
  selector: 'app-pillars-list',
  imports: [
    CommonModule,
    DataTable,
    Overlay,
    DataForm,
    ConfirmDialog
  ],
  templateUrl: './pillars-list.html',
  styleUrl: './pillars-list.css',
})
export class PillarsListComponent implements OnInit {

  constructor(private pillarService: PillarService) {}

  // ===== DATA =====
  pillars$!: Observable<Pillar[]>;

  selectedRow: Pillar | null = null;
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
      tableName: 'Pillars',
      columns: [
        { key: 'name', header: 'Pillar' },
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
  }

   loadPillars() {
    this.pillars$ = this.pillarService.getAll();
  }

  
     // ===== OVERLAY HANDLING =====
    openForm(
      mode: 'create' | 'view' | 'edit',
      title: string,
      row: Pillar | null,
      closeOnBackdrop: boolean
    ) {
      this.formConfig = buildPillarFormConfig(mode);
      this.selectedRow = row;
  
      this.overlayState = {
        open: true,
        title,
        closeOnBackdrop
      };
    }
  
  

  // ===== TABLE ACTIONS =====
    onTableAction(event: { type: string; row: Pillar }) {
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
    
      openView(row: Pillar) {
        this.openForm('view', 'View Pillar', row, true);
      }
    
      openEdit(row: Pillar) {
        this.openForm('edit', 'Edit Pillar', row, false);
      }

      closeOverlay() {
      this.overlayState.open = false;
      this.formConfig = null;
      this.selectedRow = null;
      this.formErrorMessage = '';
    }

     // ===== DIALOG HANDLING =====
      openDelete(row: Pillar) {
        this.selectedRow = row;
        this.dialogState = {
          open: true,
          title: 'Delete Pillar',
          message: `Are you sure you want to delete pillar: ${row.name}?`,
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

    this.pillarService.deletePillar(row.id).subscribe({
      next: () => {
        this.closeDialog();
        this.loadPillars();

        this.dialogState = {
          open: true,
          title: 'Pillar Deleted',
          message: `Pillar deleted: ${row.email}`,
          type: 'info'
        };
      },
      error: () => {
        this.closeDialog();
        this.dialogState = {
          open: true,
          title: 'Error',
          message: `Failed to delete pillar: ${row.email}`,
          type: 'info'
        };
      }
    });
  }

  // ===== FORM SUBMIT =====
    onFormSubmit(data: any) {
      if (!this.formConfig) return;
  
      if (this.formConfig.mode === 'create') {
        const request: CreatePillarRequest = {
          name: data.name,
          description : data.description
        };
  
        this.pillarService.createPillar(request).subscribe({
          next: pillar => {
            this.closeOverlay();
            this.loadPillars();
  
            this.dialogState = {
              open: true,
              title: 'Pillar Created',
              message: `New pillar created: ${pillar.name}`,
              type: 'info'
            };
          },
          error: err => {
            this.formErrorMessage = err?.message || 'Failed to create pillar';
          }
        });
      }
  
      if (this.formConfig.mode === 'edit' && this.selectedRow) {
        const request: UpdatePillarRequest = {
          name: data.name,
          id: data.id,
          description : data.description
        };
  
        this.pillarService.updatePillar(request).subscribe({
          next: pillar => {
            this.closeOverlay();
            this.loadPillars();
  
            this.dialogState = {
              open: true,
              title: 'Pillar Updated',
              message: `Pillar updated: ${pillar.name}`,
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
