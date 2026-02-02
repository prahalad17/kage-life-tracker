import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { User } from '../model/User';
import { ApiResponse } from '../../../../shared/models/api/api-response.model';
import { UserResponse } from '../model/user-response';
import { UpdateUserRequest } from '../model/update-user-request';
import { CreateUserRequest } from '../model/create-user-request';
@Injectable({
  providedIn: 'root'
})
export class UsersService {
  

  private BASE_URL = 'http://localhost:4200/api/users';


  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(
      `${this.BASE_URL}/getAll`
    );
  }

createUser(userDetails : CreateUserRequest) : Observable<UserResponse> {
  return this.http.post<UserResponse>(

    `${this.BASE_URL}`, userDetails
  );
}

updateUser(userDetails : UpdateUserRequest) : Observable<UserResponse>
  {
    return this.http.patch<UserResponse>(

      `${this.BASE_URL}`, userDetails
    );

  }

 deleteUser(userId: string): Observable<void> {
  return this.http.delete<void>(
    `${this.BASE_URL}/${userId}`
  );
}
}