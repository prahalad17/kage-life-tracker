import { FormFieldConfig } from "./form-field-config";
import { FormActionConfig } from "./form-action-config";

export interface FormConfig {
  title?: string;
  fields: FormFieldConfig[];

  mode?: 'create' | 'edit' | 'view';

  actions?: FormActionConfig[];

  layout?: 'vertical' | 'horizontal' | 'grid';
  gridColumns?: number; // used if layout = grid

  readOnly?: boolean;
}
