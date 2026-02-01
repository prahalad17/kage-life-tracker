import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PillarShell } from './pillar-shell';

describe('PillarShell', () => {
  let component: PillarShell;
  let fixture: ComponentFixture<PillarShell>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PillarShell]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PillarShell);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
