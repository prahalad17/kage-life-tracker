import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { AuthStateService } from './auth-state.service';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthInitService {

  constructor(
    private authService: AuthService,
    private authState: AuthStateService
  ) {}

 initialize() {
    return this.authService.refresh().pipe(
      tap(res => {
        this.authState.setAccessToken(res.accessToken);
        this.authState.setUser(res);
      }),
      catchError(() => {
        this.authState.clear();
        return of(null);
      })
    );
  }
}
