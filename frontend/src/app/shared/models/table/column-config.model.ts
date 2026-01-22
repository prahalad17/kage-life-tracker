export interface ColumnConfig {

    key: string;
    header: string;
    type?: 'text'| 'date'| 'status' | 'custom';
    visible?: boolean;
    sortable?: boolean;
}