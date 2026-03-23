import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {map, Observable } from 'rxjs';
import { ApiResponse } from "../../../../shared/models/api/api-response.model";
import { PageResponse } from "../../../../shared/models/api/page-response.model";
import { SearchRequestDto } from "../../../../shared/models/api/search-request.model";
import { ActionPlan } from "../models/action-plan.model";
import { CreateActionPlanReq } from "../models/create-action-plan-request";
import { UpdateActionPlanReq } from "../models/update-action-plan-resuest";
@Injectable({
    providedIn:'root'
})

export class ActionPlanService{

  private BASE_URL = 'http://localhost:8080/api/v1/action-plan';
 
  constructor(private http: HttpClient) {}

    getAll(): Observable<ActionPlan[]> {
    return this.http
      .get<ActionPlan[]>(
         `${this.BASE_URL}`
        )
      
  }

  search(request: SearchRequestDto): Observable<PageResponse<ActionPlan>> {
  return this.http.post<PageResponse<ActionPlan>>(
    `${this.BASE_URL}/search`,
    request
  );
}

  createLog(dailyLog: CreateActionPlanReq): Observable<ActionPlan> {
    return this.http.post<ActionPlan>(this.BASE_URL, dailyLog)
  }

  updateLog(dailyLog: UpdateActionPlanReq): Observable<ActionPlan> {
    return this.http.put<ActionPlan>(
      `${this.BASE_URL}`,
      dailyLog);
  }

  deleteLog(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.BASE_URL}/${id}`
    )
  }

  
}