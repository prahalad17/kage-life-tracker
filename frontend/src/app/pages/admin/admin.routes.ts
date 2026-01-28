import { Routes } from '@angular/router';
import { Dashboard } from './dashboard/dashboard';


import { UsersLayoutComponent } from './users/layout/users-layout/users-layout';
import { UsersOverviewComponent } from './users/pages/users-overview/users-overview';
import { PillarsShellComponent } from './pillars/pages/pillars-shell/pillars-shell';
import { PillarsListComponent } from './pillars/pages/pillars-list/pillars-list';
import { UsersListComponent } from './users/pages/users-list/users-list';
import { ActivityShellComponent } from './activity/pages/activity-shell/activity-shell';
import { ActivityListComponent } from './activity/pages/activity-list/activity-list';
import { UsersShell } from './users/pages/users-shell/users-shell';



 const adminRoutes: Routes = [

  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

  { path: 'dashboard', component: Dashboard },

  {
    path: 'users',
    component: UsersShell,
    children: [
      { path: '', redirectTo: 'overview', pathMatch: 'full' },
      { path: 'overview', component: UsersOverviewComponent },
      { path: 'list', component: UsersListComponent },
    ]
  },

  {
    path: 'pillars',
    component: PillarsShellComponent,
    children: [
      { path: '', redirectTo: 'list', pathMatch: 'full' },
      { path: 'list', component: PillarsListComponent }
    ]
  },

  {
    path: 'activity',
    component: ActivityShellComponent,
    children: [
      { path: '', redirectTo: 'list', pathMatch: 'full' },
      { path: 'list', component: ActivityListComponent }
    ]
  }
];

export default adminRoutes;  
