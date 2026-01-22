import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Pillar } from '../../models/pillar.model';
import { PillarService } from '../../service/pillar.service';

import { PillarTableComponent } from '../../components/pillar-table/pillar-table';
import { PillarForm } from '../../components/pillar-form/pillar-form';

import { Observable } from 'rxjs';
import { ConfirmDialog } from '../../../../../shared/components/confirm-dialog/confirm-dialog';

@Component({
  standalone: true,
  selector: 'app-pillars-list',
  imports: [
    CommonModule,
    PillarTableComponent,
    PillarForm,
    ConfirmDialog
    
  ],
  templateUrl: './pillars-list.html',
  styleUrl: './pillars-list.css',
})
export class PillarsListComponent implements OnInit {

  // ===== DATA =====
  pillars$!: Observable<Pillar[]>;

  // ===== FORM MODAL STATE =====
  showForm = false;
  selectedPillar: Pillar | null = null;

  // ===== CONFIRM MODAL STATE =====
  showConfirm = false;
  pillarToDelete: Pillar | null = null;

  constructor(private pillarService: PillarService) {}

  // ===== LIFECYCLE =====
  ngOnInit(): void {
    this.pillars$ = this.pillarService.getAll();
  }

  // ===== LOAD DATA =====
  private loadPillars(): void {
    this.pillarService.getAll().subscribe({
    next: (pillars) => {
      // this.pillars = pillars;
    },
    error: (err) => console.error('Failed to load pillars', err)
  });
  }

  // ===== CREATE / EDIT =====
  openCreate(): void {
    this.selectedPillar = null;
    this.showForm = true;
  }

  openEdit(pillar: Pillar): void {
    this.selectedPillar = pillar;
    this.showForm = true;
  }

  onSavePillar(pillar: Pillar): void {
    const request$ = pillar.id
      ? this.pillarService.update(pillar)
      : this.pillarService.create(pillar);

    request$.subscribe({
      next: () => {
        this.loadPillars();
        this.showForm = false;
      },
      error: (err) => console.error('Save failed', err)
    });
  }

  onCancel(): void {
    this.showForm = false;
  }

  // ===== DELETE =====
  confirmDelete(pillar: Pillar): void {
    this.pillarToDelete = pillar;
    this.showConfirm = true;
  }

  onConfirmDelete(): void {
    if (!this.pillarToDelete) return;

    this.pillarService.delete(this.pillarToDelete.id).subscribe({
      next: () => {
        this.loadPillars();
        this.closeConfirm();
      },
      error: (err) => console.error('Delete failed', err)
    });
  }

  onCancelDelete(): void {
    this.closeConfirm();
  }

  private closeConfirm(): void {
    this.showConfirm = false;
    this.pillarToDelete = null;
  }

  // ===== CONFIRM MESSAGE =====
  get deleteMessage(): string {
    return this.pillarToDelete
      ? `Are you sure you want to delete "${this.pillarToDelete.name}"?`
      : 'Are you sure?';
  }
}
