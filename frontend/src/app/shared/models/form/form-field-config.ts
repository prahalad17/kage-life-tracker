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
  options?: {
    label: string;
    value: any;
  }[];

  // UI helpers
  width?: string;      // 100%, 50%, etc
  cssClass?: string;

  // Advanced
  dependsOn?: string; // field dependency
}

