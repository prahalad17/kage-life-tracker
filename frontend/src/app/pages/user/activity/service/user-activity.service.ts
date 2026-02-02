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

      private BASE_URL = 'http://localhost:8080/api/v1/activity';

     constructor(private http: HttpClient) {}

     getAll(): Observable<UserActivity[]> {
         return this.http
           .get<UserActivity[]>(this.BASE_URL);
       }

     createActivity(activity: CreateUserActivityRequest): Observable<UserActivity> {
        return this.http.post<UserActivity>(this.BASE_URL, activity);

     }

     updateActivity(activity: UpdateUserActivityRequest): Observable<UserActivity> {
         return this.http.put<UserActivity>(
           `${this.BASE_URL}`,
           activity
         );
       }
     
       deleteActivity(id: number): Observable<void> {
         return this.http.delete<void>(
           `${this.BASE_URL}/${id}`
         );
       }

}