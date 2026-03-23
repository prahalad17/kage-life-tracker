export interface CreateUserPillarRequest
 {
  pillarName: string;
  pillarDescription: string;
  priorityWeight:number;
  orderIndex:number;
  pillarColor:number;

  pillarTemplateId: number;
}