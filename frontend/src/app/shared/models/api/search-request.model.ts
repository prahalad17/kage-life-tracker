export interface SearchRequestDto {
  page?: number;
  size?: number;
  sort?: SortCriteria[];
  filters?: FilterCriteria[];
}

export interface SortCriteria {
  field: string;
  direction: 'ASC' | 'DESC';
}

export interface FilterCriteria {
  field: string;
  operator: string;
  value: any;
}