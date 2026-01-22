import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Pillar } from '../../models/pillar.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-pillar-form',
  imports: [CommonModule,FormsModule],
  templateUrl: './pillar-form.html',
  styleUrl: './pillar-form.css',
})
export class PillarForm {

   @Input()
  pillar: Pillar | null = null;   // null = create, non-null = edit

  @Output()
  save = new EventEmitter<Pillar>();

  @Output()
  cancel = new EventEmitter<void>();

  pillarName = '';

   ngOnInit(): void {
    if (this.pillar) {
      this.pillarName = this.pillar.name;
    }
  }

  onSave(): void {
    const result: Pillar = {
      id: this.pillar?.id ?? Date.now(),
      name: this.pillarName
    };

    this.save.emit(result);
  }

}
