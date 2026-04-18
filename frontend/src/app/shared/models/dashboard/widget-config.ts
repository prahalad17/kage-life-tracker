import { WidgetType } from './widget-type';

export interface WidgetConfig {
  id: string;                // unique per dashboard
  type: WidgetType;          // which widget to render
  title?: string;            // optional title

  dataKey: string;           // KEY to fetch data from dashboardData

  display?: {
    size?: 'sm' | 'md' | 'lg';
    variant?: 'card' | 'minimal';
  };

  visible?: boolean | ((data: any) => boolean);

  emptyState?: string;
  
}