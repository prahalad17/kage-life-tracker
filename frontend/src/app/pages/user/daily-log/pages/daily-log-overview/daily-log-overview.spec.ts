import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DailyLogOverview } from './daily-log-overview';

describe('DailyLogOverview', () => {
  let component: DailyLogOverview;
  let fixture: ComponentFixture<DailyLogOverview>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DailyLogOverview]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DailyLogOverview);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
