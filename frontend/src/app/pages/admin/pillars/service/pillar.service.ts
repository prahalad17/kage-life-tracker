import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { Pillar } from '../models/pillar.model';
import {map, Observable } from 'rxjs';
import { ApiResponse } from "../../../../shared/models/api/api-response.model";
@Injectable({
    providedIn:'root'
})

export class PillarService{

  private readonly baseUrl = 'http://localhost:8080/api/v1/master-pillars';

  constructor(private http: HttpClient) {}

    getAll(): Observable<Pillar[]> {
    return this.http
      .get<ApiResponse<Pillar[]>>(this.baseUrl)
      .pipe(
        map(res => {
        // console.log('RAW API RESPONSE:', res);
        // console.log('TYPE OF data:', typeof res.data);
        // console.log('IS ARRAY:', Array.isArray(res.data));
        return res.data ?? [];
      })
      );
  }

  create(pillar: Pillar): Observable<Pillar> {
    return this.http.post<ApiResponse<Pillar>>(this.baseUrl, pillar)
      .pipe(map(res => res.data));
  }

  update(pillar: Pillar): Observable<Pillar> {
    return this.http.put<ApiResponse<Pillar>>(
      `${this.baseUrl}/${pillar.id}`,
      pillar
    ).pipe(map(res => res.data));
  }

  delete(id: number): Observable<void> {
    return this.http.delete<ApiResponse<void>>(
      `${this.baseUrl}/${id}`
    ).pipe(map(() => void 0));
  }

  
}