import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PillarForm } from './pillar-form';

describe('PillarForm', () => {
  let component: PillarForm;
  let fixture: ComponentFixture<PillarForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PillarForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PillarForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
