import { Routes } from '@angular/router';
import { Dashboard } from './dashboard/dashboard';

 const userRoutes: Routes = [

  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: Dashboard }

  
];

export default userRoutes;  
