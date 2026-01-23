import { CanActivateFn, ActivatedRouteSnapshot, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../../../auth/services/auth.service';
import { AuthStateService } from '../../../auth/services/auth-state.service';

export const roleGuard: CanActivateFn = (route, state) => {
  
  const router = inject(Router);
  const authService = inject(AuthService);

    const authStateService = inject(AuthStateService);


  // 1️⃣ Get required roles from route metadata
  const allowedRoles = route.data['roles'] as string[] | undefined;

  // 2️⃣ Get current user role
  const userRole = authStateService.getUserRole();

  // 3️⃣ If route does not specify roles → allow
  if (!allowedRoles || allowedRoles.length === 0) {
    return true;
  }

  // 4️⃣ If user role matches allowed roles → allow
  if (userRole && allowedRoles.includes(userRole)) {
    return true;
  }

  // 5️⃣ Otherwise → block and redirect
  return router.createUrlTree(['/unauthorized']);
};
