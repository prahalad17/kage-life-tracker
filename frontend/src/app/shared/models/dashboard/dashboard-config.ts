import { WidgetConfig } from './widget-config';

export interface DashboardConfig {
  id: string;

  layout: {
    type: 'grid' | 'stack';
    columns?: number; // for grid
  };

  widgets: WidgetConfig[];
}