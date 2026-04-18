export interface UpdateActionPlanReq
 {
  actionPlanId: number;
  actionPlanDate: Date;
  actionPlanName: string;
  actionPlanStatus: string;
  actionPlanNature: string;
  actionPlanTrackingType: string;
  activityId: number;
  pillarId: number;
  actionPlanNotes: string;
}