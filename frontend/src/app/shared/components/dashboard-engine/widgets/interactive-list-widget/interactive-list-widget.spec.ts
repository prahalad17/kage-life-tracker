import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InteractiveListWidget } from './interactive-list-widget';

describe('InteractiveListWidget', () => {
  let component: InteractiveListWidget;
  let fixture: ComponentFixture<InteractiveListWidget>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InteractiveListWidget]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InteractiveListWidget);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
