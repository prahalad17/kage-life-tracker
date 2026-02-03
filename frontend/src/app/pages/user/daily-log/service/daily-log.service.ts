import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {map, Observable } from 'rxjs';
import { ApiResponse } from "../../../../shared/models/api/api-response.model";
import { DailyLog } from "../models/daily-log.model";
import { CreateDailyLogReq } from "../models/create-daily-log-request";
import { UpdateDailyLogReq } from "../models/update-daily-log-resuest";
@Injectable({
    providedIn:'root'
})

export class DailyLogService{

  private BASE_URL = 'http://localhost:8080/api/v1/activity/daily-log';
 
  constructor(private http: HttpClient) {}

    getAll(): Observable<DailyLog[]> {
    return this.http
      .get<DailyLog[]>(
         `${this.BASE_URL}`
        )
      
  }

  createPillar(dailyLog: CreateDailyLogReq): Observable<DailyLog> {
    return this.http.post<DailyLog>(this.BASE_URL, dailyLog)
  }

  updatePillar(dailyLog: UpdateDailyLogReq): Observable<DailyLog> {
    return this.http.put<DailyLog>(
      `${this.BASE_URL}`,
      dailyLog);
  }

  deletePillar(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.BASE_URL}/${id}`
    )
  }

  
}