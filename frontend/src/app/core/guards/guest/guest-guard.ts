import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../../auth/services/auth.service';
import { inject } from '@angular/core';

export const guestGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const token = authService.getToken();

  // If token exists → user is authenticated
  if (token) {
    // Redirect away from auth pages
    return router.createUrlTree(['/']);
  }

  // No token → allow access to login/register
  return true;
  
  
};
