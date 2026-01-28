import { Routes } from '@angular/router';
import { Dashboard } from './dashboard/dashboard';


import { UsersOverviewComponent } from './users/pages/users-overview/users-overview';
import { PillarsShellComponent } from './pillars/pages/pillars-shell/pillars-shell';
import { PillarsListComponent } from './pillars/pages/pillars-list/pillars-list';
import { UsersListComponent } from './users/pages/users-list/users-list';
import { ActivityShellComponent } from './activity/pages/activity-shell/activity-shell';
import { ActivityListComponent } from './activity/pages/activity-list/activity-list';
import { UsersShell } from './users/pages/users-shell/users-shell';
import { PillarsOverview } from './pillars/pages/pillars-overview/pillars-overview';
import { ActivityOverview } from './activity/pages/activity-overview/activity-overview';



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
      { path: '', redirectTo: 'overview', pathMatch: 'full' },
      { path: 'overview', component: PillarsOverview },
      { path: 'list', component: PillarsListComponent }
    ]
  },

  {
    path: 'activity',
    component: ActivityShellComponent,
    children: [
      { path: '', redirectTo: 'overview', pathMatch: 'full' },
      { path: 'overview', component: ActivityOverview },
      { path: 'list', component: ActivityListComponent }
    ]
  }
];

export default adminRoutes;  
