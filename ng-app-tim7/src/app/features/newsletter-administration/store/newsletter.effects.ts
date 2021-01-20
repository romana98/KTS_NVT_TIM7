import { Injectable } from '@angular/core';
import { Actions, ofType, Effect } from '@ngrx/effects';
import {switchMap, catchError, map, tap} from 'rxjs/operators';
import { of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import * as NewsletterActions from './newsletter.actions';
import {NewsletterModel} from '../../../models/newsletter.model';
import {Router} from '@angular/router';

const handleSuccess = (type: string) => {
  let message = '';
  if (type === 'delete'){
    message = 'Newsletter deleted.';
  }
  else if (type === 'edit') {
    message = 'Newsletter updated.';
  }
  else if (type === 'unsubscribe') {
    message = 'Successfully unsubscribed.';
  }
  else{
    message = 'Newsletter added.';
  }
  return new NewsletterActions.NewsletterSuccess(message);
};

const handleError = (errorRes: any) => {
  let errorMessage = errorRes.error;
  if (!( typeof errorRes.error === 'string')) {
    errorMessage = 'An unknown error occurred!';
  }
  return of(new NewsletterActions.NewsletterFail(errorMessage));
};

@Injectable()
export class NewsletterEffects {
  @Effect()
  newsletters = this.actions$.pipe(
    ofType(NewsletterActions.GET_NEWSLETTER_PAGE),
    switchMap((data: NewsletterActions.GetNewsletterPage) => {
      return this.http
        .get(
          'http://localhost:8080/newsletter/by-page?page=' + data.payload.page + '&size=' + data.payload.size
        )
        .pipe(
          map(dataRes => {
            return new NewsletterActions.GetNewslettersSuccess(dataRes);
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );

  @Effect()
  categoriesSelect = this.actions$.pipe(
    ofType(NewsletterActions.GET_CATEGORIES_SELECT),
    switchMap((data: NewsletterActions.GetCategoriesSelect) => {
      return this.http
        .get(
          'http://localhost:8080/categories/by-page?page=' + data.payload.page + '&size=' + data.payload.size
        )
        .pipe(
          map(dataRes => {
            return new NewsletterActions.GetCategoriesSelectSuccess(dataRes);
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );

  @Effect()
  subcategoriesSelect = this.actions$.pipe(
    ofType(NewsletterActions.GET_SUBCATEGORIES_SELECT),
    switchMap((data: NewsletterActions.GetSubcategoriesSelect) => {
      return this.http
        .get(
          'http://localhost:8080/subcategories/' + data.payload.category + '/by-page?page=' + data.payload.page +
          '&size=' + data.payload.size
        )
        .pipe(
          map(dataRes => {
            return new NewsletterActions.GetSubcategoriesSelectSuccess(dataRes);
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );

  @Effect()
  offersSelect = this.actions$.pipe(
    ofType(NewsletterActions.GET_OFFERS_SELECT),
    switchMap((data: NewsletterActions.GetOffersSelect) => {
      return this.http
        .get(
          'http://localhost:8080/cultural-offers/subcategory/' + data.payload.subcategory + '/by-page?page=' +
          data.payload.page + '&size=' + data.payload.size
        )
        .pipe(
          map(dataRes => {
            return new NewsletterActions.GetOffersSelectSuccess(dataRes);
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );


  @Effect()
  newsletter = this.actions$.pipe(
    ofType(NewsletterActions.GET_NEWSLETTER),
    switchMap((data: NewsletterActions.GetNewsletter) => {
      return this.http
        .get<NewsletterModel>(
          'http://localhost:8080/newsletter/' + data.payload.id
        )
        .pipe(
          map(dataRes => {
            const newsletter = new NewsletterModel(dataRes.id, dataRes.name, dataRes.description, dataRes.publishedDate,
              dataRes.culturalOfferId, dataRes.picture, '');
            return new NewsletterActions.GetNewsletterSuccess(newsletter);
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );


  @Effect()
  delete = this.actions$.pipe(
    ofType(NewsletterActions.DELETE_NEWSLETTER),
    switchMap((data: NewsletterActions.DeleteNewsletter) => {
      return this.http
        .delete(
          'http://localhost:8080/newsletter/' + data.payload, {responseType: 'text'}
        )
        .pipe(
          map(() => {
            return handleSuccess('delete');
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );

  @Effect()
  add = this.actions$.pipe(
    ofType(NewsletterActions.ADD_NEWSLETTER),
    switchMap((userData: NewsletterActions.AddNewsletter) => {
      return this.http
        .post<NewsletterModel>(
          'http://localhost:8080/newsletter',
          {
            name: userData.payload.name,
            description: userData.payload.description,
            picture: userData.payload.picture,
            culturalOfferId: userData.payload.culturalOfferId,
            publishedDate: userData.payload.publishedDate
          }
        )
        .pipe(
          map(() => {
            return handleSuccess('add');
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );

  @Effect()
  edit = this.actions$.pipe(
    ofType(NewsletterActions.UPDATE_NEWSLETTER),
    switchMap((userData: NewsletterActions.UpdateNewsletter) => {
      return this.http
        .put<NewsletterModel>(
          'http://localhost:8080/newsletter',
          {
            id: userData.payload.newsletter.id,
            name: userData.payload.newsletter.name,
            description: userData.payload.newsletter.description,
            publishedDate: userData.payload.newsletter.publishedDate,
            culturalOfferId: userData.payload.newsletter.culturalOfferId,
            picture: userData.payload.newsletter.picture,
          }
        )
        .pipe(
          map(() => {
            return handleSuccess('edit');
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );

  @Effect()
  categoriesSubscribed = this.actions$.pipe(
    ofType(NewsletterActions.GET_CATEGORIES_SUBSCRIBED),
    switchMap((data: NewsletterActions.GetCategoriesSubscribed) => {
      return this.http
        .get(
          'http://localhost:8080/categories/subscribed/' + data.payload.id
        )
        .pipe(
          map(dataRes => {
            return new NewsletterActions.GetCategoriesSubscribedSuccess(dataRes);
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );

  @Effect()
  newslettersSubscribed = this.actions$.pipe(
    ofType(NewsletterActions.GET_NEWSLETTERS_SUBSCRIBED),
    switchMap((data: NewsletterActions.GetNewslettersSubscribed) => {
      return this.http
        .get(
          'http://localhost:8080/newsletter/subscribed/' + data.payload.id + '/' + data.payload.catId + '/by-page?page=' +
            data.payload.page + '&size=' + data.payload.size
        )
        .pipe(
          map(dataRes => {
            return new NewsletterActions.GetNewslettersSubscribedSuccess(dataRes);
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );

  @Effect()
  unsubscribe = this.actions$.pipe(
    ofType(NewsletterActions.UNSUBSCRIBE),
    switchMap((userData: NewsletterActions.Unsubscribe) => {
      return this.http
        .post<any>(
          'http://localhost:8080/cultural-offers/unsubscribe',
          {
            idOffer: userData.payload.idOffer,
            idUser: userData.payload.idUser
          }, {responseType: 'text' as 'json'}
        )
        .pipe(
          map(() => {
            return handleSuccess('unsubscribe');
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
      })
    );

  constructor(private actions$: Actions, private http: HttpClient, private router: Router) {}
}
