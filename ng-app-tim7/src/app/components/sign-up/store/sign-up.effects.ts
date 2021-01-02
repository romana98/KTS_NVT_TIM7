import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, ofType, Effect } from '@ngrx/effects';
import {switchMap, catchError, map, tap, concatMap} from 'rxjs/operators';
import { of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import * as SignUpActions from '../store/sign-up.actions';
import { UserModel } from '../../../models/user.model';

const handleSuccess = () => {
  const message = 'Registration successful! Activate account by email.';
  return new SignUpActions.SignUpSuccess(message);
};

const handleError = (errorRes: any) => {

  let errorMessage = errorRes.error;
  if (!( typeof errorRes.error === 'string')) {
    errorMessage = 'An unknown error occurred!';
  }
  return of(new SignUpActions.SignUpFail(errorMessage));
};

@Injectable()
export class SignUpEffects {
  @Effect()
  signUp = this.actions$.pipe(
    ofType(SignUpActions.SIGN_UP),
    switchMap((userData: SignUpActions.SignUpStart) => {
      return this.http
        .post<UserModel>(
          'http://localhost:8080/auth/sign-up',
          {
            username: userData.payload.username,
            email: userData.payload.email,
            password: userData.payload.password
          }
        )
        .pipe(
          map(() => {
            return handleSuccess();
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );

  @Effect({ dispatch: false })
  signUpRedirect = this.actions$.pipe(
    ofType(SignUpActions.SIGN_UP_SUCCESS),
    tap(() => {
      this.router.navigate(['/']);
    })
  );
  constructor(private actions$: Actions, private http: HttpClient, private router: Router) {}
}
