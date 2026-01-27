import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { TableConfig } from '../../../../../shared/models/table/table-config.model';
import { DataTable } from '../../../../../shared/components/data-table/data-table';
import { UsersService } from '../../service/user.service';
import { User } from '../../model/User';
import { FormConfig } from '../../../../../shared/models/form/form-config';
import { Overlay } from '../../../../../shared/components/overlay/overlay';
import { DataForm } from '../../../../../shared/components/data-form/data-form';
import { buildUserFormConfig } from '../../model/user-form-config';

@Component({
  standalone: true,
  selector: 'app-users-list',
  templateUrl: './users-list.html',
  imports: [CommonModule, DataTable, Overlay, DataForm]
})
export class UsersListComponent implements OnInit {

  constructor(private userService: UsersService) {}

  users$!: Observable<User[]>;

  overlayOpen = false;
  overlayTitle = '';

  formConfig: FormConfig | null = null;
  selectedRow: any = null;

  closeOnBackdrop = true;

  tableConfig: TableConfig = {
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
      label: 'Add User'
    }
  };

  ngOnInit(): void {
    this.users$ = this.userService.getUsers();
  }

  onTableAction(event: { type: string; row: any }) {
    switch (event.type) {
      case 'view':
        this.openView(event.row);
        break;

      case 'edit':
        this.openEdit(event.row);
        break;

      case 'delete':
        // this.deleteUser(event.row);
        break;
    }
  }

  openCreate() {
    this.overlayTitle = 'Add User';
    this.formConfig = buildUserFormConfig('create');
    this.selectedRow = null;
    this.closeOnBackdrop = false;
    this.overlayOpen = true;
  }

  openView(row: any) {
    this.overlayTitle = 'View User';
    this.formConfig = buildUserFormConfig('view');
    this.selectedRow = row;
     this.closeOnBackdrop = true;
    this.overlayOpen = true;
  }

  openEdit(row: any) {
    this.overlayTitle = 'Edit User';
    this.formConfig = buildUserFormConfig('edit');
    this.selectedRow = row;
     this.closeOnBackdrop = false;
    this.overlayOpen = true;
  }

  closeOverlay() {
    this.overlayOpen = false;
    this.formConfig = null;
    this.selectedRow = null;
  }

  onFormSubmit(data: any) {
    if (this.formConfig?.mode === 'create') {
      // this.userService.createUser(data);
    }

    if (this.formConfig?.mode === 'edit') {
      // this.userService.updateUser(data);
    }

    this.closeOverlay();
  }
}

