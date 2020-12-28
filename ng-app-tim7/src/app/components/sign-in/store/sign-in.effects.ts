import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, ofType, Effect } from '@ngrx/effects';
import { switchMap, catchError, map, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import * as AuthActions from '../store/sign-in.actions';
import { SignedInModel } from '../../../models/signed-in.model';

export interface AuthResponseData {
  id: number;
  accessToken: string;
  username: string;
  role: string;
}

const handleAuthentication = (username: string, id: number, accessToken: string) => {
  const user = new SignedInModel(username, id, accessToken);
  localStorage.setItem('userData', JSON.stringify(user));
  return new AuthActions.AuthenticateSuccess({
    username,
    id,
    accessToken
  });
};

const handleError = (errorRes: any) => {
  console.log(errorRes);
  let errorMessage = 'An unknown error occurred!';
  if (!errorRes.error || !errorRes.error.error) {
    return of(new AuthActions.AuthenticateFail(errorMessage));
  }
  switch (errorRes.error.error.message) {
    case 'USERNAME_EXISTS':
      errorMessage = 'This username exists already';
      break;
    case 'USERNAME_NOT_FOUND':
      errorMessage = 'This username does not exist.';
      break;
    case 'INVALID_PASSWORD':
      errorMessage = 'This password is not correct.';
      break;
  }
  return of(new AuthActions.AuthenticateFail(errorMessage));
};

@Injectable()
export class AuthEffects {
  @Effect()
  authLogin = this.actions$.pipe(
    ofType(AuthActions.SIGN_IN),
    switchMap((authData: AuthActions.SignInStart) => {
      return this.http
        .post<AuthResponseData>(
          'http://localhost:8080/auth/log-in',
          {
            username: authData.payload.username,
            password: authData.payload.password
          }
        )
        .pipe(
          map(resData => {
            return handleAuthentication(
              authData.payload.username,
              resData.id,
              resData.accessToken
            );
          }),
          tap(() => {
            this.router.navigate(['/']);
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );

  @Effect({ dispatch: false })
  authRedirect = this.actions$.pipe(
    ofType(AuthActions.AUTHENTICATE_SUCCESS),
    tap(() => {
      this.router.navigate(['/']);
    })
  );

  @Effect({ dispatch: false })
  authLogout = this.actions$.pipe(
    ofType(AuthActions.SIGN_OUT),
    tap(() => {
      localStorage.removeItem('userData');
      this.router.navigate(['/']);
    })
  );

  constructor(private actions$: Actions, private http: HttpClient, private router: Router) {}
}
