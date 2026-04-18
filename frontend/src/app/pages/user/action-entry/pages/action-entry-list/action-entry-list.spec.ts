import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActionEntryList } from './action-entry-list';

describe('ActionEntryList', () => {
  let component: ActionEntryList;
  let fixture: ComponentFixture<ActionEntryList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActionEntryList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActionEntryList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
