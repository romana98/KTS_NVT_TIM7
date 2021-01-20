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
import { ActivatedRoute, convertToParamMap, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { MatSelectModule, SELECT_PANEL_PADDING_X } from '@angular/material/select';
import { MatProgressBar, MatProgressBarModule } from '@angular/material/progress-bar';
import { NewsletterModel } from 'src/app/models/newsletter.model';
import { CategoryNewsletterComponent } from './category-newsletter.component';
import { PaginationComponent } from '../../common/pagination/pagination.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { CategoryModel } from 'src/app/models/category.model';

describe('CategoryNewsletterComponent', () => {
  let component: CategoryNewsletterComponent;
  let fixture: ComponentFixture<CategoryNewsletterComponent>;
  let store: Store;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CategoryNewsletterComponent, PaginationComponent ],
      imports: [FormsModule, ReactiveFormsModule,  StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule, MatDividerModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatProgressBarModule,
        MatPaginatorModule],
      providers: [Store]
    })
    .compileComponents();
    store = TestBed.inject(Store);
  }));

  beforeEach(() => {

    let localStore = {};
    const mockLocalStorage = {
      getItem: (key: string): string => {
        return key in localStore ? localStore[key] : null;
      },
      setItem: (key: string, value: string) => {
        localStore[key] = `${value}`;
      },
      clear: () => {
        localStore = {};
      }
    };

    spyOn(localStorage, 'getItem')
      .and.callFake(mockLocalStorage.getItem);
    spyOn(localStorage, 'setItem')
      .and.callFake(mockLocalStorage.setItem);
    spyOn(localStorage, 'clear')
      .and.callFake(mockLocalStorage.clear);

    localStorage.setItem('user', JSON.stringify({id: 1}));

    fixture = TestBed.createComponent(CategoryNewsletterComponent);
    component = fixture.componentInstance;
    component.category = new CategoryModel(1, 'Category1');
    fixture.detectChanges();

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should subscribe to store and get newsletters for subscribed user in ngOnInit lifecycle', () => {

      const action = new NewsletterActions.GetNewslettersSubscribed({page: 0, size: 5, catId: 1, id: 1});
      const spy = spyOn(store, 'dispatch');
      fixture.detectChanges();
      component.ngOnInit();
      expect(spy).toHaveBeenCalledWith(action);

    });
  });

  describe('onPagination()', () => {
    it('should dispatchget next page of newsletters for subscribed user', () => {

      const action = new NewsletterActions.GetNewslettersSubscribed({page: 1, size: 5, catId: 1, id: 1});
      const spy = spyOn(store, 'dispatch');
      fixture.detectChanges();
      component.onPagination(1);
      expect(spy).toHaveBeenCalledWith(action);

    });
  });

  describe('unsubscribe()', () => {
    it('should dispatch unsubscribe from cultural offer and get newsletter', () => {

      const action = new NewsletterActions.Unsubscribe({idOffer: 1, idUser: 1});
      const actionGet = new NewsletterActions.GetNewslettersSubscribed({page: 0, size: 5, catId: 1, id: 1});
      const spy = spyOn(store, 'dispatch');
      fixture.detectChanges();
      component.unsubscribe(1);
      expect(spy).toHaveBeenCalledWith(action);
      expect(spy).toHaveBeenCalledWith(actionGet);

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
