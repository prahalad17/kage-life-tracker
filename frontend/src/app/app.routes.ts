import { Routes } from '@angular/router';
import { LoginComponent } from './auth/pages/login/login';
import { authGuard } from './core/guards/auth/auth-guard';
import { PublicLayout } from './core/layouts/public-layout/public-layout';
import { Landing } from './core/pages/landing/landing';

import { Register } from './auth/pages/register/register';
import { CheckEmail } from './auth/pages/check-email/check-email';
import { MainLayout } from './layout/main-layout/main-layout';
import { roleGuard } from './core/guards/role/role-guard';
import { guestGuard } from './core/guards/guest/guest-guard';
import { AuthShell } from './auth/pages/auth-shell/auth-shell';
export const routes: Routes = [


 
  /* ----------------------------
   * 🔓 Public area
   * ---------------------------- */
  { path: '', 
    component: PublicLayout,
    canActivate: [guestGuard],
    children: [
      {
        path: '',
        component: Landing
      },
      {
        path: 'auth',
        component: AuthShell,
        children: [
          { path: 'login', component: LoginComponent },
          { path: 'register', component: Register },
          { path: 'check-email', component: CheckEmail }
        ]
      }
    ]
   },


   /* ----------------------------
   * 🔒 Protected area
   * ---------------------------- */

  {
    path:'',
    canActivate: [authGuard],
    component: MainLayout,
    children:[

       /* ----------------------------
       *  Admin routes (ADMIN only)
       * ---------------------------- */
      {
        path: 'admin',
        canActivate: [roleGuard],
        data: { roles: ['ROLE_ADMIN'] },
        loadChildren: () =>
          import('./pages/admin/admin.routes')
          .then(m => m.default)
      },

      /* ----------------------------
       *  User routes (USER only)
       * ---------------------------- */
      {
        path: '',
        canActivate: [roleGuard],
        data: { roles: ['ROLE_USER'] },
        loadChildren: () =>
          import('./pages/user/user.routes')
          .then(m => m.default)
      }
    ]
  },


  // Default redirect
  //{ path: '', redirectTo: 'login', pathMatch: 'full' }
];