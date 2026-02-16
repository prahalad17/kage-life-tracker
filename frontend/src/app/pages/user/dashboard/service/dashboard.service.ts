import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {map, Observable } from 'rxjs';
import { ApiResponse } from "../../../../shared/models/api/api-response.model";
import { DailyLog } from "../../daily-log/models/daily-log.model";
import { UpdateDailyLogReq } from "../../daily-log/models/update-daily-log-resuest";
import { CreateDailyLogReq } from "../../daily-log/models/create-daily-log-request";
@Injectable({
    providedIn:'root'
})

export class DashboardService{

  private BASE_URL = 'http://localhost:8080/api/v1/dashboard';
 
  constructor(private http: HttpClient) {}

  

    getToDo(): Observable<DailyLog[]> {
    return this.http
      .get<DailyLog[]>(
         `${this.BASE_URL}/toDo`
        )
      
  }

   getDone(): Observable<DailyLog[]> {
    return this.http
      .get<DailyLog[]>(
         `${this.BASE_URL}/completed`
        )
      
  }

  createLog(dailyLog: CreateDailyLogReq): Observable<DailyLog> {
    return this.http.post<DailyLog>(this.BASE_URL, dailyLog)
  }

  updateLog(dailyLog: UpdateDailyLogReq): Observable<DailyLog> {
    return this.http.put<DailyLog>(
      `${this.BASE_URL}`,
      dailyLog);
  }

  deleteLog(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.BASE_URL}/${id}`
    )
  }

  
}