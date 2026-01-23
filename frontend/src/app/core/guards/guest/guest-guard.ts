import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../../auth/services/auth.service';
import { inject } from '@angular/core';
import { AuthStateService } from '../../../auth/services/auth-state.service';

export const guestGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

    const authStateService = inject(AuthStateService);

 const token = authStateService.getAccessToken();
  // If token exists → user is authenticated
  if (token) {
    // Redirect away from auth pages
    return router.createUrlTree(['/']);
  }

  // No token → allow access to login/register
  return true;
  
  
};
