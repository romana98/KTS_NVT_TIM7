import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, ofType, Effect } from '@ngrx/effects';
import {switchMap, catchError, map, tap} from 'rxjs/operators';
import { of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import * as ActivateAccountActions from './activate-account.actions';

const handleAuthentication = () => {
  const message = 'Activation succeeded.';
  return new ActivateAccountActions.ActivateSuccess(message);
};

const handleError = (errorRes: any) => {
  let errorMessage = errorRes.error;
  if (!errorRes.error) {
    errorMessage = 'An unknown error occurred!';
  }
  return of(new ActivateAccountActions.ActivateFail(errorMessage));
};

@Injectable()
export class ActivateAccountEffects {
  @Effect()
  activate = this.actions$.pipe(
    ofType(ActivateAccountActions.ACTIVATE),
    switchMap((userData: ActivateAccountActions.ActivateStart) => {
      return this.http
        .post<string>(
          'http://localhost:8080/auth/activate/' + userData.payload.id,
          {},
          {responseType: 'json'}
        )
        .pipe(
          map(() => {
            return handleAuthentication();
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );

  @Effect({ dispatch: false })
  signUpRedirect = this.actions$.pipe(
    ofType(ActivateAccountActions.ACTIVATE_SUCCESS, ActivateAccountActions.ACTIVATE_FAIL),
    tap(() => {
      this.router.navigate(['/']);
    })
  );
  constructor(private actions$: Actions, private http: HttpClient, private router: Router) {}
}
