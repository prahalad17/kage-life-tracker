import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { TableConfig } from '../../../../../shared/models/table/table-config.model';
import { DataTable } from '../../../../../shared/components/data-table/data-table';
import { UsersService } from '../../service/user.service';
import { User } from '../../model/User';

@Component({
  standalone: true,
  selector: 'app-users-list',
  templateUrl: './users-list.html',
  imports: [CommonModule, FormsModule , DataTable]
})
export class UsersListComponent implements OnInit{

   constructor(private userService: UsersService) {}

    users$!: Observable<User[]>;

    tableConfig: TableConfig = {
    columns: [
      { key: 'name', header: 'Name' },
      { key: 'email', header: 'Email' },
      { key: 'role', header: 'Role' }
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
     this.users$  = this.userService.getUsers()
  }

  onTableAction(event: { type: string; row: any }): void {
    switch (event.type) {
      case 's':
        console.log('View user', event.row);
        break;

      case 'edit':
        console.log('Edit user', event.row);
        break;

      case 'delete':
        console.log('Delete user', event.row);
        break;
    }
  }

  onCreateUser(): void {
    console.log('Create new user');
  }

}
