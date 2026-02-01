import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PillarOverview } from './pillar-overview';

describe('PillarOverview', () => {
  let component: PillarOverview;
  let fixture: ComponentFixture<PillarOverview>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PillarOverview]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PillarOverview);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
