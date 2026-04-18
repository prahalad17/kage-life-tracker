import { Routes } from '@angular/router';
import { PillarShell } from './pillar/pages/pillar-shell/pillar-shell';
import { ActivityShell } from './activity/pages/activity-shell/activity-shell';
import { PillarOverview } from './pillar/pages/pillar-overview/pillar-overview';
import { ActivityOverview } from './activity/pages/activity-overview/activity-overview';
import { PillarList } from './pillar/pages/pillar-list/pillar-list';
import { ActivityList } from './activity/pages/activity-list/activity-list';
import { DashboardShell } from './dashboard/pages/dashboard-shell/dashboard-shell';
import { DashboardOverview } from './dashboard/pages/dashboard-overview/dashboard-overview';
import { ActionEntryShell } from './action-entry/pages/action-entry-shell/action-entry-shell';
import { ActionEntryOverview } from './action-entry/pages/action-entry-overview/action-entry-overview';
import { ActionEntryList } from './action-entry/pages/action-entry-list/action-entry-list';
import { ActionPlanShell } from './action-plan/pages/action-plan-shell/action-plan-shell';
import { ActionPlanOverview } from './action-plan/pages/action-plan-overview/action-plan-overview';
import { ActionPlanList } from './action-plan/pages/action-plan-list/action-plan-list';

 const userRoutes: Routes = [

  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

  {
     path: 'dashboard', 
     component: DashboardShell,
     children: [
        { path: '', redirectTo: 'overview', pathMatch: 'full' },
        { path: 'overview', component: DashboardOverview },
        { path: 'list', component: ActionEntryList },
      ]
     
    },

  {
      path: 'action-entry',
      component: ActionEntryShell,
      children: [
        { path: '', redirectTo: 'overview', pathMatch: 'full' },
        { path: 'overview', component: ActionEntryOverview },
        { path: 'list', component: ActionEntryList },
      ]
    },

    {
      path: 'action-plan',
      component: ActionPlanShell,
      children: [
        { path: '', redirectTo: 'overview', pathMatch: 'full' },
        { path: 'overview', component: ActionPlanOverview },
        { path: 'list', component: ActionPlanList },
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