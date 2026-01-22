import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersOverviewComponent } from './users-overview';

describe('UsersOverviewComponent', () => {
  let component: UsersOverviewComponent;
  let fixture: ComponentFixture<UsersOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsersOverviewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsersOverviewComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
