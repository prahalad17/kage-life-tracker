import { ColumnConfig } from './column-config.model';
import { ActionConfig } from './action-config.model';
import { CreateConfig } from './create-config';

export interface TableConfig {
  columns: ColumnConfig[];
  actions?: ActionConfig[];
  create?: CreateConfig;
  selectable?: boolean;
  pagination?: boolean;
}