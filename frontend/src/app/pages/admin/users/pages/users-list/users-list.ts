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
import { UpdateUserRequest } from '../../model/update-user-request';
import { CreateUserRequest } from '../../model/create-user-request copy';
import { ConfirmDialog } from '../../../../../shared/components/confirm-dialog/confirm-dialog';

@Component({
  standalone: true,
  selector: 'app-users-list',
  templateUrl: './users-list.html',
  imports: [CommonModule, DataTable, Overlay, DataForm ,ConfirmDialog]
})
export class UsersListComponent implements OnInit {

  constructor(private userService: UsersService) {}

  users$!: Observable<User[]>;

  overlayOpen = false;
  overlayTitle = '';

  formConfig: FormConfig | null = null;
  selectedRow: any = null;

  closeOnBackdrop = true;

  errorMessage = ''


  ///Dialog Box Var

  dialogType = '';
  dialogMessage = '';
  dialogTitle = '';
  dialogOpen = false;

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
      label: 'Add New User'
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
        this.openDelete(event.row);
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

  openDelete(row:any){
    console.log(row);
    
    this.dialogTitle = ' Delete User';
    this.dialogMessage = ' Are You Sure To delete User : ' + row.name;
    this.dialogType = 'delete';
    this.selectedRow = row;
    this.dialogOpen = true;
  }

  onDialogConfirm(row :any){

    if(this.dialogType === 'delete'){

      this.userService.deleteUser(row.id).subscribe({
        next:  () => {
          this.closeDialog;
          this.dialogTitle = 'User Deleted';
            this.dialogMessage = 'User Deleted  : ' + row.email;
            this.dialogType = 'info';
            this.dialogOpen = true;
            this.ngOnInit();
        },
        error: (err) => {
          this.closeDialog;
          this.dialogTitle = 'Error';
            this.dialogMessage = 'Error In Deleting  : ' + row.email;
            this.dialogType = 'info';
            this.dialogOpen = true;
        }
});

      
    }

  }

  closeDialog(){
      this.dialogOpen = false;
      this.dialogTitle = '';
      this.dialogMessage = '';
      this.dialogType = '';
  }

  closeOverlay() {
    this.overlayOpen = false;
    this.formConfig = null;
    this.selectedRow = null;
    this.errorMessage = ''
  }

  onFormSubmit(data: any) {
    if (this.formConfig?.mode === 'create') {

     const userReq: CreateUserRequest = {
          name: data.name,
          email: data.email,
          role: data.userRole,
          password : data.password
        };
      this.userService.createUser(userReq).subscribe(
        {
          next : user => {
            this.closeOverlay();
            this.dialogTitle = 'User Created';
            this.dialogMessage = ' New User Creeated  : ' + user.email;
            this.dialogType = 'info';
            this.dialogOpen = true;
             this.ngOnInit();


            
          },error : err =>{
            this.errorMessage = err.message || 'failed'
          }
        }
      );
    }

    if (this.formConfig?.mode === 'edit') {
      const userReq: UpdateUserRequest = {
          name: data.name,
          role: data.role,
          email: data.email
        };

         this.userService.updateUser(userReq).subscribe(
          {
            next : user =>{

            this.closeOverlay();  

            this.dialogTitle = 'User Updated';
            this.dialogMessage = ' Updated User : ' + user.email;
            this.dialogType = 'info';
            this.dialogOpen = true;
             this.ngOnInit();

            },error : err => {
              this.errorMessage = err.message || "Failed"
            }
          }
         );
    }

  }

}

