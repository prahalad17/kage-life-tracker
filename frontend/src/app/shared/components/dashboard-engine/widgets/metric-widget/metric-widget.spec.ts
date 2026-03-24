import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MetricWidget } from './metric-widget';

describe('MetricWidget', () => {
  let component: MetricWidget;
  let fixture: ComponentFixture<MetricWidget>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MetricWidget]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MetricWidget);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
