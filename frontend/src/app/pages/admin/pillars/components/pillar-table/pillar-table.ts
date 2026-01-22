import { Component, Input,Output, EventEmitter } from '@angular/core';
import { Pillar } from '../../models/pillar.model';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-pillar-table',
  imports: [CommonModule],
  templateUrl: './pillar-table.html',
  styleUrl: './pillar-table.css',
})
export class PillarTableComponent  {

  @Input() pillars: Pillar[] = [];

  // ngOnChanges(changes: SimpleChanges): void {
  //   console.log('TABLE RECEIVED PILLARS:', this.pillars);
  // }

  @Output()
  edit = new EventEmitter<Pillar>();
   onEdit(pillar: Pillar): void {
    this.edit.emit(pillar);
  }

  @Output()
  delete = new EventEmitter<Pillar>();
  onDelete(pillar: Pillar): void {
    this.delete.emit(pillar);
  }

}
