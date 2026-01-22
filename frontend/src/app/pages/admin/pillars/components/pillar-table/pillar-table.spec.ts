import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PillarTableComponent } from './pillar-table';

describe('PillarTable', () => {
  let component: PillarTableComponent;
  let fixture: ComponentFixture<PillarTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PillarTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PillarTableComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
