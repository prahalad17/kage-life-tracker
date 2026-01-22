import { Component } from '@angular/core';
import { ActivityTable } from '../../components/activity-table/activity-table';
import { ActivityForm } from '../../components/activity-form/activity-form';
import { Observable } from 'rxjs';
import { Activity } from '../../models/activity.model';
import { ActivityService } from '../../service/activity.service';
import { CommonModule } from '@angular/common';
import { ConfirmDialog } from '../../../../../shared/components/confirm-dialog/confirm-dialog';


@Component({
  standalone: true,
  selector: 'app-activity-list',
  imports: [ActivityTable,
     ActivityForm,
    CommonModule,
  ConfirmDialog],
  templateUrl: './activity-list.html',
  styleUrl: './activity-list.css',
})
export class ActivityListComponent {

activity$! : Observable<Activity[]>;

// ===== FORM MODAL STATE =====
  showForm = false;
  selectedActivity: Activity | null = null;

// ===== CONFIRM MODAL STATE =====
  showConfirm = false;
  activityToDelete: Activity | null = null;

constructor(private activityService: ActivityService) {}

// ===== LIFECYCLE =====
  ngOnInit(): void {
    this.activity$ = this.activityService.getAll();
  }

   // ===== LOAD DATA =====
  private loadActivity(): void {
    this.activityService.getAll().subscribe({
    next: (activity) => {
      // this.pillars = pillars;
    },
    error: (err) => console.error('Failed to load activity', err)
  });
  }


  // ===== CREATE / EDIT =====
    openCreate(): void {
      this.selectedActivity = null;
      this.showForm = true;
    }
  
    openEdit(activity: Activity): void {
      this.selectedActivity = activity;
      this.showForm = true;
    }


     onSaveActivity(activity: Activity): void {
        const request$ = activity.id
          ? this.activityService.update(activity)
          : this.activityService.create(activity);
    
        request$.subscribe({
          next: () => {
            // this.loadPillars();
            this.showForm = false;
          },
          error: (err) => console.error('Save failed', err)
        });
      }

    onCancel(): void {
    this.showForm = false;
  }

  // ===== DELETE =====
    confirmDelete(activity: Activity): void {
      this.activityToDelete = activity;
      this.showConfirm = true;
    }

    onConfirmDelete(): void {
    if (!this.activityToDelete) return;

    this.activityService.delete(this.activityToDelete.id).subscribe({
      next: () => {
        this.loadActivity();
        this.closeConfirm();
      },
      error: (err) => console.error('Delete failed', err)
    });
  }

  private closeConfirm(): void {
    this.showConfirm = false;
    this.activityToDelete = null;
  }

  // ===== CONFIRM MESSAGE =====
  get deleteMessage(): string {
    return this.activityToDelete
      ? `Are you sure you want to delete "${this.activityToDelete.name}"?`
      : 'Are you sure?';
  }

}
