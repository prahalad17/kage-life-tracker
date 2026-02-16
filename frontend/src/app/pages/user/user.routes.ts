import { Routes } from '@angular/router';
import { DailyLogShell } from './daily-log/pages/daily-log-shell/daily-log-shell';
import { PillarShell } from './pillar/pages/pillar-shell/pillar-shell';
import { ActivityShell } from './activity/pages/activity-shell/activity-shell';
import { DailyLogOverview } from './daily-log/pages/daily-log-overview/daily-log-overview';
import { PillarOverview } from './pillar/pages/pillar-overview/pillar-overview';
import { ActivityOverview } from './activity/pages/activity-overview/activity-overview';
import { DailyLogList } from './daily-log/pages/daily-log-list/daily-log-list';
import { PillarList } from './pillar/pages/pillar-list/pillar-list';
import { ActivityList } from './activity/pages/activity-list/activity-list';
import { DashboardShell } from './dashboard/pages/dashboard-shell/dashboard-shell';
import { DashboardOverview } from './dashboard/pages/dashboard-overview/dashboard-overview';

 const userRoutes: Routes = [

  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

  {
     path: 'dashboard', 
     component: DashboardShell,
     children: [
        { path: '', redirectTo: 'overview', pathMatch: 'full' },
        { path: 'overview', component: DashboardOverview },
        { path: 'list', component: DailyLogList },
      ]
     
    },

  {
      path: 'daily-log',
      component: DailyLogShell,
      children: [
        { path: '', redirectTo: 'overview', pathMatch: 'full' },
        { path: 'overview', component: DailyLogOverview },
        { path: 'list', component: DailyLogList },
      ]
    },
  
    {
      path: 'pillars',
      component: PillarShell,
      children: [
        { path: '', redirectTo: 'overview', pathMatch: 'full' },
        { path: 'overview', component: PillarOverview },
        { path: 'list', component: PillarList }
      ]
    },

  {
      path: 'activity',
      component: ActivityShell,
      children: [
        { path: '', redirectTo: 'overview', pathMatch: 'full' },
        { path: 'overview', component: ActivityOverview },
        { path: 'list', component: ActivityList }
      ]
    }

 
  
];

export default userRoutes;