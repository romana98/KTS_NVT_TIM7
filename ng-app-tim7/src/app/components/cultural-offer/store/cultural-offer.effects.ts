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
  oneOffer = this.actions$.pipe(
    ofType(culturalOfferActions.GET_ONE_OFFER_ACTION),
    switchMap((data: culturalOfferActions.GetOneOfferAction) => {
      return this.http.get(
        'http://localhost:8080/cultural-offers/' + data.payload
      )
        .pipe(
          map(dataRes => {
            console.log(dataRes);
            return new CulturalOfferActions.GetOneOfferActionSuccess(dataRes);
          }),
          catchError(errorRes => {
            return of(new CulturalOfferActions.ErrorAction(errorRes.message));
          })
        );
    })
  );

  constructor(private actions$: Actions, private http: HttpClient, private router: Router) {}

}
