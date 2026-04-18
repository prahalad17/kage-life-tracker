export type FieldType =
  | 'text'
  | 'email'
  | 'password'
  | 'number'
  | 'textarea'
  | 'select'
  | 'checkbox'
  | 'radio'
  | 'color'
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
  field: string;

  // condition
  value?: any;
  condition?: (val: any) => boolean;

  // actions
  actions?: {
    type: 'show' | 'hide' | 'enable' | 'disable' | 'patchFromOption' | 'setValue';
    
    // for enable/disable/show/hide
    targets?: string[];

    // for patching
    mapping?: Record<string, string>; 
    // { targetField: sourceKey }

    // for static values
    values?: Record<string, any>;
  }[];
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

