import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationAdministratorComponent } from './navigation-administrator.component';
import {MatToolbarModule} from '@angular/material/toolbar';

describe('NavigationAdministratorComponent', () => {
  let component: NavigationAdministratorComponent;
  let fixture: ComponentFixture<NavigationAdministratorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavigationAdministratorComponent ],
      imports: [MatToolbarModule]
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

  describe('signOutUser()', () => {
    it('should emit signOut()', () => {

      const spy = spyOn(component.signOut, 'emit');
      component.signOutUser();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith();
    });
  });
});
