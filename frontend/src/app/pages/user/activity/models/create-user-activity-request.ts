export interface CreateUserActivityRequest
 {
  name: string;
  description: string;
  pillarId: number;
  activityNature : string;
  defaultTrackingType: string;
  defaultUnit: string;
  
}