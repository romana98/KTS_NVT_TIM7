import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivateAccountComponent } from './activate-account.component';
import {Store, StoreModule} from '@ngrx/store';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import * as fromApp from '../../../../store/app.reducer';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';
import * as ActivateAccountActions from '../../store/activate-account.actions';

describe('ActivateAccountComponent', () => {
  let component: ActivateAccountComponent;
  let fixture: ComponentFixture<ActivateAccountComponent>;
  let store: Store;
  let activatedRoute: ActivatedRoute;
  const id = 10;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActivateAccountComponent ],
      imports: [FormsModule, ReactiveFormsModule,  StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatInputModule],
      providers: [Store, { provide: ActivatedRoute, useValue: { queryParams: of({ id })}}]
    })
    .compileComponents();

    store = TestBed.inject(Store);
    activatedRoute = TestBed.inject(ActivatedRoute);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivateAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should subscribe to store in ngOnInit lifecycle', () => {
      const action = (new ActivateAccountActions.ActivateStart({ id }));
      const spy = spyOn(store, 'dispatch');
      component.ngOnInit();
      fixture.detectChanges();
      expect(component.error).toBe(null);
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('showErrorAlert()', () => {
    it('should dispatch ClearError action', () => {
      const action = new ActivateAccountActions.ClearError();
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
      const action = new ActivateAccountActions.ClearSuccess();
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
