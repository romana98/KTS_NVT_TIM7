import {Injectable} from '@angular/core';
import {Actions, Effect, ofType} from '@ngrx/effects';
import * as culturalOfferActions from '../../cultural-offer/store/cultural-offer.actions';
import {catchError, map, switchMap} from 'rxjs/operators';
import * as CulturalOfferActions from './cultural-offer.actions';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {of} from 'rxjs';

@Injectable()
export class CulturalOfferEffects {
  @Effect()
  culturalOffers = this.actions$.pipe(
    ofType(culturalOfferActions.GET_CULTURALOFFER_PAGE),
    switchMap((data: culturalOfferActions.GetCulturalOfferPage) => {
      return this.http.get(
          'http://localhost:8080/cultural-offers/by-page?page=' + data.payload.page + '&size=' + data.payload.size
        )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.GetCulturalOfferPageSuccess(dataRes);
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction(errorRes.message));
          })
        );
    })
  );

  @Effect()
  deleteOffer = this.actions$.pipe(
    ofType(culturalOfferActions.DELETE_CULTURALOFFER),
    switchMap((data: culturalOfferActions.DeleteCulturalOffer) => {
      return this.http.delete<string>(
        'http://localhost:8080/cultural-offers/' + data.payload.id
      )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.SuccessAction('Cultural offer successfully deleted.');
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction('Cultural offer cant be removed'));
          })
        );
    })
  );

  @Effect()
  filteredOffers = this.actions$.pipe(
    ofType(culturalOfferActions.FILTER_ACTION),
    switchMap((data: culturalOfferActions.FilterCulturalOffersAction) => {
      return this.http.post(
        'http://localhost:8080/cultural-offers/filter?page=' + data.payload.page + '&size=' + data.payload.page_size,
        {parameter : data.payload.parameter, value: data.payload.value}
      )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.FilterSuccessAction(dataRes);
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction(errorRes.message));
          })
        );
    })
  );

  @Effect()
  onCategoryChanged = this.actions$.pipe(
    ofType(culturalOfferActions.CATEGORY_CHANGED),
    switchMap((data: culturalOfferActions.CategoryChangedAction) => {
      return this.http
        .get(
          'http://localhost:8080/subcategories/' + data.payload.categoryId + '/by-page?page=' + data.payload.page +
          '&size=' + data.payload.page_size
        )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.CategoryChangedActionSuccess(dataRes);
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction(errorRes.message));
          })
        );
    })
  );

  @Effect()
  comments = this.actions$.pipe(
    ofType(culturalOfferActions.GET_COMMENTS),
    switchMap((data: culturalOfferActions.GetComments) => {
      return this.http.get(
        'http://localhost:8080/comments/' + data.payload.offerId + '/by-page?page=' + data.payload.page + '&size=' + data.payload.size
      )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.GetCommentsSuccess(dataRes);
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction('Something went wrong while fetching comments!'));
          })
        );
    })
  );

  @Effect()
  updateOffer = this.actions$.pipe(
    ofType(culturalOfferActions.UPDATE_OFFER_ACTION),
    switchMap((data: culturalOfferActions.UpdateOfferAction) => {
      return this.http.put(
        'http://localhost:8080/cultural-offers', data.payload
      )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.UpdateOfferActionSuccess(dataRes);
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction(errorRes.message));
          })
        );
    })
  );

  @Effect()
  newsletters = this.actions$.pipe(
    ofType(culturalOfferActions.GET_NEWSLETTERS),
    switchMap((data: culturalOfferActions.GetNewsletters) => {
      return this.http.get(
        'http://localhost:8080/newsletter/cultural-offer/' + data.payload.offerId + '/by-page?page=' +
        data.payload.page + '&size=' + data.payload.size
      )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.GetNewslettersSuccess(dataRes);
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction('Something went wrong while fetching newsletters!'));
          })
        );
    })
  );

  @Effect()
  averageRating = this.actions$.pipe(
    ofType(culturalOfferActions.GET_AVERAGE_RATING),
    switchMap((data: culturalOfferActions.GetAverageRating) => {
      return this.http.get(
        'http://localhost:8080/ratings/getRating/average/' + data.payload.offerId
      )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.GetAverageRatingSuccess(dataRes);
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction('Something went wrong while fetching average rating!'));
          })
        );
    })
  );

  @Effect()
  categoriesSelect = this.actions$.pipe(
    ofType(culturalOfferActions.GET_CATEGORIES_SELECT),
    switchMap((data: culturalOfferActions.GetCategories) => {
      return this.http
        .get(
          'http://localhost:8080/categories/by-page?page=' + data.payload.page + '&size=' + data.payload.page_size
        )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.GetSubCategoriesAndCategories(
              {page: 0, page_size: 10, categories: dataRes});
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction(errorRes.message));
          })
        );
    })
  );

  @Effect()
  rate = this.actions$.pipe(
    ofType(culturalOfferActions.RATE),
    switchMap((data: culturalOfferActions.Rate) => {
      return this.http.post(
        'http://localhost:8080/ratings/',
        {
          registeredId : data.payload.registeredId,
          rate: data.payload.rate,
          culturalOfferId: data.payload.offerId
        }
      )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.SuccessAction('Successfully rated!');
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction('Failed to rate!'));
          })
        );
    })
  );

  @Effect()
  subcategoriesAndCategoriesSelect = this.actions$.pipe(
    ofType(culturalOfferActions.GET_SUBCATEGORIES_AND_CATEGORIES),
    switchMap((data: culturalOfferActions.GetSubCategoriesAndCategories) => {
      return this.http
        .get(
          'http://localhost:8080/subcategories/' + data.categoryId + '/by-page?page=' + data.payload.page +
          '&size=' + data.payload.page_size
        )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.GetSubCategoriesAndCategoriesSuccess(
              {categories: data.payload.categories, subcategories: dataRes});
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction(errorRes.message));
          })
        );
    })
  );

  @Effect()
  comment = this.actions$.pipe(
    ofType(culturalOfferActions.CREATE_COMMENT),
    switchMap((data: culturalOfferActions.CreateComment) => {
      return this.http.post(
        'http://localhost:8080/comments/',
        {
          description: data.payload.description,
          publishedDate: data.payload.publishedDate,
          registeredId: data.payload.registeredId,
          picturesId: data.payload.picturesId,
          culturalOfferId: data.payload.culturalOfferId
        }
      )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.SuccessAction('Successfully commented!');
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction('Failed to comment!'));
          })
        );
    })
  );

  @Effect()
  alreadyRated = this.actions$.pipe(
    ofType(culturalOfferActions.ALREADY_RATED),
    switchMap((data: culturalOfferActions.AlreadyRated) => {
      return this.http.get(
        'http://localhost:8080/ratings/getRating/' + data.payload
      )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.AlreadyRatedSuccess(dataRes);
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction('Something went wrong!'));
          })
        );
    })
  );

  @Effect()
  subscribe = this.actions$.pipe(
    ofType(culturalOfferActions.SUBSCRIBE),
    switchMap((data: culturalOfferActions.Subscribe) => {
      return this.http.post(
        'http://localhost:8080/cultural-offers/subscribe',
        {
          idOffer: data.payload.offerId,
          idUser: data.payload.userId
        }
      )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.SuccessAction('Successfully subscribed!');
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction('Failed to subscribe!'));
          })
        );
    })
  );

  @Effect()
  unsubscribe = this.actions$.pipe(
    ofType(culturalOfferActions.UNSUBSCRIBE),
    switchMap((data: culturalOfferActions.Unsubscribe) => {
      return this.http.post(
        'http://localhost:8080/cultural-offers/unsubscribe',
        {
          idOffer: data.payload.offerId,
          idUser: data.payload.userId
        }
      )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.SuccessAction('Successfully unsubscribed!');
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction('Failed to unsubscribe!'));
          })
        );
    })
  );
  @Effect()
  alreadySubscribed = this.actions$.pipe(
    ofType(culturalOfferActions.ALREADY_SUBSCRIBED),
    switchMap((data: culturalOfferActions.AlreadySubscribed) => {
      return this.http.get(
        'http://localhost:8080/cultural-offers/alreadySubscribed/' + data.payload
      )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.AlreadySubscribedValue(true);
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.AlreadySubscribedValue(false));
          })
        );
    })
  );

  @Effect()
  oneOffer = this.actions$.pipe(
    ofType(culturalOfferActions.GET_ONE_OFFER_ACTION),
    switchMap((data: culturalOfferActions.GetOneOfferAction) => {
      return this.http.get(
        'http://localhost:8080/cultural-offers/' + data.payload
      )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.GetSubCategories({page: 0, page_size: 10, culturalOffer: dataRes});
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction(errorRes.message));
          })
        );
    })
  );

  @Effect()
  subcategoriesSelect = this.actions$.pipe(
    ofType(culturalOfferActions.GET_SUBCATEGORIES_SELECT),
    switchMap((data: culturalOfferActions.GetSubCategories) => {
      return this.http
        .get(
          'http://localhost:8080/subcategories/' + data.categoryId + '/by-page?page=' + data.payload.page +
                                                                                          '&size=' + data.payload.page_size
        )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.GetInitialCategories(
              {page: 0, page_size: 10, subcategories: dataRes, culturalOffer: data.payload.culturalOffer});
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction(errorRes.message));
          })
        );
    })
  );

  @Effect()
  getCategoriesInitial = this.actions$.pipe(
    ofType(culturalOfferActions.GET_INITIAL_CATEGORIES_SELECT),
    switchMap((data: culturalOfferActions.GetInitialCategories) => {
      return this.http
        .get(
          'http://localhost:8080/categories/by-page?page=' + data.payload.page + '&size=' + data.payload.page_size
        )
        .pipe(
          map(dataRes => {
            return new CulturalOfferActions.GetOneOfferActionSuccess(
              {categories: dataRes, subcategories: data.payload.subcategories, culturalOffer: data.payload.culturalOffer});
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction(errorRes.message));
          })
        );
    })
  );


  constructor(private actions$: Actions, private http: HttpClient, private router: Router) {}

}
