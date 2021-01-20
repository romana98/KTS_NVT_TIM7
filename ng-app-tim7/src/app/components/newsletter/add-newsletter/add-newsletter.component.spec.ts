import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Store, StoreModule} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDividerModule} from '@angular/material/divider';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BrowserModule, By} from '@angular/platform-browser';
import {of} from 'rxjs';
import * as NewsletterActions from '../store/newsletter.actions';

import { AddNewsletterComponent } from './add-newsletter.component';
import { ActivatedRoute, convertToParamMap, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { MatSelectModule, SELECT_PANEL_PADDING_X } from '@angular/material/select';
import { MatProgressBar, MatProgressBarModule } from '@angular/material/progress-bar';
import { NewsletterModel } from 'src/app/models/newsletter.model';

describe('AddNewsletterComponent', () => {
  let component: AddNewsletterComponent;
  let fixture: ComponentFixture<AddNewsletterComponent>;
  let store: Store;
  let route: ActivatedRoute;
  let router: Router;

  const mockRouter = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddNewsletterComponent ],
      imports: [FormsModule, ReactiveFormsModule,  StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule, MatDividerModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatProgressBarModule ],
      providers: [Store, {provide: ActivatedRoute,
        useValue: { snapshot: { paramMap: convertToParamMap( { culturalOfferId: 1,  culturalOfferName: 'CulturalOffer1' } ) } }},
        {provide: Router, useValue: mockRouter}]
    })
    .compileComponents();
    store = TestBed.inject(Store);
    router = TestBed.inject(Router);
    route = TestBed.inject(ActivatedRoute);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddNewsletterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should subscribe to store and get categories for select in ngOnInit lifecycle', () => {

      const action = new NewsletterActions.GetCategoriesSelect({ page: 0, size: 15 });
      const spy = spyOn(store, 'dispatch');
      fixture.detectChanges();
      component.ngOnInit();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.error).toBe(null);

    });
  });

  describe('submit()', () => {
    it('should dispatch AddNewsletter action and navigate to newsletter dashboard', () => {
      const action = new NewsletterActions.AddNewsletter(new NewsletterModel(0, 'Title', 'Description',
        new Date('2020-12-12'), 1, 'img.jpg', ''));
      const spy = spyOn(store, 'dispatch');
      component.form.controls.name.setValue('Title');
      component.form.controls.description.setValue('Description');
      component.form.controls.picture.setValue('img.jpg');
      component.culturalOfferId = 1;
      component.publishedDate = new Date('2020-12-12');
      fixture.detectChanges();
      component.submit();
      expect(spy).toHaveBeenCalledWith(action);
      expect(router.navigate).toHaveBeenCalledWith(['/newsletter/dashboard']);
    });
  });

  describe('getNextBatchCategories()', () => {
    it('should dispatch GetCategoriesSelect', () => {

      const action = new NewsletterActions.GetCategoriesSelect({ page: 1, size: 15 });
      const spy = spyOn(store, 'dispatch');
      component.pageCategory = 0;
      component.pageSize = 15;
      fixture.detectChanges();
      component.getNextBatchCategories();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('getNextBatchSubcategories()', () => {
    it('should dispatch GetSubcategoriesSelect', () => {

      const action = new NewsletterActions.GetSubcategoriesSelect({ page: 1, size: 15, category: 1 });
      const spy = spyOn(store, 'dispatch');
      component.pageSubcategory = 0;
      component.pageSize = 15;
      component.categoryId = 1;
      fixture.detectChanges();
      component.getNextBatchSubcategories();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('getNextBatchOffers()', () => {
    it('should dispatch GetSubcategoriesSelect', () => {

      const action = new NewsletterActions.GetOffersSelect({ page: 1, size: 15, subcategory: 1 });
      const spy = spyOn(store, 'dispatch');
      component.pageOffer = 0;
      component.pageSize = 15;
      component.subcategoryId = 1;
      fixture.detectChanges();
      component.getNextBatchOffers();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('onChangeCategories()', () => {
    it('should dispatch GetSubcategoriesSelect', () => {

      const action = new NewsletterActions.GetSubcategoriesSelect({ page: 0, size: 15, category: 1 });
      const spy = spyOn(store, 'dispatch');
      component.pageSize = 15;
      fixture.detectChanges();
      component.onChangeCategories({ value: { id: 1 } });
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('onChangeSubcategories()', () => {
    it('should dispatch GetSubcategoriesSelect', () => {

      const action = new NewsletterActions.GetOffersSelect({ page: 0, size: 15, subcategory: 1 });
      const spy = spyOn(store, 'dispatch');
      component.pageSize = 15;
      fixture.detectChanges();
      component.onChangeSubcategories({ value: { id: 1 }});
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('onChangeOffers()', () => {
    it('should set cultural offer id', () => {
      fixture.detectChanges();
      component.onChangeOffers({ value: { id: 1 }});
      expect(component.culturalOfferId).toEqual(1);
    });
  });

  describe('handleReaderLoaded()', () => {
    it('should set picture', () => {
      fixture.detectChanges();
      component.handleReaderLoaded({target: {result: 'new_picture'}});
      expect(component.picture).toBeTruthy();
    });
  });

  describe('onPictureRemove()', () => {
    it('should remove picture', () => {
      fixture.detectChanges();
      component.onPictureRemove(new Event('input'));
      expect(component.picture).toEqual('');
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
      expect(component.snackBar.open).toHaveBeenCalledWith('error message', 'Ok', {duration: 2000});
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
