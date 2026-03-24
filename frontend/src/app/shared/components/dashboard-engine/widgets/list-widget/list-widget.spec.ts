import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListWidget } from './list-widget';

describe('ListWidget', () => {
  let component: ListWidget;
  let fixture: ComponentFixture<ListWidget>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListWidget]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListWidget);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
