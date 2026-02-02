export interface CreateActivityRequest
 {
  name: string;
  description: string;
  pillarTemplateId: number;
  activityNature : string;
  defaultTrackingType: string;
  defaultUnit: string;
  
}