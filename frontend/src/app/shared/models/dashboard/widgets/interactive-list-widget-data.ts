export interface InteractiveListItem {
  id: string;
  title: string;
  completed?: boolean;
}

export interface InteractiveListWidgetData {
  items: InteractiveListItem[];
}