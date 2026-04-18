import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActionPlanShell } from './action-plan-shell';

describe('ActionPlanShell', () => {
  let component: ActionPlanShell;
  let fixture: ComponentFixture<ActionPlanShell>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActionPlanShell]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActionPlanShell);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
