import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {map, Observable } from 'rxjs';
import { ApiResponse } from "../../../../shared/models/api/api-response.model";
import { UserPillar } from "../model/user-pillar.model";
import { CreateUserPillarRequest } from "../model/create-user-pillar-request";
import { UpdateUserPillarRequest } from "../model/update-user-pillar-request";
@Injectable({
    providedIn:'root'
})

export class UserPillarService{

  private BASE_URL = 'http://localhost:8080/api/v1/master-pillars';
 
  constructor(private http: HttpClient) {}

    getAll(): Observable<UserPillar[]> {
    return this.http
      .get<UserPillar[]>(
         `${this.BASE_URL}`
        )
      
  }

  createPillar(pillar: CreateUserPillarRequest): Observable<UserPillar> {
    return this.http.post<ApiResponse<UserPillar>>(this.BASE_URL, pillar)
      .pipe(map(res => res.data));
  }

  updatePillar(pillar: UpdateUserPillarRequest): Observable<UserPillar> {
    return this.http.put<ApiResponse<UserPillar>>(
      `${this.BASE_URL}/${pillar.id}`,
      pillar
    ).pipe(map(res => res.data));
  }

  deletePillar(id: number): Observable<void> {
    return this.http.delete<ApiResponse<void>>(
      `${this.BASE_URL}/${id}`
    ).pipe(map(() => void 0));
  }

  
}