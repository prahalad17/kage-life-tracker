import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActionPlanOverview } from './action-plan-overview';

describe('ActionPlanOverview', () => {
  let component: ActionPlanOverview;
  let fixture: ComponentFixture<ActionPlanOverview>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActionPlanOverview]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActionPlanOverview);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
