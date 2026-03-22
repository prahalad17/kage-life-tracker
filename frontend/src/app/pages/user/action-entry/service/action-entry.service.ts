import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {map, Observable } from 'rxjs';
import { ApiResponse } from "../../../../shared/models/api/api-response.model";
import { PageResponse } from "../../../../shared/models/api/page-response.model";
import { ActionEntry } from "../models/action-entry.model";
import { SearchRequestDto } from "../../../../shared/models/api/search-request.model";
import { CreateActionEntryReq } from "../models/create-action-entry-request";
import { UpdateActionEntryReq } from "../models/update-action-entry-resuest";
@Injectable({
    providedIn:'root'
})

export class ActionEntryService{

  private BASE_URL = 'http://localhost:8080/api/v1/activity/daily-log';
 
  constructor(private http: HttpClient) {}

    getAll(): Observable<ActionEntry[]> {
    return this.http
      .get<ActionEntry[]>(
         `${this.BASE_URL}`
        )
      
  }

  search(request: SearchRequestDto): Observable<PageResponse<ActionEntry>> {
  return this.http.post<PageResponse<ActionEntry>>(
    `${this.BASE_URL}/search`,
    request
  );
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

  
}