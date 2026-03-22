import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActionEntryShell } from './action-entry-shell';

describe('ActionEntryShell', () => {
  let component: ActionEntryShell;
  let fixture: ComponentFixture<ActionEntryShell>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActionEntryShell]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActionEntryShell);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
