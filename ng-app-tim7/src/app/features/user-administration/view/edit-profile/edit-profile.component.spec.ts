import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditProfileComponent } from './edit-profile.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Store, StoreModule} from '@ngrx/store';
import * as fromApp from '../../../../store/app.reducer';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDividerModule} from '@angular/material/divider';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {Router} from '@angular/router';
import {MatCardModule} from '@angular/material/card';
import * as AdminActions from '../../administrator-administration/store/administrator.actions';
import {of} from 'rxjs';

describe('EditProfileComponent', () => {
  let component: EditProfileComponent;
  let fixture: ComponentFixture<EditProfileComponent>;
  let router: any;
  let store: Store;
  const accessToken = 'eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6Im1pY28iLCJhdWQiOiJ3ZWIiLCJpYXQiOjE2MTA4OTIzNDIsImV4cCI6MTYxMDg5NDE0MiwidXNlcm5hbWUiOiJtaWNvIiwiaWQiOjUwMDUsInJvbGUiOiJST0xFX0FETUlOSVNUUkFUT1IifQ.we4NyEH-EJMIRgFCsJAmSOOKJDleKY2h-KB6lB7PXm-0RUtG7mkD_SF-jsGQ-yCARzx-imPDMiQ2G18ZJwKKrg';

  beforeEach(async(() => {
    const routerMock = {
      navigate: jasmine.createSpy('navigate')
    };
    TestBed.configureTestingModule({
      declarations: [ EditProfileComponent ],
      imports: [FormsModule, ReactiveFormsModule,  StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule, MatDividerModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatInputModule, MatCardModule ],
      providers: [Store,
        { provide: Router, useValue: routerMock }]
    })
    .compileComponents();

    router = TestBed.inject(Router);
    store = TestBed.inject(Store);
  }));

  beforeEach(() => {
    let storeLocal = {};
    const mockLocalStorage = {
      getItem: (key: string): string => {
        return key in storeLocal ? storeLocal[key] : null;
      },
      setItem: (key: string, value: string) => {
        storeLocal[key] = `${value}`;
      },
      clear: () => {
        storeLocal = {};
      }
    };

    spyOn(localStorage, 'getItem')
      .and.callFake(mockLocalStorage.getItem);
    spyOn(localStorage, 'setItem')
      .and.callFake(mockLocalStorage.setItem);
    spyOn(localStorage, 'clear')
      .and.callFake(mockLocalStorage.clear);

    const userLocalStorage = { username: 'mico', id: 5005, accessToken, role: 'ROLE_ADMINISTRATOR' };
    localStorage.setItem('user', JSON.stringify(userLocalStorage));
    fixture = TestBed.createComponent(EditProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(component.userType).toEqual('administrator');
    expect(component.Actions).toEqual(AdminActions);
  });

  describe('ngOnInit()', () => {
    it('should subscribe to store in ngOnInit lifecycle', () => {

      component.ngOnInit();
      fixture.detectChanges();
      expect(component.error).toBe(null);
      expect(component.success).toBe(null);
      expect(component.bar).toBe(false);
    });
  });

  describe('ngOnInit(), redirect', () => {
    it('should subscribe to store in ngOnInit lifecycle, without going to ViewProfile first', () => {
      localStorage.clear();
      component.ngOnInit();
      fixture.detectChanges();
      expect(router.navigate).toHaveBeenCalledWith(['/' + component.userType + '/view-profile']);
    });
  });


  describe('submit()', () => {
    it('should dispatch SignInStart action', () => {
      expect(component.form.valid).toBeFalsy();
      const action = new AdminActions.EditProfile(
        {id: JSON.parse(localStorage.getItem('user')).id, username: component.user, email: 'email', password: 'password' });
      const spy = spyOn(store, 'dispatch');
      component.form.controls.email.setValue('email');
      component.form.controls.password.setValue('password');
      component.submit();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('showErrorAlert()', () => {
    it('should dispatch ClearError action', () => {
      const action = new AdminActions.ClearError();
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      component.showErrorAlert('error message');
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.snackBar.open).toHaveBeenCalledWith('error message', 'Ok', {duration: 2000});
      expect(component.error).toEqual(null);
    });
  });

  describe('showSuccessAlert()', () => {
    it('should dispatch ClearSuccess action', () => {
      const action = new AdminActions.ClearSuccess();
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      component.showSuccessAlert('success message');
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.snackBar.open).toHaveBeenCalledWith('success message', 'Ok', {duration: 2000});
      expect(component.success).toEqual(null);
    });
  });

  describe('ngOnDestroy()', () => {
    it('should unsubscribe to store in ngOnDestroy lifecycle', () => {

      component.storeSub = of(true).subscribe();

      component.ngOnDestroy();

      expect(component.storeSub.closed).toBeTruthy();
    });
  });
});
