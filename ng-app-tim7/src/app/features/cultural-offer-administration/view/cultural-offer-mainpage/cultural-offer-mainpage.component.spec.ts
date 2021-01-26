import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CulturalOfferMainpageComponent } from './cultural-offer-mainpage.component';
import * as CategoryActions from '../../../category-administration/store/category.actions';
import {Store, StoreModule} from '@ngrx/store';
import {Router} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import * as fromApp from '../../../../store/app.reducer';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDividerModule} from '@angular/material/divider';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import * as CulturalOfferActions from '../../store/cultural-offer.actions';
import {SharedModule} from '../../../../shared/shared.module';
import {of} from 'rxjs';
import {CulturalofferModel} from '../../../../models/culturaloffer.model';

describe('CulturalOfferMainpageComponent', () => {
  let component: CulturalOfferMainpageComponent;
  let fixture: ComponentFixture<CulturalOfferMainpageComponent>;
  let store: Store;
  let router: Router;
  const mockRouter = {
    navigate: jasmine.createSpy('navigate')
  };


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CulturalOfferMainpageComponent],
      imports: [FormsModule, ReactiveFormsModule, StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule, MatDividerModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatProgressBarModule,
        SharedModule],
      providers: [Store, {provide: Router, useValue: mockRouter}]
    })
      .compileComponents();
    store = TestBed.inject(Store);
    router = TestBed.inject(Router);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferMainpageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should subscribe to store in ngOnInit lifecycle', () => {
      const action = new CulturalOfferActions.GetCulturalOfferPage({ page: 0, size: 10 });
      const spy = spyOn(store, 'dispatch');
      component.ngOnInit();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.error).toBe(null);
    });
  });

  describe('ngOnDestroy()', () => {
    it('should unsubscribe to store in ngOnDestroy lifecycle', () => {

      component.storeSub = of(true).subscribe();

      component.ngOnDestroy();

      expect(component.storeSub.closed).toBeTruthy();
    });
  });

  describe('showErrorAlert()', () => {
    it('should dispatch ClearError action', () => {
      const action = new CulturalOfferActions.ClearAction();
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      component.showErrorAlert('error message');
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.snackBar.open).toHaveBeenCalledWith('error message', 'Ok', {duration: 3000});
      expect(component.error).toEqual(null);
    });
  });

  describe('showSuccessAlert()', () => {
    it('should dispatch ClearSuccess action', () => {
      const action = new CulturalOfferActions.ClearAction();
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      component.showSuccessAlert('success message');
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.snackBar.open).toHaveBeenCalledWith('success message', 'Ok', {duration: 3000});
      expect(component.success).toEqual(null);
    });
  });

  describe('onPagination(page: number)', () => {
    it('should dispatch GetCulturalOfferPage action', () => {
      const action = new CulturalOfferActions.GetCulturalOfferPage({page: 1, size: 10});
      const spy = spyOn(store, 'dispatch');

      component.onPagination(1);
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.page).toEqual(1);
    });
  });

  describe('onDelete(id: number)', () => {
    it('should dispatch DeleteCategory action', () => {
      const action = new CulturalOfferActions.DeleteCulturalOffer({id: 1});
      const spy = spyOn(store, 'dispatch');

      component.onDelete(1);
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('onFilterChanged(newValue: any)', () => {
    it('should dispatch FilterCulturalOffersAction action', () => {
      const action = new CulturalOfferActions.FilterCulturalOffersAction(
        {page: 0, page_size: 10, parameter: 'all', value: 'exit'});
      const spy = spyOn(store, 'dispatch');

      component.onFilterChanged({value: 'exit'});
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('onRowClick(offerId: number)', () => {
    it('should change offerClicked which toggle map focus', () => {
      component.ngOnInit();
      fixture.detectChanges();
      const spy = spyOn(component, 'onRowClick');
      component.onRowClick(1);
      fixture.detectChanges();
      expect(spy).toHaveBeenCalled();
    });
  });

  describe('goToDetailed(id: number)', () => {
    it('should navigate to detailed cultural offer page', () => {
      fixture.detectChanges();
      component.goToDetailed(1);
      expect(router.navigate).toHaveBeenCalledWith(['/detailed-cultural-offer'], {queryParams: {offer_id: 1}});
    });
  });
});
