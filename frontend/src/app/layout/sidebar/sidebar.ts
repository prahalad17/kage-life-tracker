import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../auth/services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ADMIN_SIDEBAR_MENU, SidebarItem, USER_SIDEBAR_MENU } from '../../shared/components/sidebar-menu.constant';
import { UserContextService } from '../../core/services/user-context.service';

@Component({
  standalone: true,
  selector: 'app-sidebar',
  imports: [RouterLink, RouterLinkActive,CommonModule],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class SidebarComponent implements OnInit{

  constructor(
    private userContext : UserContextService
  ) {}

    menu: SidebarItem[] = [];

     ngOnInit(): void {
    const role = this.userContext.getRole();

    console.log(role)
    console.log(typeof(role))
    
    
    this.menu =
      role == 'ADMIN'
        ? ADMIN_SIDEBAR_MENU
        : USER_SIDEBAR_MENU;
        console.log(this.menu)
  }


}
