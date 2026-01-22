import {  Component, Input, Output, EventEmitter } from '@angular/core';
import { TableConfig } from '../../models/table/table-config.model';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-data-table',
  imports: [CommonModule],
  templateUrl: './data-table.html',
  styleUrl: './data-table.css',
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


}
