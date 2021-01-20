import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import { AddAdministratorComponent } from './add-administrator.component';
import {FormGroupDirective, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Store, StoreModule} from '@ngrx/store';
import * as fromApp from '../../../../../store/app.reducer';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDividerModule} from '@angular/material/divider';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import * as AdministratorActions from '../../store/administrator.actions';
import {of} from 'rxjs';

describe('AddAdministratorComponent', () => {
  let component: AddAdministratorComponent;
  let fixture: ComponentFixture<AddAdministratorComponent>;
  let store: Store;

  const formGroupDirective = jasmine.createSpyObj('FormGroupDirective', ['resetForm']);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddAdministratorComponent, FormGroupDirective ],
      imports: [FormsModule, ReactiveFormsModule,  StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule, MatDividerModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatInputModule ],
      providers: [Store]
    })
    .compileComponents();

    store = TestBed.inject(Store);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddAdministratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
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

  describe('submit()', () => {
    it('should dispatch SignInStart action', () => {
      expect(component.form.valid).toBeFalsy();
      const action = new AdministratorActions.AddAdmin({ username: 'username', email: 'email', password: 'password' });
      const spy = spyOn(store, 'dispatch');
      component.form.controls.username.setValue('username');
      component.form.controls.email.setValue('email');
      component.form.controls.password.setValue('password');
      component.submit();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('showErrorAlert()', () => {
    it('should dispatch ClearError action', () => {
      const action = new AdministratorActions.ClearError();
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
    it('should dispatch ClearSuccess action', fakeAsync(() => {
      component.formGroupDirective = formGroupDirective;
      const action = new AdministratorActions.ClearSuccess();
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      component.showSuccessAlert('success message');
      tick();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.snackBar.open).toHaveBeenCalledWith('success message', 'Ok', {duration: 2000});
      expect(component.success).toEqual(null);
      expect(formGroupDirective.resetForm).toHaveBeenCalled();
    }));
  });

  describe('ngOnDestroy()', () => {
    it('should unsubscribe to store in ngOnDestroy lifecycle', () => {

      component.storeSub = of(true).subscribe();

      component.ngOnDestroy();

      expect(component.storeSub.closed).toBeTruthy();
    });
  });

});
