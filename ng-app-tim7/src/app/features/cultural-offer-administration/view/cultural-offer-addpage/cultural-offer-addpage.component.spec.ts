import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CulturalOfferAddpageComponent } from './cultural-offer-addpage.component';
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
import {SharedModule} from '../../../../shared/shared.module';
import {ActivatedRoute, convertToParamMap, Router} from '@angular/router';
import {of} from 'rxjs';
import * as CulturalOfferActions from '../../store/cultural-offer.actions';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';

describe('CulturalOfferAddpageComponent', () => {
  let component: CulturalOfferAddpageComponent;
  let fixture: ComponentFixture<CulturalOfferAddpageComponent>;
  let store: Store;
  let router: Router;
  let activatedRoute: ActivatedRoute;
  const routerMock = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CulturalOfferAddpageComponent ],
      imports: [FormsModule, ReactiveFormsModule,  StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule, MatDividerModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatPaginatorModule, MatInputModule, MatTableModule,
        MatSelectModule, MatIconModule, MatCarouselModule, SharedModule, MatDatepickerModule, MatNativeDateModule ],
      providers: [Store,
        {provide: Router, useValue: routerMock},
        {provide: ActivatedRoute, useValue: { snapshot: { paramMap: convertToParamMap({id: 0})}}}]
    })
      .compileComponents();
    store = TestBed.inject(Store);
    router = TestBed.inject(Router);
    activatedRoute = TestBed.inject(ActivatedRoute);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferAddpageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should subscribe to store and get categories to show in ngOnInit lifecycle', () => {
      const action = new CulturalOfferActions.GetCategories({ page: 0, page_size: 10 });
      const spy = spyOn(store, 'dispatch');
      fixture.detectChanges();
      component.ngOnInit();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.error).toBe(null);

    });
  });

  describe('showMessage(message: string)', () => {
    it('should dispatch ClearAction action', () => {
      const action = new CulturalOfferActions.ClearAction();
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      fixture.detectChanges();
      component.showMessage('error message');
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.snackBar.open).toHaveBeenCalledWith('error message', 'Ok', {duration: 3000});
      expect(component.error).toEqual(null);
    });
  });

  describe('onValueChanged(key: string, value: any)', () => {
    it('should dispatch CategoryChangedAction action', () => {
      const action = new CulturalOfferActions.CategoryChangedAction({categoryId: undefined, page: 0 , page_size: 10});
      const spy = spyOn(store, 'dispatch');

      fixture.detectChanges();
      component.onValueChanged('category', 'festival');
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('getNextBatchCategory()', () => {
    it('should dispatch GetCategories action which get 10 more categories', () => {
      const action = new CulturalOfferActions.GetCategories({page: 0, page_size: 20});
      const spy = spyOn(store, 'dispatch');

      fixture.detectChanges();
      component.getNextBatchCategory();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('getNextBatchSubcategory()', () => {
    it('should dispatch GetSubcategories action which get 10 more subcategories', () => {
      component.culturalOffer.category = 5;
      const action = new CulturalOfferActions.CategoryChangedAction(
        {categoryId: 5, page: 0 , page_size: 20});
      const spy = spyOn(store, 'dispatch');

      fixture.detectChanges();
      component.getNextBatchSubcategory();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('onSubmitClicked()', () => {
    it('should dispatch Add offer action to add offer', () => {
      const action = new CulturalOfferActions.AddOfferAction(
        { id: -1, name: '', description: '', startDate: '',
          endDate: '', subcategory: -1, location: 1, subcategoryName: '',
          categoryName: '', category: -1, latitude: 45.25167, longitude: 19.83694,
          pictures: [  ], locationName: '' });
      const clearAction = new CulturalOfferActions.ClearSelectedOfferAction('');
      const spy = spyOn(store, 'dispatch');

      fixture.detectChanges();
      component.onSubmitClicked();
      expect(spy).toHaveBeenCalledWith(clearAction);
      expect(spy).toHaveBeenCalledWith(action);

    });
  });

  describe('onLocationChanged()', () => {
    it('on location on map changed', () => {
      component.onLocationChanged({latitude: 1, longitude: 1, locationName: 'naziv'});
      fixture.detectChanges();
      expect(component.culturalOffer.latitude).toEqual(1);
      expect(component.culturalOffer.longitude).toEqual(1);
      expect(component.culturalOffer.locationName).toEqual( 'naziv');
    });
  });


  describe('onPictureChanged()', () => {
    it('should change the index of the picture', () => {
      component.onPictureChanged(2);
      expect(component.currentPicture).toEqual(2);
    });
  });

  describe('onPictureRemove()', () => {
    it('should remove the picture from the list', () => {
      component.culturalOffer.pictures = ['picture1', 'picture2', 'picture3'];
      component.currentPicture = 1;
      component.onPictureRemove();
      expect(component.culturalOffer.pictures.length).toEqual(2);
    });
  });

  describe('handleReader', () => {
    it('should push the picture to the list', () => {
      component._handleReaderLoaded({target: {result: 'new_picture'}});
      expect(component.culturalOffer.pictures.length).toEqual(1);
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

describe('CulturalOfferEditPage()', () => {
  let component: CulturalOfferAddpageComponent;
  let fixture: ComponentFixture<CulturalOfferAddpageComponent>;
  let store: Store;
  let router: Router;
  let activatedRoute: ActivatedRoute;
  const routerMock = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CulturalOfferAddpageComponent ],
      imports: [FormsModule, ReactiveFormsModule,  StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule, MatDividerModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatPaginatorModule, MatInputModule, MatTableModule,
        MatSelectModule, MatIconModule, MatCarouselModule, SharedModule, MatDatepickerModule, MatNativeDateModule ],
      providers: [Store,
        {provide: Router, useValue: routerMock},
        {provide: ActivatedRoute, useValue: { snapshot: { paramMap: convertToParamMap({id: 1})}}}]
    })
      .compileComponents();
    store = TestBed.inject(Store);
    router = TestBed.inject(Router);
    activatedRoute = TestBed.inject(ActivatedRoute);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferAddpageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  describe('ngOnInit()', () => {
    it('should subscribe to store and get cultural offers in ngOnInit lifecycle', () => {
      const action = new CulturalOfferActions.GetOneOfferAction(1);
      const spy = spyOn(store, 'dispatch');
      fixture.detectChanges();
      component.ngOnInit();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.error).toBe(null);
    });
  });


  describe('onSubmitClicked()', () => {
    it('should dispatch update offer action to update offer', () => {
      const action = new CulturalOfferActions.UpdateOfferAction(
        { id: -1, name: '', description: '', startDate: '',
          endDate: '', subcategory: -1, location: 1, subcategoryName: '',
          categoryName: '', category: -1, latitude: 45.25167, longitude: 19.83694,
          pictures: [  ], locationName: '' });
      const clearAction = new CulturalOfferActions.ClearSelectedOfferAction('');
      const spy = spyOn(store, 'dispatch');

      fixture.detectChanges();
      component.onSubmitClicked();
      expect(spy).toHaveBeenCalledWith(clearAction);
      expect(spy).toHaveBeenCalledWith(action);

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

