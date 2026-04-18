export interface WidgetEvent {
  widgetId: string;      // which widget triggered
  actionType: string;    // what action happened
  payload?: any;         // extra data
}