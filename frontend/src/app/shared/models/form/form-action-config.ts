export interface FormActionConfig {
  label: string;
  type: 'submit' | 'reset' | 'button';

  action?: string; // create | update | cancel | custom

  color?: 'primary' | 'accent' | 'warn';
  hidden?: boolean;
  disabled?: boolean;
}
