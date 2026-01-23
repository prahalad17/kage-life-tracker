import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApiResponse } from '../../shared/models/api/api-response.model';
import { LoginResponse } from '../models/login-response';
import { AuthUser } from '../models/auth-user';
import { map } from 'rxjs'; 
import { LoginRequest } from '../models/login-request';
import { RegisterRequest } from '../models/register-request';
import { RegisterResponse } from '../models/register-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private BASE_URL = 'http://localhost:4200/auth';

  constructor(private http: HttpClient) {}

  // ===== LOGIN =====
  login(credentials: LoginRequest): Observable<AuthUser> {
  return this.http
    .post<ApiResponse<LoginResponse>>(`${this.BASE_URL}/login`, credentials , {
      headers: new HttpHeaders
      ({
        'X-Skip-Loader': 'true',
        'X-Skip-Api-Wrapper': 'true'
        
      })
    })
    .pipe(
      map(res => {
        const { accessToken, name, userRole } = res.data;
        const user: AuthUser = {accessToken, name, userRole };
        // localStorage.setItem('token', accessToken);
        // localStorage.setItem('user', JSON.stringify(user));
        // localStorage.setItem('role', userRole);
        return user;
      })
    );
}

 // ===== REGISTER =====
  register(credentials: RegisterRequest): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(
      `${this.BASE_URL}/register`,
      credentials
    );
  }

  // ===== REFRESH =====
  refresh(): Observable<{ accessToken: string }> {
    return this.http.post<{ accessToken: string }>(
      `${this.BASE_URL}/refresh`,
      {},
      { withCredentials: true }
    );
  }

  // ===== LOGOUT =====
  logout(): Observable<void> {
    return this.http.post<void>(
      `${this.BASE_URL}/logout`,
      {},
      { withCredentials: true }
    );
  }
}
