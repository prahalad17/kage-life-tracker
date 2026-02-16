export interface UpdateDailyLogReq
 {
  logId: number;
  activityId: number;
  actualValue: number;
  completed: boolean;
  notes:string;
}