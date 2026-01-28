import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityOverview } from './activity-overview';

describe('ActivityOverview', () => {
  let component: ActivityOverview;
  let fixture: ComponentFixture<ActivityOverview>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActivityOverview]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActivityOverview);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
