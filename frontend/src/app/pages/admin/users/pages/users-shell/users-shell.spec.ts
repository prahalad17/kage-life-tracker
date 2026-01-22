import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersShell } from './users-shell';

describe('UsersShell', () => {
  let component: UsersShell;
  let fixture: ComponentFixture<UsersShell>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsersShell]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsersShell);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
