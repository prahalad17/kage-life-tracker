import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { NavigationEnd, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ADMIN_SIDEBAR_MENU, SidebarItem, USER_SIDEBAR_MENU } from '../models/sidebar-menu.constant';
import { AuthStateService } from '../../auth/services/auth-state.service';
import { filter, Subscription } from 'rxjs';

@Component({
  standalone: true,
  selector: 'app-sidebar',
  imports: [RouterModule,CommonModule],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.scss',
})
export class SidebarComponent implements OnInit, OnDestroy{
@Input() collapsed!: boolean;
  @Output() toggleSidebar = new EventEmitter<void>();

  menu: SidebarItem[] = [];

  expandedItem: string | null = null;

  private routerSub!: Subscription;

  constructor(
    private authState: AuthStateService,
    private router: Router
  ) {}

  /* ====================================
     INIT
  ==================================== */

  ngOnInit(): void {
    this.loadMenuByRole();
    this.autoExpandOnRoute();
    this.listenToRouteChanges();
  }

  ngOnDestroy(): void {
    this.routerSub?.unsubscribe();
  }

  /* ====================================
     ROLE BASED MENU
  ==================================== */

  private loadMenuByRole(): void {
    const role = this.authState.getUserRole();

    this.menu =
      role === 'ROLE_ADMIN'
        ? ADMIN_SIDEBAR_MENU
        : USER_SIDEBAR_MENU;
  }

  /* ====================================
     ROUTE LISTENER
  ==================================== */

  private listenToRouteChanges(): void {
    this.routerSub = this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        this.autoExpandOnRoute();
      });
  }

  /* ====================================
     AUTO EXPAND SECTION
  ==================================== */

  private autoExpandOnRoute(): void {
    const currentUrl = this.router.url;
    this.expandedItem = null;

    for (const item of this.menu) {
      if (item.children?.length) {
        const match = item.children.some(child =>
          currentUrl.startsWith(child.route || '')
        );

        if (match) {
          this.expandedItem = item.label;
          break;
        }
      }
    }
  }

  /* ====================================
     ACTIVE ROUTE CHECK
  ==================================== */

  isActive(route?: string): boolean {
  if (!route) return false;

  return this.router.url === route;
}

isParentActive(item: SidebarItem): boolean {
  if (!item.children) return false;

  return item.children.some(child =>
    this.router.url.startsWith(child.route || '')
  );
}

}
