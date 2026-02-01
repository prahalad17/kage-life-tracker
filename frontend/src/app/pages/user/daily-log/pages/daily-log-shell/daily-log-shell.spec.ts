import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DailyLogShell } from './daily-log-shell';

describe('DailyLogShell', () => {
  let component: DailyLogShell;
  let fixture: ComponentFixture<DailyLogShell>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DailyLogShell]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DailyLogShell);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
