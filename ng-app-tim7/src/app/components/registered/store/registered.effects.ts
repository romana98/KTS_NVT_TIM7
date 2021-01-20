import { Injectable } from '@angular/core';
import { Actions, ofType, Effect } from '@ngrx/effects';
import {switchMap, catchError, map, tap} from 'rxjs/operators';
import { of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import * as RegActions from './registered.actions';
import {UserModel} from '../../../models/user.model';
import {Router} from '@angular/router';

const handleSuccess = () => {
  const message = 'Profile updated.';

  return new RegActions.RegisteredSuccess(message);
};

const handleError = (errorRes: any) => {
  let errorMessage = errorRes.error;
  if (!( typeof errorRes.error === 'string')) {
    errorMessage = 'An unknown error occurred!';
  }
  return of(new RegActions.RegisteredFail(errorMessage));
};

@Injectable()
export class RegisteredEffects {
  @Effect()
  registered = this.actions$.pipe(
    ofType(RegActions.GET_REG),
    switchMap((data: RegActions.GetUser) => {
      return this.http
        .get<UserModel>(
          'http://localhost:8080/registered-users/' + JSON.parse(localStorage.getItem('user')).id
        )
        .pipe(
          map(dataRes => {
            const user = new UserModel(dataRes.username, dataRes.email, dataRes.password);
            localStorage.setItem('signed-in-user', JSON.stringify(user));
            return new RegActions.GetRegisteredSuccess(user);
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );
  @Effect()
  edit = this.actions$.pipe(
    ofType(RegActions.EDIT_REG),
    switchMap((userData: RegActions.EditProfile) => {
      return this.http
        .put<UserModel>(
          'http://localhost:8080/registered-users',
          {
            id: userData.payload.id,
            username: userData.payload.username,
            email: userData.payload.email,
            password: userData.payload.password === '' ? '________' : userData.payload.password
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
  registeredRedirect = this.actions$.pipe(
    ofType(RegActions.REG_SUCCESS),
    tap(() => {
      this.router.navigate(['/registered/view-profile']);
    })
  );
  constructor(private actions$: Actions, private http: HttpClient, private router: Router) {}
}
