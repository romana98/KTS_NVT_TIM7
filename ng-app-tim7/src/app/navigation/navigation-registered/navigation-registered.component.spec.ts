import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationRegisteredComponent } from './navigation-registered.component';
import {MatToolbarModule} from '@angular/material/toolbar';

describe('NavigationRegisteredComponent', () => {
  let component: NavigationRegisteredComponent;
  let fixture: ComponentFixture<NavigationRegisteredComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavigationRegisteredComponent ],
      imports: [MatToolbarModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavigationRegisteredComponent);
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
