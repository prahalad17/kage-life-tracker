import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActionEntryOverview } from './action-entry-overview';

describe('ActionEntryOverview', () => {
  let component: ActionEntryOverview;
  let fixture: ComponentFixture<ActionEntryOverview>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActionEntryOverview]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActionEntryOverview);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
