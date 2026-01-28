import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';

import { UsersService } from '../../service/user.service';
import { User } from '../../model/User';

import { TableConfig } from '../../../../../shared/models/table/table-config.model';
import { FormConfig } from '../../../../../shared/models/form/form-config';

import { DataTable } from '../../../../../shared/components/data-table/data-table';
import { Overlay } from '../../../../../shared/components/overlay/overlay';
import { DataForm } from '../../../../../shared/components/data-form/data-form';
import { ConfirmDialog } from '../../../../../shared/components/confirm-dialog/confirm-dialog';

import { buildUserFormConfig } from '../../model/user-form-config';
import { CreateUserRequest } from '../../model/create-user-request';
import { UpdateUserRequest } from '../../model/update-user-request';

type DialogType = 'info' | 'delete' | '';

@Component({
  standalone: true,
  selector: 'app-users-list',
  templateUrl: './users-list.html',
  imports: [CommonModule, DataTable, Overlay, DataForm, ConfirmDialog]
})
export class UsersListComponent implements OnInit {

  constructor(private userService: UsersService) {}

  // ===== DATA =====
  users$!: Observable<User[]>;

  selectedRow: User | null = null;
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
    tableName: 'Users',
    columns: [
      { key: 'name', header: 'Name' },
      { key: 'email', header: 'Email' },
      { key: 'userRole', header: 'Role' }
    ],
    actions: [
      { type: 'view', label: 'View' },
      { type: 'edit', label: 'Edit' },
      { type: 'delete', label: 'Delete', confirm: true }
    ],
    create: {
      enabled: true,
      label: 'Add New User'
    }
  };

  // ===== LIFECYCLE =====
  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.users$ = this.userService.getUsers();
  }

   // ===== OVERLAY HANDLING =====
  openForm(
    mode: 'create' | 'view' | 'edit',
    title: string,
    row: User | null,
    closeOnBackdrop: boolean
  ) {
    this.formConfig = buildUserFormConfig(mode);
    this.selectedRow = row;

    this.overlayState = {
      open: true,
      title,
      closeOnBackdrop
    };
  }

  // ===== TABLE ACTIONS =====
  onTableAction(event: { type: string; row: User }) {
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
    this.openForm('create', 'Add User', null, false);
  }

  openView(row: User) {
    this.openForm('view', 'View User', row, true);
  }

  openEdit(row: User) {
    this.openForm('edit', 'Edit User', row, false);
  }

  closeOverlay() {
    this.overlayState.open = false;
    this.formConfig = null;
    this.selectedRow = null;
    this.formErrorMessage = '';
  }

  // ===== DIALOG HANDLING =====
  openDelete(row: User) {
    this.selectedRow = row;
    this.dialogState = {
      open: true,
      title: 'Delete User',
      message: `Are you sure you want to delete user: ${row.name}?`,
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

    this.userService.deleteUser(row.id).subscribe({
      next: () => {
        this.closeDialog();
        this.loadUsers();

        this.dialogState = {
          open: true,
          title: 'User Deleted',
          message: `User deleted: ${row.email}`,
          type: 'info'
        };
      },
      error: () => {
        this.closeDialog();
        this.dialogState = {
          open: true,
          title: 'Error',
          message: `Failed to delete user: ${row.email}`,
          type: 'info'
        };
      }
    });
  }

  // ===== FORM SUBMIT =====
  onFormSubmit(data: any) {
    if (!this.formConfig) return;

    if (this.formConfig.mode === 'create') {
      const request: CreateUserRequest = {
        name: data.name,
        email: data.email,
        role: data.userRole,
        password: data.password
      };

      this.userService.createUser(request).subscribe({
        next: user => {
          this.closeOverlay();
          this.loadUsers();

          this.dialogState = {
            open: true,
            title: 'User Created',
            message: `New user created: ${user.email}`,
            type: 'info'
          };
        },
        error: err => {
          this.formErrorMessage = err?.message || 'Failed to create user';
        }
      });
    }

    if (this.formConfig.mode === 'edit' && this.selectedRow) {
      const request: UpdateUserRequest = {
        name: data.name,
        email: data.email,
        role: data.role
      };

      this.userService.updateUser(request).subscribe({
        next: user => {
          this.closeOverlay();
          this.loadUsers();

          this.dialogState = {
            open: true,
            title: 'User Updated',
            message: `User updated: ${user.email}`,
            type: 'info'
          };
        },
        error: err => {
          this.formErrorMessage = err?.message || 'Failed to update user';
        }
      });
    }
  }
}
