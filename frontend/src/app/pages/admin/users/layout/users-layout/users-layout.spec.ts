import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersLayoutComponent } from './users-layout';

describe('UsersLayoutComponent', () => {
  let component: UsersLayoutComponent;
  let fixture: ComponentFixture<UsersLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsersLayoutComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsersLayoutComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
