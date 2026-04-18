import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TextWidget } from './text-widget';

describe('TextWidget', () => {
  let component: TextWidget;
  let fixture: ComponentFixture<TextWidget>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TextWidget]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TextWidget);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
