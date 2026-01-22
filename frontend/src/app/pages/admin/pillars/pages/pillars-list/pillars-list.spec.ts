import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PillarsListComponent } from './pillars-list';

describe('PillarsListComponent', () => {
  let component: PillarsListComponent;
  let fixture: ComponentFixture<PillarsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PillarsListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PillarsListComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
