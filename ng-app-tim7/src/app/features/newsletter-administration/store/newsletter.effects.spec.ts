import { provideMockActions } from '@ngrx/effects/testing';
import { TestBed } from '@angular/core/testing';
import {Observable, of} from 'rxjs';
import * as NewsletterActions from './newsletter.actions';
import { NewsletterEffects } from './newsletter.effects';
import {Action} from '@ngrx/store';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {Router} from '@angular/router';
import { NewsletterModel } from 'src/app/models/newsletter.model';

describe('NewsletterEffects', () => {
  let actions$: Observable<Action>;
  let effects: NewsletterEffects;
  let http: HttpTestingController;
  let router: any;
  beforeEach(() => {
    const routerMock = {
      navigate: jasmine.createSpy('navigate')
    };
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        NewsletterEffects,
        provideMockActions(() => actions$),
        { provide: Router, useValue: routerMock }
      ]
    });

    effects = TestBed.inject(NewsletterEffects);
    http = TestBed.inject(HttpTestingController);
    router = TestBed.inject(Router);

    let store = {};
    const mockLocalStorage = {
      getItem: (key: string): string => {
        return key in store ? store[key] : null;
      },
      setItem: (key: string, value: string) => {
        store[key] = `${value}`;
      },
      clear: () => {
        store = {};
      }
    };

    spyOn(localStorage, 'getItem')
      .and.callFake(mockLocalStorage.getItem);
    spyOn(localStorage, 'setItem')
      .and.callFake(mockLocalStorage.setItem);
    spyOn(localStorage, 'clear')
      .and.callFake(mockLocalStorage.clear);
  });

  afterEach(() => {
    http.verify();
  });

  it('should be created', () => {
    expect(effects).toBeTruthy();
  });

  describe('Get newsletter page', () => {
    it('should return GetNewsletterSuccess action, with newsletters data', () => {
      const date = new Date();
      actions$ = of(new NewsletterActions.GetNewsletterPage({page: 1, size: 1}));
      effects.newsletters.subscribe(action => {
        expect(action).toEqual(new NewsletterActions.GetNewslettersSuccess({
            content: [new NewsletterModel(1, 'Title', 'Description', date, 1, 'img.jpg', '')]
        }));
      });

      const req = http.expectOne('http://localhost:8080/newsletter/by-page?page=1&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush({
        content: [new NewsletterModel(1, 'Title', 'Description', date, 1, 'img.jpg', '')]
      });
    });
  });

  describe('Get categories select', () => {
    it('should return GetCategoriesSelectSuccess action, with categories data', () => {
      actions$ = of(new NewsletterActions.GetCategoriesSelect({page: 1, size: 1}));
      effects.categoriesSelect.subscribe(action => {
        expect(action).toEqual(new NewsletterActions.GetCategoriesSelectSuccess({
            content: [{id: 1, name: 'Category1'}]
        }));
      });

      const req = http.expectOne('http://localhost:8080/categories/by-page?page=1&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush({
        content: [{id: 1, name: 'Category1'}]
      });
    });
  });

  describe('Get subcategories select', () => {
    it('should return GetSubcategoriesSelectSuccess action, with subcategories data', () => {
      actions$ = of(new NewsletterActions.GetSubcategoriesSelect({page: 1, size: 1, category: 1}));
      effects.subcategoriesSelect.subscribe(action => {
        expect(action).toEqual(new NewsletterActions.GetSubcategoriesSelectSuccess({
            content: [{id: 1, name: 'Subcategory1'}]
        }));
      });

      const req = http.expectOne('http://localhost:8080/subcategories/1/by-page?page=1&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush({
        content: [{id: 1, name: 'Subcategory1'}]
      });
    });
  });

  describe('Get offers select', () => {
    it('should return GetOffersSelectSuccess action, with cultural offers data', () => {
      actions$ = of(new NewsletterActions.GetOffersSelect({page: 1, size: 1, subcategory: 1}));
      effects.offersSelect.subscribe(action => {
        expect(action).toEqual(new NewsletterActions.GetOffersSelectSuccess({
            content: [{id: 1, name: 'CulturalOffer1'}]
        }));
      });

      const req = http.expectOne('http://localhost:8080/cultural-offers/subcategory/1/by-page?page=1&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush({
        content: [{id: 1, name: 'CulturalOffer1'}]
      });
    });
  });

  describe('Get newsletter', () => {
    it('should return GetNewsletterSuccess action, with newsletter data', () => {
      const date = new Date();
      const newsletter = new NewsletterModel(
          1, 'Title', 'Description', date, 1, 'img.jpg', ''
      );
      actions$ = of(new NewsletterActions.GetNewsletter({id: 1}));
      effects.newsletter.subscribe(action => {
        expect(action).toEqual(new NewsletterActions.GetNewsletterSuccess(newsletter));
      });

      const req = http.expectOne('http://localhost:8080/newsletter/1');
      expect(req.request.method).toEqual('GET');
      req.flush({
        id: 1,
        name: 'Title',
        description: 'Description',
        publishedDate: date,
        culturalOfferId: 1,
        picture: 'img.jpg',
        culturalOffer: 'CulturalOffer1'
      });
    });
  });

  describe('Get categories subscribed', () => {
    it('should return GetCategoriesSubscribedSuccess action, with categories for user', () => {
      actions$ = of(new NewsletterActions.GetCategoriesSubscribed({id: 1}));
      effects.categoriesSubscribed.subscribe(action => {
        expect(action).toEqual(new NewsletterActions.GetCategoriesSubscribedSuccess({
            content: [{id: 1, name: 'Category1'}]
        }));
      });

      const req = http.expectOne('http://localhost:8080/categories/subscribed/1');
      expect(req.request.method).toEqual('GET');
      req.flush({
        content: [{id: 1, name: 'Category1'}]
      });
    });
  });

  describe('Get newsletter subscribed', () => {
    it('should return GetNewslettersSubscribedSuccess action, with newsletters for user', () => {
      actions$ = of(new NewsletterActions.GetNewslettersSubscribed({ page: 1, size: 1, catId: 1, id: 1}));
      effects.newslettersSubscribed.subscribe(action => {
        expect(action).toEqual(new NewsletterActions.GetNewslettersSubscribedSuccess({
            content: [{id: 1, name: 'Newsletter1'}]
        }));
      });

      const req = http.expectOne('http://localhost:8080/newsletter/subscribed/1/1/by-page?page=1&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush({
        content: [{id: 1, name: 'Newsletter1'}]
      });
    });
  });

  describe('Unsubscribe', () => {
    it('should unsubscribe user from cultural offer and return NewsletterSuccess action', () => {
      actions$ = of(new NewsletterActions.Unsubscribe({ idOffer: 1, idUser: 1}));
      effects.unsubscribe.subscribe(action => {
        expect(action).toEqual(new NewsletterActions.NewsletterSuccess(
            'Successfully unsubscribed.'
        ));
      });

      const req = http.expectOne('http://localhost:8080/cultural-offers/unsubscribe');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({ idOffer: 1, idUser: 1});
      req.flush(
        'Successfully unsubscribed!'
      );
    });
  });

  describe('Add newsletter', () => {
    it('should add newsletter and return NewsletterSuccess action', () => {
      const date = new Date();
      actions$ = of(new NewsletterActions.AddNewsletter({
        name: 'Title',
        description: 'Description',
        picture: 'img.jpg',
        culturalOfferId: 1,
        publishedDate: date
      }));
      effects.add.subscribe(action => {
        expect(action).toEqual(new NewsletterActions.NewsletterSuccess(
            'Newsletter added.'
        ));
      });

      const req = http.expectOne('http://localhost:8080/newsletter');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({
        name: 'Title',
        description: 'Description',
        picture: 'img.jpg',
        culturalOfferId: 1,
        publishedDate: date
      });
      req.flush({
        name: 'Title',
        description: 'Description',
        picture: 'img.jpg',
        culturalOfferId: 1,
        publishedDate: new Date()
      });
    });
  });

  describe('Update newsletter', () => {
    it('should update newsletter and return NewsletterSuccess action', () => {
      const date = new Date();
      const newsletter = new NewsletterModel(1, 'Title', 'Description', date, 1, 'img.jpg', '');
      actions$ = of(new NewsletterActions.UpdateNewsletter({newsletter}));
      effects.edit.subscribe(action => {
        expect(action).toEqual(new NewsletterActions.NewsletterSuccess(
            'Newsletter updated.'
        ));
      });

      const req = http.expectOne('http://localhost:8080/newsletter');
      expect(req.request.method).toEqual('PUT');
      expect(req.request.body).toEqual({
        id: 1,
        name: 'Title',
        description: 'Description',
        picture: 'img.jpg',
        culturalOfferId: 1,
        publishedDate: date
      });
      req.flush({
        name: 'Title',
        description: 'Description',
        picture: 'img.jpg',
        culturalOfferId: 1,
        publishedDate: new Date()
      });
    });
  });

  describe('Delete newsletter', () => {
    it('should delete newsletter and return NewsletterSuccess action', () => {
      actions$ = of(new NewsletterActions.DeleteNewsletter(1));
      effects.delete.subscribe(action => {
        expect(action).toEqual(new NewsletterActions.NewsletterSuccess(
            'Newsletter deleted.'
        ));
      });

      const req = http.expectOne('http://localhost:8080/newsletter/1');
      expect(req.request.method).toEqual('DELETE');
      req.flush(
        'Successfully deleted.'
      );
    });
  });

});
