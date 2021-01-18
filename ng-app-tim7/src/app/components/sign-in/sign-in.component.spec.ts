import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { SignInComponent } from './sign-in.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Store, StoreModule} from '@ngrx/store';
import * as fromApp from '../../store/app.reducer';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDividerModule} from '@angular/material/divider';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BrowserModule} from '@angular/platform-browser';
import {of} from 'rxjs';
import * as AuthActions from './store/sign-in.actions';

describe('SignInComponent', () => {
  let component: SignInComponent;
  let fixture: ComponentFixture<SignInComponent>;
  let store: Store;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SignInComponent ],
      imports: [FormsModule, ReactiveFormsModule,  StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule, MatDividerModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatInputModule ],
      providers: [Store]
    })
    .compileComponents();

    store = TestBed.inject(Store);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignInComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should subscribe to store in ngOnInit lifecycle', () => {

      fixture.detectChanges();
      component.ngOnInit();
      expect(component.error).toBe(null);
    });
/*
    it('should subscribe to store in ngOnInit lifecycle and change error', () => {

      component.store.dispatch(new AuthActions.AuthenticateFail('error message'));

      fixture.detectChanges();
      expect(component.error).toEqual('error message');
    });
    */
  });

  describe('submit()', () => {
    it('should dispatch SignInStart action', () => {
      const action = new AuthActions.SignInStart({ username: 'username', password: 'password' });
      const spy = spyOn(store, 'dispatch');
      component.form.controls.username.setValue('username');
      component.form.controls.password.setValue('password');
      fixture.detectChanges();
      component.submit();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('showErrorAlert()', () => {
    it('should dispatch ClearAction action', () => {
      const action = new AuthActions.ClearError();
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      fixture.detectChanges();
      component.showErrorAlert('error message');
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.snackBar.open).toHaveBeenCalledWith('error message', 'Ok', {duration: 2000});
      expect(component.error).toEqual(null);
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
