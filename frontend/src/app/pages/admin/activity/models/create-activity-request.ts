export interface CreateActivityRequest
 {
  name: string;
  description: string;
  pillarName: string;
  pillarTemplateId: number;
  activityNature : string;
  defaultTrackingType: string;
  defaultUnit: string;
  
}