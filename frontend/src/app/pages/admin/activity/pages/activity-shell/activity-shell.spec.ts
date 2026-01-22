import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityShellComponent } from './activity-shell';

describe('ActivityShellComponent', () => {
  let component: ActivityShellComponent;
  let fixture: ComponentFixture<ActivityShellComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActivityShellComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActivityShellComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
