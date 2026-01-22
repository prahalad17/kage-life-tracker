import { Injectable } from "@angular/core";

@Injectable({ providedIn: 'root' })
export class UserContextService {

  getRole(): 'ADMIN' | 'USER' {
    // for now: from token / localStorage
    return localStorage.getItem('role') as 'ADMIN' | 'USER';
  }
}
