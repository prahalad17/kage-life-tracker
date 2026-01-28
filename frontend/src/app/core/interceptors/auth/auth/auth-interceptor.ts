import {
  HttpInterceptorFn,
  HttpRequest,
  HttpHandlerFn,
  HttpEvent
} from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthStateService } from '../../../../auth/services/auth-state.service';
import { Observable } from 'rxjs';

export const authInterceptor: HttpInterceptorFn =
  (req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> => {

    const authState = inject(AuthStateService);
    const accessToken = authState.getAccessToken();

    // Skip auth endpoints
    if (!accessToken || req.url.includes('/api/auth')) {
      return next(req);
    }

    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${accessToken}`
      }
    });

    return next(authReq);
  };