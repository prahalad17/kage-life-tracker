import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Pillar } from '../models/pillar.model';
import {map, Observable } from 'rxjs';
import { ApiResponse } from "../../../../shared/models/api/api-response.model";
import { CreatePillarRequest } from "../models/create-pillar-request";
import { UpdatePillarRequest } from "../models/update-pillar-resuest";
@Injectable({
    providedIn:'root'
})

export class PillarService{

  private BASE_URL = 'http://localhost:8080/api/v1/pillar-template';
 
  constructor(private http: HttpClient) {}

    getAll(): Observable<Pillar[]> {
    return this.http
      .get<Pillar[]>(
         `${this.BASE_URL}`
        )
      
  }

  createPillar(pillar: CreatePillarRequest): Observable<Pillar> {
    return this.http.post<Pillar>(this.BASE_URL, pillar)
      
  }

  updatePillar(pillar: UpdatePillarRequest): Observable<Pillar> {
    return this.http.put<Pillar>(
      `${this.BASE_URL}`,
      pillar
    )
  }

  deletePillar(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.BASE_URL}/${id}`)
  }

  
}