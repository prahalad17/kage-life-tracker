import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PillarsOverview } from './pillars-overview';

describe('PillarsOverview', () => {
  let component: PillarsOverview;
  let fixture: ComponentFixture<PillarsOverview>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PillarsOverview]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PillarsOverview);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
