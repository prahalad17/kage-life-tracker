export interface ActionConfig{
type: string;          // 'edit', 'delete', 'view', 'custom'
  label: string;
  icon?: string;
  visible?: boolean;
  confirm?: boolean;
}