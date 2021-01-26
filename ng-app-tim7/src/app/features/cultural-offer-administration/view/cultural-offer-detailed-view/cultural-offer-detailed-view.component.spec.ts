import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CulturalOfferDetailedViewComponent } from './cultural-offer-detailed-view.component';
import {Store, StoreModule} from '@ngrx/store';
import {CarouselComponent} from '../../../../shared/carousel/carousel.component';
import {FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
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
import {ActivatedRoute, convertToParamMap, Router} from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {MatCarouselModule} from '@ngmodule/material-carousel';
import * as CulturalOfferActions from '../../store/cultural-offer.actions';
import {of} from 'rxjs';

describe('CulturalOfferDetailedViewComponent', () => {
  let component: CulturalOfferDetailedViewComponent;
  let fixture: ComponentFixture<CulturalOfferDetailedViewComponent>;
  let store: Store;
  let router: Router;
  let activatedRoute: ActivatedRoute;
  const routerMock = {
    navigate: jasmine.createSpy('navigate')
  };
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CulturalOfferDetailedViewComponent, CarouselComponent ],
      imports: [FormsModule, ReactiveFormsModule,  StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule, MatDividerModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatPaginatorModule, MatInputModule, MatTableModule,
        MatSelectModule, MatIconModule, MatCarouselModule],
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
    let storeLocalStorage = {};
    const mockLocalStorage = {
      getItem: (key: string): string => {
        return key in storeLocalStorage ? storeLocalStorage[key] : null;
      },
      setItem: (key: string, value: string) => {
        storeLocalStorage[key] = `${value}`;
      },
      clear: () => {
        storeLocalStorage = {};
      }
    };
    spyOn(localStorage, 'getItem')
      .and.callFake(mockLocalStorage.getItem);
    spyOn(localStorage, 'setItem')
      .and.callFake(mockLocalStorage.setItem);
    spyOn(localStorage, 'clear')
      .and.callFake(mockLocalStorage.clear);
    fixture = TestBed.createComponent(CulturalOfferDetailedViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  describe('ngOnInit()', () => {
    it('should subscribe to store in ngOnInit lifecycle', () => {
      const action1 = new CulturalOfferActions.GetComments({page: component.pageComments,
        size: component.pageSizeComments, offerId: Number(activatedRoute.snapshot.queryParamMap.get('offer_id'))});
      const action2 = new CulturalOfferActions.GetNewsletters({page: component.pageNewsletters,
        size: component.pageSizeNewsletters, offerId: Number(activatedRoute.snapshot.queryParamMap.get('offer_id'))});
      const action3 = new CulturalOfferActions.GetAverageRating({offerId: Number(activatedRoute.snapshot.queryParamMap.get('offer_id'))});
      const spy = spyOn(store, 'dispatch');
      localStorage.setItem('user', JSON.stringify({id: 1}));
      component.ngOnInit();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action1);
      expect(spy).toHaveBeenCalledWith(action2);
      expect(spy).toHaveBeenCalledWith(action3);
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
    it('should dispatch ClearSuccess action and AlreadySubscribed action', () => {
      const action = new CulturalOfferActions.AlreadySubscribed(component.culturalOfferDetailed.id);
      const action2 = new CulturalOfferActions.ClearAction();
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      component.showSuccessAlert('Successfully subscribed!');
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(spy).toHaveBeenCalledWith(action2);
      expect(component.snackBar.open).toHaveBeenCalledWith('Successfully subscribed!', 'Ok', {duration: 3000});
      expect(component.success).toEqual(null);
    });
    it('should dispatch ClearSuccess action and AlreadySubscribed action', () => {
      const action = new CulturalOfferActions.AlreadySubscribed(component.culturalOfferDetailed.id);
      const action2 = new CulturalOfferActions.ClearAction();
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      component.showSuccessAlert('Successfully unsubscribed!');
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(spy).toHaveBeenCalledWith(action2);
      expect(component.snackBar.open).toHaveBeenCalledWith('Successfully unsubscribed!', 'Ok', {duration: 3000});
      expect(component.success).toEqual(null);
    });
    it('should dispatch ClearSuccess action and GetAverageRating action', () => {
      const action = new CulturalOfferActions.GetAverageRating({offerId: component.culturalOfferDetailed.id});
      const action2 = new CulturalOfferActions.ClearAction();
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      component.showSuccessAlert('Successfully rated!');
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(spy).toHaveBeenCalledWith(action2);
      expect(component.snackBar.open).toHaveBeenCalledWith('Successfully rated!', 'Ok', {duration: 3000});
      expect(component.success).toEqual(null);
    });
    it('should dispatch ClearSuccess action and GetCommentsAction', () => {
      const action = new CulturalOfferActions.GetComments(
        {page: component.pageComments, size: component.pageSizeComments, offerId: component.culturalOfferDetailed.id});
      const action2 = new CulturalOfferActions.ClearAction();
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      component.showSuccessAlert('default message');
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(spy).toHaveBeenCalledWith(action2);
      expect(component.snackBar.open).toHaveBeenCalledWith('default message', 'Ok', {duration: 3000});
      expect(component.success).toEqual(null);
    });
  });
  describe('rate()', () => {
    it('should dispatch Rate action', () => {
      localStorage.setItem('user', JSON.stringify({id: 1}));
      const action = new CulturalOfferActions.Rate(
        {offerId: component.culturalOfferDetailed.id,
          registeredId: (JSON.parse(localStorage.getItem('user')).id), rate: component.pickedRating});
      const spy = spyOn(store, 'dispatch');
      component.rate();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.disabledRating).toEqual(true);
    });
  });
  describe('createComment()', () => {
    it('should dispatch CreateComment action', () => {
      localStorage.setItem('user', JSON.stringify({id: 1}));
      component.publishedDateNewComment = new Date();
      const action = new CulturalOfferActions.CreateComment(
        {description: component.form.value.commentInput, publishedDate: component.publishedDateNewComment,
        registeredId: (JSON.parse(localStorage.getItem('user'))).id,
          picturesId: component.pickedPhotos, culturalOfferId: component.culturalOfferDetailed.id});
      const spy = spyOn(store, 'dispatch');
      component.addComment();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });
  describe('commentNavigateBefore()', () => {
    it('should dispatch GetComments action', () => {
      const action = new CulturalOfferActions.GetComments(
        {page: component.pageComments - 1, size: component.pageSizeComments, offerId: component.culturalOfferDetailed.id});
      const spy = spyOn(store, 'dispatch');
      component.commentNavigateBefore();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });
  describe('commentNavigateNext()', () => {
    it('should dispatch GetComments action', () => {
      const action = new CulturalOfferActions.GetComments(
        {page: component.pageComments + 1, size: component.pageSizeComments, offerId: component.culturalOfferDetailed.id});
      const spy = spyOn(store, 'dispatch');
      component.commentNavigateNext();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });
  describe('newsletterNavigateBefore()', () => {
    it('should dispatch GetNewsletters action', () => {
      const action = new CulturalOfferActions.GetNewsletters(
        {page: component.pageNewsletters - 1, size: component.pageSizeNewsletters, offerId: component.culturalOfferDetailed.id});
      const spy = spyOn(store, 'dispatch');
      component.newslettersNavigateBefore();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });
  describe('newsletterNavigateNext()', () => {
    it('should dispatch GetNewsletters action', () => {
      const action = new CulturalOfferActions.GetNewsletters(
        {page: component.pageNewsletters + 1, size: component.pageSizeNewsletters, offerId: component.culturalOfferDetailed.id});
      const spy = spyOn(store, 'dispatch');
      component.newslettersNavigateAfter();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });
  describe('subscribe()', () => {
    it('should dispatch Subscribe action', () => {
      localStorage.setItem('user', JSON.stringify({id: 1}));
      const action = new CulturalOfferActions.Subscribe(
        {offerId: component.culturalOfferDetailed.id, userId: (JSON.parse(localStorage.getItem('user'))).id});
      const spy = spyOn(store, 'dispatch');
      component.subscribe();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });
  describe('unsubscribe()', () => {
    it('should dispatch Subscribe action', () => {
      localStorage.setItem('user', JSON.stringify({id: 1}));
      const action = new CulturalOfferActions.Unsubscribe(
        {offerId: component.culturalOfferDetailed.id, userId: (JSON.parse(localStorage.getItem('user'))).id});
      const spy = spyOn(store, 'dispatch');
      component.hideSubscribe = true;
      component.subscribe();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });
  describe('onPictureChange()', () => {
    it('should change the index of the picture', () => {
      component.onPictureChanged(2);
      expect(component.picture).toEqual(2);
    });
  });
  describe('onPictureRemove()', () => {
    it('should remove the picture from the list', () => {
      component.pickedPhotos = ['picture1', 'picture2', 'picture3'];
      component.picture = 1;
      component.onPictureRemove();
      expect(component.pickedPhotos.length).toEqual(2);
    });
  });
  describe('handleReader', () => {
    it('should push the picture to the list', () => {
      component._handleReaderLoaded({target: {result: 'new_picture'}});
      expect(component.pickedPhotos.length).toEqual(1);
    });
  });
  describe('goToPublish', () => {
    it('should navigate to add newsletter', () => {
      component.culturalOfferDetailed.name = 'CulturalOffer1';
      component.goToPublish();
      expect(router.navigate).toHaveBeenCalledWith(['/newsletter/add-newsletter',
        {culturalOfferId: 1, culturalOfferName: 'CulturalOffer1'}]);
});
  });


});
