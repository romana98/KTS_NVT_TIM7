import {Injectable, NgZone} from '@angular/core';
import { Router } from '@angular/router';
import { Actions, ofType, Effect } from '@ngrx/effects';
import { switchMap, catchError, map, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import * as AuthActions from './sign-in.actions';
import { SignedInModel } from '../../../models/signed-in.model';
import {JwtHelperService} from '@auth0/angular-jwt';

export interface AuthResponseData {
  id: number;
  accessToken: string;
  username: string;
  role: string;
}

const handleAuthentication = (accessToken: string) => {

  const jwt: JwtHelperService = new JwtHelperService();
  const info = jwt.decodeToken(accessToken);
  const username = info.username;
  const id = info.id;
  const role = info.role;
  const user = new SignedInModel(username, id, accessToken, role);
  localStorage.setItem('user', JSON.stringify(user));
  return new AuthActions.AuthenticateSuccess({
    username,
    id,
    accessToken,
    role
  });
};

const handleError = (errorRes: any) => {
  let errorMessage = 'An unknown error occurred!';
  if (!( typeof errorRes.error === 'string')) {
      errorMessage = 'Bad credentials!';
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
              resData.accessToken
            );
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
      this.zone.run(() => {
        this.router.navigate(['/']);
      });
    })
  );

  @Effect({ dispatch: false })
  authLogout = this.actions$.pipe(
    ofType(AuthActions.SIGN_OUT),
    tap(() => {
      localStorage.clear();
      this.zone.run(() => {
        this.router.navigate(['/']);
      });
    })
  );

  constructor(private actions$: Actions, public http: HttpClient, private router: Router, private zone: NgZone) {}
}
