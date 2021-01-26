import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CulturalOfferDashboardComponent } from './cultural-offer-dashboard.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Store, StoreModule} from '@ngrx/store';
import * as fromApp from '../../../../store/app.reducer';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDividerModule} from '@angular/material/divider';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatInputModule} from '@angular/material/input';
import {MatTableModule} from '@angular/material/table';
import {MatSelectModule} from '@angular/material/select';
import {MatIconModule} from '@angular/material/icon';
import {MatCarouselModule} from '@ngmodule/material-carousel';
import {ActivatedRoute, convertToParamMap, Router} from '@angular/router';
import * as CulturalOfferActions from '../../store/cultural-offer.actions';
import {of} from 'rxjs';
import {SharedModule} from '../../../../shared/shared.module';

describe('CulturalOfferDashboardComponent', () => {
  let component: CulturalOfferDashboardComponent;
  let fixture: ComponentFixture<CulturalOfferDashboardComponent>;
  let store: Store;
  let router: Router;
  let activatedRoute: ActivatedRoute;
  const routerMock = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CulturalOfferDashboardComponent ] ,
      imports: [FormsModule, ReactiveFormsModule,  StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule, MatDividerModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatPaginatorModule, MatInputModule, MatTableModule,
        MatSelectModule, MatIconModule, MatCarouselModule, SharedModule],
      providers: [Store,
        {provide: Router, useValue: routerMock},
        {provide: ActivatedRoute, useValue: { snapshot: { queryParamMap: convertToParamMap({offer_id: 1})}}}]
    })
      .compileComponents();
    store = TestBed.inject(Store);
    router = TestBed.inject(Router);
    activatedRoute = TestBed.inject(ActivatedRoute);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should subscribe to store and get cultural offers in ngOnInit lifecycle', () => {
      const action = new CulturalOfferActions.GetCulturalOfferPage({ page: 0, size: 10 });
      const spy = spyOn(store, 'dispatch');
      fixture.detectChanges();
      component.ngOnInit();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.error).toBe(null);

    });
  });

  describe('onDelete()', () => {
    it('should dispatch delete cultural offer', () => {
      const action = new CulturalOfferActions.DeleteCulturalOffer({id: 1});
      const spy = spyOn(store, 'dispatch');
      fixture.detectChanges();
      component.onDelete(1);
      expect(spy).toHaveBeenCalledWith(action);

    });
  });

  describe('onClick()', () => {
    it('should navigate to update cultural offer page', () => {
      fixture.detectChanges();
      component.onClick(1);
      expect(router.navigate).toHaveBeenCalledWith(['administrator/editCulturalOffer', {id: 1}]);
    });
  });

  describe('onPagination()', () => {
    it('should dispatch get cultural offers page', () => {

      const action = new CulturalOfferActions.GetCulturalOfferPage({page: 1, size: 10});
      const spy = spyOn(store, 'dispatch');
      fixture.detectChanges();
      component.onPagination(1);
      expect(spy).toHaveBeenCalledWith(action);

    });
  });

  describe('showErrorAlert()', () => {
    it('should dispatch ClearAction action', () => {
      const action = new CulturalOfferActions.ClearAction();
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
      spyOn(component.snackBar, 'open');

      fixture.detectChanges();
      component.showSuccessAlert('success message');
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
