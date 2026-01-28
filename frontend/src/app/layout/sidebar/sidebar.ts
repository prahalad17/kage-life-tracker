import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ADMIN_SIDEBAR_MENU, SidebarItem, USER_SIDEBAR_MENU } from '../models/sidebar-menu.constant';
import { AuthStateService } from '../../auth/services/auth-state.service';

@Component({
  standalone: true,
  selector: 'app-sidebar',
  imports: [RouterLink, RouterLinkActive,CommonModule],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class SidebarComponent implements OnInit{

  constructor(
    private authState : AuthStateService
  ) {}

    menu: SidebarItem[] = [];

     ngOnInit(): void {
    const role = this.authState.getUserRole();

    this.menu =
      role === 'ROLE_ADMIN'
        ? ADMIN_SIDEBAR_MENU
        : USER_SIDEBAR_MENU;
  }


}
