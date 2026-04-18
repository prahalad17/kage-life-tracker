import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {map, Observable } from 'rxjs';
import { ApiResponse } from "../../../../shared/models/api/api-response.model";
import { ActivityDailyLogSchedulerRequest } from "../models/activityScheduler";
import { ActionEntry } from "../../action-entry/models/action-entry.model";
import { CreateActionEntryReq } from "../../action-entry/models/create-action-entry-request";
import { UpdateActionEntryReq } from "../../action-entry/models/update-action-entry-resuest";
@Injectable({
    providedIn:'root'
})

export class DashboardService{

  private BASE_URL = 'http://localhost:8080/api/v1/dashboard';
 
  constructor(private http: HttpClient) {}

  

    getToDo(): Observable<ActionEntry[]> {
    return this.http
      .get<ActionEntry[]>(
         `${this.BASE_URL}/toDo`
        )
      
  }

   getDone(): Observable<ActionEntry[]> {
    return this.http
      .get<ActionEntry[]>(
         `${this.BASE_URL}/completed`
        )
      
  }

  createLog(dailyLog: CreateActionEntryReq): Observable<ActionEntry> {
    return this.http.post<ActionEntry>(this.BASE_URL, dailyLog)
  }

  updateLog(dailyLog: UpdateActionEntryReq): Observable<ActionEntry> {
    return this.http.put<ActionEntry>(
      `${this.BASE_URL}`,
      dailyLog);
  }

  deleteLog(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.BASE_URL}/${id}`
    )
  }

 schedule(date: string): Observable<any> {

  const body: ActivityDailyLogSchedulerRequest = {
    date: date
  };
    return this.http.post<any>(
      `/api/v1/scheduler/generate`,body
    )
  }

  
}