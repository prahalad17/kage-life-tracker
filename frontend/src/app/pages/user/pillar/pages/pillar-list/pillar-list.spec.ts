import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PillarList } from './pillar-list';

describe('PillarList', () => {
  let component: PillarList;
  let fixture: ComponentFixture<PillarList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PillarList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PillarList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
