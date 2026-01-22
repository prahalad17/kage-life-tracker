import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { User } from '../model/User';
import { ApiResponse } from '../../../../shared/models/api/api-response.model';
@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private apiUrl = 'http://localhost:8080/api/users/getAll';

  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
  return this.http.get<User[]>(this.apiUrl);
}
}

