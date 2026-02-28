import { ColumnConfig } from './column-config.model';
import { ActionConfig } from './action-config.model';
import { CreateConfig } from './create-config';

export interface TableConfig {
  columns: ColumnConfig[];
  actions?: ActionConfig[];
  tableName?: string;
  create?: CreateConfig;
  selectable?: boolean;
  pagination?: {
    enabled: boolean;
    pageSizeOptions?: number[];
    defaultPageSize?: number;
  };
}