import { Injectable } from '@angular/core';
import { AuthUser } from '../models/auth-user';

@Injectable({
  providedIn: 'root'
})
export class AuthStateService {

  private accessToken: string | null = null;
   private user: AuthUser | null = null;

 // ===== ACCESS TOKEN =====
  setAccessToken(token: string): void {
    this.accessToken = token;
  }

  getAccessToken(): string | null {
    return this.accessToken;
  }

  // ===== USER STATE =====
  setUser(user: AuthUser): void {
    this.user = user;
  }

  getUser(): AuthUser | null {
    return this.user;
  }

  getUserRole(): string | null {
    return this.user?.userRole ?? null;
  }

  getUserName(): string | null {
    return this.user?.name ?? null;
  }

  // ===== SESSION =====
  isLoggedIn(): boolean {
    return !!this.accessToken;
  }

  clear(): void {
    this.accessToken = null;
    this.user = null;
  }
}
