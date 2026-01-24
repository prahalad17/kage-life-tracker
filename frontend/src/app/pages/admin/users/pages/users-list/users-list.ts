import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { TableConfig } from '../../../../../shared/models/table/table-config.model';
import { DataTable } from '../../../../../shared/components/data-table/data-table';
import { UsersService } from '../../service/user.service';
import { User } from '../../model/User';
import { FormConfig } from '../../../../../shared/models/form/form-config';
import { Overlay } from '../../../../../shared/components/overlay/overlay';
import { DataForm } from '../../../../../shared/components/data-form/data-form';

@Component({
  standalone: true,
  selector: 'app-users-list',
  templateUrl: './users-list.html',
  imports: [CommonModule, FormsModule , DataTable, Overlay, DataForm]
})
export class UsersListComponent implements OnInit{

   constructor(private userService: UsersService) {}

    users$!: Observable<User[]>;

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

  // overlay state
overlayOpen = false;
overlayTitle = '';

  // form state
  formConfig!: FormConfig;
  selectedRow: any = null;

  

   ngOnInit(): void {
     this.users$  = this.userService.getUsers()
  }

  onTableAction(event: { type: string; row: any }): void {
    switch (event.type) {
      case 'view':
        console.log('View user', event.row);
         this.openView(event.row);
        break;

      case 'edit':
        console.log('Edit user', event.row);
        // this.openEdit(event.row);
        break;

      case 'delete':
        console.log('Delete user', event.row);
         //this.deleteUser(event.row);
        break;
    }
  }

  openCreate() {
  this.overlayTitle = 'Add User';
  this.formConfig = this.CREATE_USER_FORM_CONFIG;
  this.selectedRow = null;
  this.overlayOpen = true;
}

openView(row: any) {
  this.overlayTitle = 'View User';
  // this.formConfig = this.buildFormConfig('view');
  this.selectedRow = row;
  this.overlayOpen = true;
}

closeOverlay() {
  this.overlayOpen = false;
  this.selectedRow = null;
}

onFormSubmit(data: any) {
  if (this.formConfig.mode === 'create') {
    // this.createUser(data);
  }

  if (this.formConfig.mode === 'edit') {
    // this.updateUser(data);
  }

  this.closeOverlay();
}

  onCreateUser(): void {
     this.overlayTitle = 'Add User';
  this.formConfig = this.CREATE_USER_FORM_CONFIG;
  this.selectedRow = null;
  this.overlayOpen = true;
    console.log('Create new user');
  }

   CREATE_USER_FORM_CONFIG: FormConfig = {
  title: 'Create User',
  mode: 'create',
  readOnly: false,

  layout: 'grid',
  gridColumns: 2,

  fields: [
    {
      name: 'name',
      label: 'Full Name',
      type: 'text',
      placeholder: 'Enter full name',
      required: true,
      minLength: 3
    },
    {
      name: 'email',
      label: 'Email Address',
      type: 'email',
      placeholder: 'Enter email',
      required: true
    },
    {
      name: 'password',
      label: 'Password',
      type: 'password',
      placeholder: 'Enter password',
      required: true,
      minLength: 6
    },
    {
      name: 'userRole',
      label: 'User Role',
      type: 'select',
      required: true,
      options: [
        { label: 'Admin', value: 'ADMIN' },
        { label: 'User', value: 'USER' }
      ]
    },
    {
      name: 'active',
      label: 'Active',
      type: 'checkbox',
      defaultValue: true
    }
  ],

  actions: [
    {
      label: 'Cancel',
      type: 'button',
      action: 'cancel'
    },
    {
      label: 'Create User',
      type: 'submit',
      color: 'primary'
    }
  ]
};

}
