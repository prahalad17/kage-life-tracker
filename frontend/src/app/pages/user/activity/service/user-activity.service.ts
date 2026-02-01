import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';

import {map, Observable } from 'rxjs';
import { UserActivity } from "../models/user-activity.model";
import { ApiResponse } from "../../../../shared/models/api/api-response.model";
import { CreateUserActivityRequest } from "../models/create-user-activity-request";
import { UpdateUserActivityRequest } from "../models/update-user-activity-request";

@Injectable({
    providedIn:'root'
})
export class UserActivityService{

    private readonly baseUrl = 'http://localhost:8080/api/v1/activity-template';

     constructor(private http: HttpClient) {}

     getAll(): Observable<UserActivity[]> {
         return this.http
           .get<ApiResponse<UserActivity[]>>(this.baseUrl)
           .pipe(
             map(res => {
             // console.log('RAW API RESPONSE:', res);
             // console.log('TYPE OF data:', typeof res.data);
             // console.log('IS ARRAY:', Array.isArray(res.data));
             return res.data ?? [];
           })
           );
       }

     createActivity(activity: CreateUserActivityRequest): Observable<UserActivity> {
        return this.http.post<ApiResponse<UserActivity>>(this.baseUrl, activity)
              .pipe(map(res => res.data));

     }

     updateActivity(activity: UpdateUserActivityRequest): Observable<UserActivity> {
         return this.http.put<ApiResponse<UserActivity>>(
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