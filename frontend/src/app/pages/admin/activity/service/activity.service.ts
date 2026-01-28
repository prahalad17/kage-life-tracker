import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';

import {map, Observable } from 'rxjs';
import { Activity } from "../models/activity.model";
import { ApiResponse } from "../../../../shared/models/api/api-response.model";
import { CreateActivityRequest } from "../models/create-activity-request";
import { UpdateActivityRequest } from "../models/update-activity-request";

@Injectable({
    providedIn:'root'
})
export class ActivityService{

    private readonly baseUrl = 'http://localhost:8080/api/v1/activity-template';

     constructor(private http: HttpClient) {}

     getAll(): Observable<Activity[]> {
         return this.http
           .get<ApiResponse<Activity[]>>(this.baseUrl)
           .pipe(
             map(res => {
             // console.log('RAW API RESPONSE:', res);
             // console.log('TYPE OF data:', typeof res.data);
             // console.log('IS ARRAY:', Array.isArray(res.data));
             return res.data ?? [];
           })
           );
       }

     createActivity(activity: CreateActivityRequest): Observable<Activity> {
        return this.http.post<ApiResponse<Activity>>(this.baseUrl, activity)
              .pipe(map(res => res.data));

     }

     updateActivity(activity: UpdateActivityRequest): Observable<Activity> {
         return this.http.put<ApiResponse<Activity>>(
           `${this.baseUrl}/${activity.id}`,
           activity
         ).pipe(map(res => res.data));
       }
     
       deleteActivity(id: number): Observable<void> {
         return this.http.delete<ApiResponse<void>>(
           `${this.baseUrl}/${id}`
         ).pipe(map(() => void 0));
       }

}