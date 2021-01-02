import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardAdministratorComponent } from './dashboard-administrator.component';

describe('DashboardAdministratorComponent', () => {
  let component: DashboardAdministratorComponent;
  let fixture: ComponentFixture<DashboardAdministratorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashboardAdministratorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardAdministratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
