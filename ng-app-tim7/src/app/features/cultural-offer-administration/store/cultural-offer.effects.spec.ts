import {Observable, of} from 'rxjs';
import {Action} from '@ngrx/store';
import {CulturalOfferEffects} from './cultural-offer.effects';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {provideMockActions} from '@ngrx/effects/testing';
import * as CulturalOfferActions from './cultural-offer.actions';

describe('Cultural Offer Effects', () => {
  let actions$: Observable<Action>;
  let effects: CulturalOfferEffects;
  let http: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        CulturalOfferEffects,
        provideMockActions(() => actions$)
      ]
    });

    effects = TestBed.inject(CulturalOfferEffects);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    http.verify();
  });
  it('should be created', () => {
      expect(effects).toBeTruthy();
  });
  describe('Get Comments', () => {
    it('[SUCCESS] should return a GetCommentsSuccess with an array of comments as payload', () => {
      actions$ = of(new CulturalOfferActions.GetComments({page: 0, size: 1, offerId: 1}));
      effects.comments.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.GetCommentsSuccess({content:
            [{description: 'description', registeredUser: 'registered',
                picturesId: ['picture'], publishedDate: new Date(2000, 12, 12, 5, 5, 5, 1)}]}));
      });
      const req = http.expectOne('http://localhost:8080/comments/1/by-page?page=0&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush({content:
          [{description: 'description', registeredUser: 'registered',
            picturesId: ['picture'], publishedDate: new Date(2000, 12, 12, 5, 5, 5, 1)}]});
    });
    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.GetComments({page: 0, size: 1, offerId: 1}));
      effects.comments.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Something went wrong while fetching comments!'));
      });
      const req = http.expectOne('http://localhost:8080/comments/1/by-page?page=0&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush('Something went wrong while fetching comments!', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Get Newsletters', () => {
    it('[SUCCESS] should return a GetNewslettersSuccess with an array of newsletters as payload', () => {
      actions$ = of(new CulturalOfferActions.GetNewsletters({page: 0, size: 1, offerId: 1}));
      effects.newsletters.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.GetNewslettersSuccess({content:
            [{description: 'description', name: 'name', picture: 'picture'}]}));
      });
      const req = http.expectOne('http://localhost:8080/newsletter/cultural-offer/1/by-page?page=0&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush({content:
          [{description: 'description', name: 'name', picture: 'picture'}]});
    });
    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.GetNewsletters({page: 0, size: 1, offerId: 1}));
      effects.newsletters.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Something went wrong while fetching newsletters!'));
      });
      const req = http.expectOne('http://localhost:8080/newsletter/cultural-offer/1/by-page?page=0&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush('Something went wrong while fetching comments!', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Get Average Rating', () => {
    it('[SUCCESS] should return a GetAverageRatingSuccess with a rating as payload', () => {
      actions$ = of(new CulturalOfferActions.GetAverageRating({offerId: 1}));
      effects.averageRating.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.GetAverageRatingSuccess({rate: 2.2}));
      });
      const req = http.expectOne('http://localhost:8080/ratings/getRating/average/1');
      expect(req.request.method).toEqual('GET');
      req.flush({rate: 2.2});
    });
    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.GetAverageRating({offerId: 1}));
      effects.averageRating.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Something went wrong while fetching average rating!'));
      });
      const req = http.expectOne('http://localhost:8080/ratings/getRating/average/1');
      expect(req.request.method).toEqual('GET');
      req.flush('Something went wrong while fetching average rating!', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Rate', () => {
    it('[SUCCESS] should return a Success action with a success message as payload', () => {
      actions$ = of(new CulturalOfferActions.Rate({offerId: 1, rate: 3.0, registeredId: 1}));
      effects.rate.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.SuccessAction('Successfully rated!'));
      });
      const req = http.expectOne('http://localhost:8080/ratings/');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({
        registeredId : 1,
        rate: 3.0,
        culturalOfferId: 1
      });
      req.flush('Successfully rated!');
    });
    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.Rate({offerId: 1, rate: 3.0, registeredId: 1}));
      effects.rate.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Failed to rate!'));
      });
      const req = http.expectOne('http://localhost:8080/ratings/');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({
        registeredId : 1,
        rate: 3.0,
        culturalOfferId: 1
      });
      req.flush('Failed to rate!', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Create comment', () => {
    it('[SUCCESS] should return a Success action with a success message as payload', () => {
      actions$ = of(new CulturalOfferActions.CreateComment({description: 'description', publishedDate: new Date(2000, 12, 12, 5, 5, 5, 1),
      registeredId: 1, picturesId: ['picture'], culturalOfferId: 1}));
      effects.comment.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.SuccessAction('Successfully commented!'));
      });
      const req = http.expectOne('http://localhost:8080/comments/');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({
        description: 'description',
        publishedDate: new Date(2000, 12, 12, 5, 5, 5, 1),
        registeredId: 1,
        picturesId: ['picture'],
        culturalOfferId: 1
      });
      req.flush('Successfully commented!');
    });
    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.CreateComment({description: 'description', publishedDate: new Date(2000, 12, 12, 5, 5, 5, 1),
        registeredId: 1, picturesId: ['picture'], culturalOfferId: 1}));
      effects.comment.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Failed to comment!'));
      });
      const req = http.expectOne('http://localhost:8080/comments/');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({
        description: 'description',
        publishedDate: new Date(2000, 12, 12, 5, 5, 5, 1),
        registeredId: 1,
        picturesId: ['picture'],
        culturalOfferId: 1
      });
      req.flush('Failed to comment!', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Already rated', () => {
    it('[SUCCESS] should return a AlreadyRatedSuccess with a rate as payload', () => {
      actions$ = of(new CulturalOfferActions.AlreadyRated(1));
      effects.alreadyRated.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.AlreadyRatedSuccess({rate: 2.2}));
      });
      const req = http.expectOne('http://localhost:8080/ratings/getRating/1');
      expect(req.request.method).toEqual('GET');
      req.flush({rate: 2.2});
    });
    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.AlreadyRated(1));
      effects.alreadyRated.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Something went wrong!'));
      });
      const req = http.expectOne('http://localhost:8080/ratings/getRating/1');
      expect(req.request.method).toEqual('GET');
      req.flush('Something went wrong!', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Subscribe', () => {
    it('[SUCCESS] should return a Success action with a success message as payload', () => {
      actions$ = of(new CulturalOfferActions.Subscribe({offerId: 1, userId: 1}));
      effects.subscribe.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.SuccessAction('Successfully subscribed!'));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers/subscribe');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({
        idUser : 1,
        idOffer: 1
      });
      req.flush('Successfully subscribed!');
    });
    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.Subscribe({offerId: 1, userId: 1}));
      effects.subscribe.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Failed to subscribe!'));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers/subscribe');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({
        idUser : 1,
        idOffer: 1
      });
      req.flush('Failed to subscribe!', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Unsubscribe', () => {
    it('[SUCCESS] should return a Success action with a success message as payload', () => {
      actions$ = of(new CulturalOfferActions.Unsubscribe({offerId: 1, userId: 1}));
      effects.unsubscribe.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.SuccessAction('Successfully unsubscribed!'));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers/unsubscribe');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({
        idUser : 1,
        idOffer: 1
      });
      req.flush('Successfully unsubscribed!');
    });
    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.Unsubscribe({offerId: 1, userId: 1}));
      effects.unsubscribe.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Failed to unsubscribe!'));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers/unsubscribe');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({
        idUser : 1,
        idOffer: 1
      });
      req.flush('Failed to unsubscribe!', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Already subscribed', () => {
    it('[SUCCESS] should return a AlreadySubscribedValue with a boolean as payload', () => {
      actions$ = of(new CulturalOfferActions.AlreadySubscribed(1));
      effects.alreadySubscribed.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.AlreadySubscribedValue(true));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers/alreadySubscribed/1');
      expect(req.request.method).toEqual('GET');
      // req.flush(true);
    });
    it('[ERROR] should return a AlreadySubscribedValue with a boolean as payload', () => {
      actions$ = of(new CulturalOfferActions.AlreadySubscribed(1));
      effects.alreadySubscribed.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.AlreadySubscribedValue(false));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers/alreadySubscribed/1');
      expect(req.request.method).toEqual('GET');
      // req.flush(false, { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Get cultural offers', () => {
    const culturalOffer = {
      category: 2,
      categoryName: 'Manifestacija',
      description: 'Proslava 20. godišnjice EXIT festivala biće održana u njegovom ' +
        'poznatom punom formatu od 8. do 11. jula 2021. na Petrovaradinskoj tvrđavi.',
      endDate: 1594499671000,
      id: 1,
      latitude: 45.25167,
      location: 1,
      locationName: 'Novi Sad',
      longitude: 19.83694,
      name: 'Exit',
      pictures: [],
      startDate: 1594159200000,
      subcategory: 7,
      subcategoryName: 'Festival'
    };

    it('[SUCCESS] should return a GetCulturalOfferPageSuccess with an array of cultural offers as payload', () => {
      actions$ = of(new CulturalOfferActions.GetCulturalOfferPage({page: 0, size: 1}));
      effects.getCulturalOffers.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.GetCulturalOfferPageSuccess({content:
            [culturalOffer]}));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers/by-page?page=0&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush({content:
          [culturalOffer]});
    });

    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.GetCulturalOfferPage({page: 0, size: 1}));
      effects.getCulturalOffers.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Something went wrong while fetching cultural offers!'));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers/by-page?page=0&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush( 'MockBody', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Delete cultural offer', () => {
    it('[SUCCESS] should return SuccessAction success message as payload', () => {
      actions$ = of(new CulturalOfferActions.DeleteCulturalOffer({id: 1}));
      effects.deleteOffer.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.SuccessAction('Cultural offer successfully deleted.'));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers/1');
      expect(req.request.method).toEqual('DELETE');
      req.flush('Cultural offer successfully deleted.', {status: 200, statusText: 'Ok' });
    });

    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.DeleteCulturalOffer({id: 1}));
      effects.deleteOffer.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Cultural offer cant be removed!'));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers/1');
      expect(req.request.method).toEqual('DELETE');
      req.flush( 'MockBody', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Filter cultural offers', () => {
    const culturalOffer = {
      category: 2,
      categoryName: 'Manifestacija',
      description: 'Proslava 20. godišnjice EXIT festivala biće održana u njegovom ' +
        'poznatom punom formatu od 8. do 11. jula 2021. na Petrovaradinskoj tvrđavi.',
      endDate: 1594499671000,
      id: 1,
      latitude: 45.25167,
      location: 1,
      locationName: 'Novi Sad',
      longitude: 19.83694,
      name: 'Exit',
      pictures: [],
      startDate: 1594159200000,
      subcategory: 7,
      subcategoryName: 'Festival'
    };

    it('[SUCCESS] should return array of filtered cultural offers as payload', () => {
      actions$ = of(new CulturalOfferActions.FilterCulturalOffersAction({page: 0, page_size: 1, parameter: '', value: 'Exit'}));
      effects.filteredOffers.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.FilterSuccessAction({content: [
            culturalOffer
            ]}));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers/filter?page=0&size=1');
      expect(req.request.body).toEqual({
        parameter : '',
        value: 'Exit'
      });
      expect(req.request.method).toEqual('POST');
      req.flush({content: [culturalOffer]}, {status: 200, statusText: 'Ok' });
    });

    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.FilterCulturalOffersAction({page: 0, page_size: 1, parameter: '', value: 'Exit'}));
      effects.filteredOffers.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Error has occurred while performing filter!'));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers/filter?page=0&size=1');
      expect(req.request.method).toEqual('POST');
      req.flush( 'MockBody', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Update cultural offer', () => {
    const culturalOffer = {
      category: 2,
      categoryName: 'Manifestacija',
      description: 'Proslava 20. godišnjice EXIT festivala biće održana u njegovom ' +
        'poznatom punom formatu od 8. do 11. jula 2021. na Petrovaradinskoj tvrđavi.',
      endDate: 1594499671000,
      id: 1,
      latitude: 45.25167,
      location: 1,
      locationName: 'Novi Sad',
      longitude: 19.83694,
      name: 'Exit',
      pictures: [],
      startDate: 1594159200000,
      subcategory: 7,
      subcategoryName: 'Festival'
    };

    it('[SUCCESS] should return update cultural offer as payload.', () => {
      actions$ = of(new CulturalOfferActions.UpdateOfferAction({culturalOffer}));
      effects.updateOffer.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.UpdateOfferActionSuccess({content:
            [culturalOffer]}));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers');
      expect(req.request.method).toEqual('PUT');
      req.flush({content:
          [culturalOffer]});
    });

    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.UpdateOfferAction({page: 0, size: 1}));
      effects.updateOffer.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Cultural offer already exists.'));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers');
      expect(req.request.method).toEqual('PUT');
      req.flush( 'MockBody', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Get subcategories.', () => {
    const subcategory = {
      id: 8,
      name: 'Sajam',
      categoryId: 1,
      categoryName: 'Manifestacija'
    };

    it('[SUCCESS] should return a CategoryChangedActionSucces  with an array of subcategories as payload', () => {
      actions$ = of(new CulturalOfferActions.CategoryChangedAction({categoryId: 1, page: 0 , page_size: 1}));
      effects.onCategoryChanged.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.CategoryChangedActionSuccess({content:
            [subcategory]}));
      });
      const req = http.expectOne('http://localhost:8080/subcategories/1/by-page?page=0&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush({content:
          [subcategory]});
    });

    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.CategoryChangedAction({categoryId: 1, page: 0 , page_size: 1}));
      effects.onCategoryChanged.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Something went wrong while fetching subcategories!'));
      });
      const req = http.expectOne('http://localhost:8080/subcategories/1/by-page?page=0&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush( 'MockBody', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Get categories', () => {
    const category = {
      id: 8,
      name: 'Sajam'
    };

    it('[SUCCESS] should return a GetSubCategoriesAndCategories with an array of categories as payload', () => {
      actions$ = of(new CulturalOfferActions.GetCategories({page: 0, page_size: 1}));
      effects.categoriesSelect.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.GetSubCategoriesAndCategories({page: 0, page_size: 1, categories: [category]}));
      });
      const req = http.expectOne('http://localhost:8080/categories/by-page?page=0&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush({content:
          [category]});
    });


    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.GetCategories({page: 0, page_size: 1}));
      effects.categoriesSelect.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Something went wrong while fetching categories.'));
      });
      const req = http.expectOne('http://localhost:8080/categories/by-page?page=0&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush( 'MockBody', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Get categories and subcategories.', () => {
    const category = {
      id: 1,
      name: 'Sajam'
    };

    const subcategory = {
      id: 8,
      name: 'Sajam',
      categoryId: 1,
      categoryName: 'Manifestacija'
    };

    it('[SUCCESS] should return a GetSubCategoriesAndCategoriesSuccess  with an array of subcategories as payload', () => {
      actions$ = of(new CulturalOfferActions.GetSubCategoriesAndCategories({page: 0 , page_size: 1, categories: {content: [category]} }));
      effects.subcategoriesAndCategoriesSelect.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.GetSubCategoriesAndCategoriesSuccess(
          {categories: {content: [category]}, subcategories: {content: [subcategory]}}));
      });
      const req = http.expectOne('http://localhost:8080/subcategories/1/by-page?page=0&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush({content:
          [subcategory]});
    });

    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.GetSubCategoriesAndCategories({page: 0 , page_size: 1, categories: {content: [category]} }));
      effects.subcategoriesAndCategoriesSelect.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Something went wrong while fetching categories and subcategories!'));
      });
      const req = http.expectOne('http://localhost:8080/subcategories/1/by-page?page=0&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush( 'MockBody', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Get one cultural offer', () => {
    const culturalOffer = {
      category: 2,
      categoryName: 'Manifestacija',
      description: 'Proslava 20. godišnjice EXIT festivala biće održana u njegovom ' +
        'poznatom punom formatu od 8. do 11. jula 2021. na Petrovaradinskoj tvrđavi.',
      endDate: 1594499671000,
      id: 1,
      latitude: 45.25167,
      location: 1,
      locationName: 'Novi Sad',
      longitude: 19.83694,
      name: 'Exit',
      pictures: [],
      startDate: 1594159200000,
      subcategory: 7,
      subcategoryName: 'Festival'
    };

    it('[SUCCESS] should return a cultural offer with given id', () => {
      actions$ = of(new CulturalOfferActions.GetOneOfferAction(1));
      effects.oneOffer.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.GetSubCategories({page: 0 , page_size: 10, culturalOffer: {content:
            [culturalOffer]}}));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers/1');
      expect(req.request.method).toEqual('GET');
      req.flush({content:
          [culturalOffer]});
    });

    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.GetOneOfferAction(1));
      effects.oneOffer.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Something went wrong while cultural offer!'));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers/1');
      expect(req.request.method).toEqual('GET');
      req.flush( 'MockBody', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Get subcategories for select.', () => {
    const subcategory = {
      id: 8,
      name: 'Sajam',
      categoryId: 1,
      categoryName: 'Manifestacija'
    };

    const culturalOffer = {
      category: 2,
      categoryName: 'Manifestacija',
      description: 'Proslava 20. godišnjice EXIT festivala biće održana u njegovom ' +
        'poznatom punom formatu od 8. do 11. jula 2021. na Petrovaradinskoj tvrđavi.',
      endDate: 1594499671000,
      id: 1,
      latitude: 45.25167,
      location: 1,
      locationName: 'Novi Sad',
      longitude: 19.83694,
      name: 'Exit',
      pictures: [],
      startDate: 1594159200000,
      subcategory: 7,
      subcategoryName: 'Festival'
    };

    it('[SUCCESS] should return a GetInitialCategories with an subcategories for category of given cultural offer.', () => {
      actions$ = of(new CulturalOfferActions.GetSubCategories({page: 0, page_size: 1, culturalOffer}));
      effects.subcategoriesSelect.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.GetInitialCategories({page: 0, page_size: 10, subcategories: {content:
            [subcategory]}, culturalOffer}));
      });
      const req = http.expectOne('http://localhost:8080/subcategories/2/by-page?page=0&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush({content:
          [subcategory]});
    });

    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.GetSubCategories({page: 0, page_size: 1, culturalOffer}));
      effects.subcategoriesSelect.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Something went wrong while fetching subcategories!'));
      });
      const req = http.expectOne('http://localhost:8080/subcategories/2/by-page?page=0&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush( 'MockBody', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Get initial categories', () => {
    const category = {
      id: 8,
      name: 'Sajam'
    };
    const subcategory = {
      id: 8,
      name: 'Sajam',
      categoryId: 1,
      categoryName: 'Manifestacija'
    };

    const culturalOffer = {
      category: 2,
      categoryName: 'Manifestacija',
      description: 'Proslava 20. godišnjice EXIT festivala biće održana u njegovom ' +
        'poznatom punom formatu od 8. do 11. jula 2021. na Petrovaradinskoj tvrđavi.',
      endDate: 1594499671000,
      id: 1,
      latitude: 45.25167,
      location: 1,
      locationName: 'Novi Sad',
      longitude: 19.83694,
      name: 'Exit',
      pictures: [],
      startDate: 1594159200000,
      subcategory: 7,
      subcategoryName: 'Festival'
    };

    it('[SUCCESS] should return a GetOneOfferActionSuccess with an array of categories as payload', () => {
      actions$ = of(new CulturalOfferActions.GetInitialCategories({page: 0, page_size: 1, subcategories: [subcategory], culturalOffer}));
      effects.getCategoriesInitial.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.GetOneOfferActionSuccess(
          {categories: {content: [category]}, subcategories: [subcategory], culturalOffer}));
      });
      const req = http.expectOne('http://localhost:8080/categories/by-page?page=0&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush({content:
          [category]});
    });

    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.GetInitialCategories({page: 0, page_size: 1, subcategories: [subcategory], culturalOffer}));
      effects.getCategoriesInitial.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Something went wrong while fetching categories.'));
      });
      const req = http.expectOne('http://localhost:8080/categories/by-page?page=0&size=1');
      expect(req.request.method).toEqual('GET');
      req.flush( 'MockBody', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Add cultural offer', () => {

    const culturalOffer = {
      category: 2,
      categoryName: 'Manifestacija',
      description: 'Proslava 20. godišnjice EXIT festivala biće održana u njegovom ' +
        'poznatom punom formatu od 8. do 11. jula 2021. na Petrovaradinskoj tvrđavi.',
      endDate: 1594499671000,
      id: 1,
      latitude: 45.25167,
      location: 1,
      locationName: 'Novi Sad',
      longitude: 19.83694,
      name: 'Exit',
      pictures: [],
      startDate: 1594159200000,
      subcategory: 7,
      subcategoryName: 'Festival'
    };

    it('[SUCCESS] should return a add cultural offer', () => {
      actions$ = of(new CulturalOfferActions.AddOfferAction(culturalOffer));
      effects.addCulturalOffer.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.AddOfferActionSuccess({content: [culturalOffer]}));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers/');
      expect(req.request.method).toEqual('POST');
      req.flush({content:
          [culturalOffer]});
    });

    it('[ERROR] should return a ErrorAction with an error message as payload', () => {
      actions$ = of(new CulturalOfferActions.AddOfferAction(culturalOffer));
      effects.addCulturalOffer.subscribe(action => {
        expect(action).toEqual(new CulturalOfferActions.ErrorAction('Cultural offer already exists.'));
      });
      const req = http.expectOne('http://localhost:8080/cultural-offers/');
      expect(req.request.method).toEqual('POST');
      req.flush( 'MockBody', { status: 400, statusText: 'Bad Request' });
    });
  });

});
