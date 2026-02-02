import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {map, Observable } from 'rxjs';
import { ApiResponse } from "../../../../shared/models/api/api-response.model";
import { UserPillar } from "../model/user-pillar.model";
import { CreateUserPillarRequest } from "../model/create-user-pillar-request";
import { UpdateUserPillarRequest } from "../model/update-user-pillar-request";
import { Pillar } from "../../../admin/pillars/models/pillar.model";
@Injectable({
    providedIn:'root'
})

export class UserPillarService{

  private BASE_URL = 'http://localhost:8080/api/v1/user-pillars';

  private PILLAR_TEMPLATE_URL = 'http://localhost:8080/api/v1/user-pillars';

 
  constructor(private http: HttpClient) {}

    getAll(): Observable<UserPillar[]> {
    return this.http
      .get<UserPillar[]>(
         `${this.BASE_URL}`
        )
      
  }

   getAllAvailable(user : number): Observable<Pillar[]> {
    return this.http
      .get<Pillar[]>(
         `${this.BASE_URL}/${user}`
        )
      
  }



  createPillar(pillar: CreateUserPillarRequest): Observable<UserPillar> {
    return this.http.post<UserPillar>(this.BASE_URL, pillar)
  }

  updatePillar(pillar: UpdateUserPillarRequest): Observable<UserPillar> {
    return this.http.put<UserPillar>(
      `${this.BASE_URL}`,pillar)
  }

  deletePillar(id: number): Observable<void> {
    return this.http.delete<ApiResponse<void>>(
      `${this.BASE_URL}/${id}`
    ).pipe(map(() => void 0));
  }

  
}