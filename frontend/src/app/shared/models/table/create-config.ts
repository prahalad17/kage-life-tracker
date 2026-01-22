export interface CreateConfig {
  enabled: boolean;
  label?: string;          // "Add User"
  mode?: 'dialog' | 'inline';
}