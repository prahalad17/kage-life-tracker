import {
  HttpInterceptorFn,
  HttpRequest,
  HttpHandlerFn,
  HttpEvent,
  HttpErrorResponse
} from '@angular/common/http';
import { inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  Observable,
  BehaviorSubject,
  throwError
} from 'rxjs';
import {
  catchError,
  filter,
  switchMap,
  take
} from 'rxjs/operators';
import { AuthStateService } from '../../../../auth/services/auth-state.service';

let refreshing = false;
const refreshSubject = new BehaviorSubject<string | null>(null);

export const authRefreshInterceptor: HttpInterceptorFn =
  (req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> => {

    const authState = inject(AuthStateService);
    const http = inject(HttpClient);

    return next(req).pipe(
      catchError((error: HttpErrorResponse): Observable<HttpEvent<any>> => {

        if (
          error.status === 401 &&
          !req.url.includes('/auth')&&
          !req.url.includes('/auth/refresh') 
        ) {
          return handle401(req, next, authState, http);
        }

        return throwError(() => error);
      })
    );
  };

function handle401(
  req: HttpRequest<any>,
  next: HttpHandlerFn,
  authState: AuthStateService,
  http: HttpClient
): Observable<HttpEvent<any>> {

  if (!refreshing) {
    refreshing = true;
    refreshSubject.next(null);

    return http.post<{ accessToken: string }>(
      '/auth/refresh',
      {},
      { withCredentials: true }
    ).pipe(
      switchMap(res => {
        refreshing = false;

        authState.setAccessToken(res.accessToken);
        refreshSubject.next(res.accessToken);

        const authReq = req.clone({
          setHeaders: {
            Authorization: `Bearer ${res.accessToken}`
          }
        });

        return next(authReq);
      }),
      catchError(err => {
        refreshing = false;
        authState.clear(); // session invalid → force login
        return throwError(() => err);
      })
    );
  }

  // Wait for refresh to complete
  return refreshSubject.pipe(
    filter(token => token !== null),
    take(1),
    switchMap(token => {
      const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token!}`
        }
      });
      return next(authReq);
    })
  );
}
