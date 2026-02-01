import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityShell } from './activity-shell';

describe('ActivityShell', () => {
  let component: ActivityShell;
  let fixture: ComponentFixture<ActivityShell>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActivityShell]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActivityShell);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
