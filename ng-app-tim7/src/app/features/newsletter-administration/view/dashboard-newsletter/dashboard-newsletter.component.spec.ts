import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Store, StoreModule} from '@ngrx/store';
import * as fromApp from '../../../../store/app.reducer';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDividerModule} from '@angular/material/divider';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BrowserModule} from '@angular/platform-browser';
import {of} from 'rxjs';
import * as NewsletterActions from '../../store/newsletter.actions';
import { Router } from '@angular/router';
import { MatSelectModule} from '@angular/material/select';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { DashboardNewsletterComponent } from './dashboard-newsletter.component';
import {SharedModule} from '../../../../shared/shared.module';

describe('DashboardNewsletterComponent', () => {
  let component: DashboardNewsletterComponent;
  let fixture: ComponentFixture<DashboardNewsletterComponent>;
  let store: Store;
  let router: Router;

  const mockRouter = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashboardNewsletterComponent ],
      imports: [FormsModule, ReactiveFormsModule,  StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule, MatDividerModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatProgressBarModule, SharedModule ],
      providers: [Store, {provide: Router, useValue: mockRouter}]
    })
    .compileComponents();
    store = TestBed.inject(Store);
    router = TestBed.inject(Router);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardNewsletterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should subscribe to store and get newsletters in ngOnInit lifecycle', () => {

      const action = new NewsletterActions.GetNewsletterPage({ page: 0, size: 10 });
      const spy = spyOn(store, 'dispatch');
      fixture.detectChanges();
      component.ngOnInit();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.error).toBe(null);

    });
  });

  describe('onDelete()', () => {
    it('should dispatch delete newsletter', () => {

      const action = new NewsletterActions.DeleteNewsletter(1);
      const spy = spyOn(store, 'dispatch');
      fixture.detectChanges();
      component.onDelete(1);
      expect(spy).toHaveBeenCalledWith(action);

    });
  });

  describe('onClick()', () => {
    it('should navigate to update newsletter page', () => {

      fixture.detectChanges();
      component.onClick(1);
      expect(router.navigate).toHaveBeenCalledWith(['/newsletter/update-newsletter', 1]);
    });
  });

  describe('onPagination()', () => {
    it('should dispatch get newsletters page', () => {

      const action = new NewsletterActions.GetNewsletterPage({page: 1, size: 10});
      const spy = spyOn(store, 'dispatch');
      fixture.detectChanges();
      component.onPagination(1);
      expect(spy).toHaveBeenCalledWith(action);

    });
  });

  describe('showErrorAlert()', () => {
    it('should dispatch ClearAction action', () => {
      const action = new NewsletterActions.ClearError();
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      fixture.detectChanges();
      component.showErrorAlert('error message');
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.snackBar.open).toHaveBeenCalledWith('error message', 'Ok', {duration: 3000});
      expect(component.error).toEqual(null);
    });
  });

  describe('showSuccessAlert()', () => {
    it('should dispatch ClearSuccess action', () => {
      const action = new NewsletterActions.ClearSuccess();
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      fixture.detectChanges();
      component.showSuccessAlert('success message');
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.snackBar.open).toHaveBeenCalledWith('success message', 'Ok', {duration: 3000});
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
