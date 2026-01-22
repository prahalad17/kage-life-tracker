import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PillarsShellComponent } from './pillars-shell';

describe('PillarsShellComponent', () => {
  let component: PillarsShellComponent;
  let fixture: ComponentFixture<PillarsShellComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PillarsShellComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PillarsShellComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
