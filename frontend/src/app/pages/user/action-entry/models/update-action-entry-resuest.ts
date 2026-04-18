export interface UpdateActionEntryReq
 {
  actionEntryId: number;
  actionEntryDate: Date;
  actionEntryName: string;
  actionEntryStatus: string;
  actionEntryNature: string;
  actionEntryTrackingType: string;
  activityId: number;
  pillarId: number;
  actionEntryNotes: string;
}