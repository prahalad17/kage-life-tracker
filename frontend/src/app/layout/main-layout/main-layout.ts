import { Component, OnInit } from '@angular/core';
import { SidebarComponent } from '../sidebar/sidebar';
import { HeaderComponent } from '../header/header';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-main-layout',
  imports: [SidebarComponent, HeaderComponent,RouterOutlet],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.scss',
})
export class MainLayout implements OnInit {

  isSidebarCollapsed = true;


ngOnInit(): void {
  const saved = localStorage.getItem('sidebar-collapsed');
  this.isSidebarCollapsed = saved === 'true';
}

toggleSidebar(): void {
  this.isSidebarCollapsed = !this.isSidebarCollapsed;
  localStorage.setItem(
    'sidebar-collapsed',
    String(this.isSidebarCollapsed)
  );
}

}
