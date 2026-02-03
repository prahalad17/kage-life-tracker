export interface UpdateDailyLogReq
 {
  activityDailyLogId: number;
  activityId: number;
  actualValue: number;
  completed: boolean;
  notes:string;
}