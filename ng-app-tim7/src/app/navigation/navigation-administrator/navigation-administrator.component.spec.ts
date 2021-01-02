import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationAdministratorComponent } from './navigation-administrator.component';

describe('NavigationAdministratorComponent', () => {
  let component: NavigationAdministratorComponent;
  let fixture: ComponentFixture<NavigationAdministratorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavigationAdministratorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavigationAdministratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
