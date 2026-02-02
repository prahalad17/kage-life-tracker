export type FieldType =
  | 'text'
  | 'email'
  | 'password'
  | 'number'
  | 'textarea'
  | 'select'
  | 'checkbox'
  | 'radio'
  | 'date';

  export type OptionsSource =
  | {
      type: 'static';
      options: {
        label: string;
        value: any;
      }[];
    }
  | {
      type: 'api';
      endpoint: string;
      labelKey: string;
      valueKey: string;
      incomingKey: string; 
    };

  export interface FieldDependency {
  field: string;          // controlling field
  value?: any;            // value to match
  condition?: (val: any) => boolean; // advanced use
  action?: 'show' | 'hide' | 'enable' | 'disable';
}

  export interface FormFieldConfig {
  name: string;
  label: string;
  type: FieldType;

  placeholder?: string;
  defaultValue?: any;

  required?: boolean;
  disabled?: boolean;
  hidden?: boolean;

  // Validation
  minLength?: number;
  maxLength?: number;
  min?: number;
  max?: number;
  pattern?: string;

  // For select / radio
 optionsConfig?: OptionsSource;

  // UI helpers
  width?: string;      // 100%, 50%, etc
  cssClass?: string;

  // Advanced
  dependsOn?: FieldDependency; // field dependency
}

