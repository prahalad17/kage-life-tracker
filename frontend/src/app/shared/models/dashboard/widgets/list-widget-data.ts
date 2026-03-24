export interface ListItem {
  id: string;
  title: string;
  subtitle?: string;
}

export interface ListWidgetData {
  items: ListItem[];
}