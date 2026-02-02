export interface UpdateActivityRequest
 {
  activityId: number;
  name: string;
  description: string;
  pillarTemplateId: number;
  activityNature : string;
  defaultTrackingType: string;
  defaultUnit: string;
}