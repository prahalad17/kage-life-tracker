import {  Component, Input, Output, EventEmitter } from '@angular/core';
import { TableConfig } from '../../models/table/table-config.model';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-data-table',
  imports: [CommonModule],
  templateUrl: './data-table.html',
  styleUrl: './data-table.scss',
})
export class DataTable {

  @Input() rows: any[]=[];
  @Input() config!: TableConfig;

  @Output() action = new EventEmitter<{type: string; row:any}>();
  @Output() create = new EventEmitter<void>();

  onActionClick(type: string, row: any): void {
    this.action.emit({ type, row });
  }

   onCreateClick(): void {
    this.create.emit();
  }


  @Input() totalElements = 0;
  @Input() pageIndex = 0;
  @Input() pageSize = 10;
  @Input() loading = false;

   @Output() pageChange = new EventEmitter<{
  pageIndex: number;
  pageSize: number;
  }>();
 
  get totalPages(): number {
  return Math.ceil(this.totalElements / this.pageSize);
}

get startItem(): number {
  return this.totalElements === 0
    ? 0
    : this.pageIndex * this.pageSize + 1;
}

get endItem(): number {
  return Math.min(
    (this.pageIndex + 1) * this.pageSize,
    this.totalElements
  );
}

onPrevious(): void {
  if (this.pageIndex > 0) {
    this.pageChange.emit({
      pageIndex: this.pageIndex - 1,
      pageSize: this.pageSize
    });
  }
}

onNext(): void {
  console.log('Next clicked', this.pageIndex);
  if (this.pageIndex < this.totalPages - 1) {
    this.pageChange.emit({
      pageIndex: this.pageIndex + 1,
      pageSize: this.pageSize
    });
  }
}

onPageSizeChange(size: number): void {
  this.pageChange.emit({
    pageIndex: 0, // reset to first page
    pageSize: size
  });
}


}
