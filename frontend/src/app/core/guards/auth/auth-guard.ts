import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthStateService } from '../../../auth/services/auth-state.service';

export const authGuard: CanActivateFn = (route, state) => {

  const authStateService = inject(AuthStateService);
  const router = inject(Router);

  const token = authStateService.getAccessToken();

  

  if (token) {
    // console.log(token +'token')
    return true;
  }

   return router.createUrlTree(['/auth/login']);
};
