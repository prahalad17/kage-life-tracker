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

  private BASE_URL = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) {}

  // ===== LOGIN =====
  login(credentials: LoginRequest): Observable<AuthUser> {
  return this.http
    .post<LoginResponse>(`${this.BASE_URL}/login`, credentials , {
      headers: new HttpHeaders
      ({
        'X-Skip-Loader': 'true'
      })
    })
    .pipe(
      map(res => {
        const { token, name, userRole } = res;
        const user: AuthUser = { name, userRole };
        localStorage.setItem('token', token);
        localStorage.setItem('user', JSON.stringify(user));
        localStorage.setItem('role', userRole);

        return user;
      })
    );
}

register(credentials:RegisterRequest): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(`${this.BASE_URL}/register`, credentials);
  }

 

  getToken(): string | null {
    return localStorage.getItem('token');
  }

   getRole(): string | null {
    return localStorage.getItem('role');
  }

 

  getUserName(): string | null {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user).name : null;
  }

  // ===== LOGOUT =====
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }
}
