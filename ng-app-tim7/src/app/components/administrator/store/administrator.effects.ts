import { Injectable } from '@angular/core';
import { Actions, ofType, Effect } from '@ngrx/effects';
import {switchMap, catchError, map} from 'rxjs/operators';
import { of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import * as AdminActions from '../store/administrator.actions';
import {UserModel} from '../../../models/user.model';

const handleAuthentication = (type: string) => {
  let message = '';
  if (type === 'delete'){
    message = 'Administrator deleted.';
  }else{
    message = 'Administrator added.';
  }
  return new AdminActions.AdminSuccess(message);
};

const handleError = (errorRes: any) => {
  let errorMessage = errorRes.error;
  if (!( typeof errorRes.error === 'string')) {
    errorMessage = 'An unknown error occurred!';
  }
  return of(new AdminActions.AdminFail(errorMessage));
};

@Injectable()
export class AdministratorEffects {
  @Effect()
  admins = this.actions$.pipe(
    ofType(AdminActions.GET_ADMIN_PAGE),
    switchMap((data: AdminActions.GetAdminPage) => {
      return this.http
        .get(
          'http://localhost:8080/administrators/by-page?page=' + data.payload.page + '&size=' + data.payload.size
        )
        .pipe(
          map(dataRes => {
            return new AdminActions.GetAdminsSuccess(dataRes);
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );

  @Effect()
  delete = this.actions$.pipe(
    ofType(AdminActions.DELETE_ADMIN),
    switchMap((data: AdminActions.DeleteAdmin) => {
      return this.http
        .delete(
          'http://localhost:8080/administrators/' + data.payload
        )
        .pipe(
          map(() => {
            return handleAuthentication('delete');
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );

  @Effect()
  add = this.actions$.pipe(
    ofType(AdminActions.ADD_ADMIN),
    switchMap((userData: AdminActions.AddAdmin) => {
      return this.http
        .post<UserModel>(
          'http://localhost:8080/administrators',
          {
            username: userData.payload.username,
            email: userData.payload.email,
            password: userData.payload.password
          }
        )
        .pipe(
          map(() => {
            return handleAuthentication('add');
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );

  constructor(private actions$: Actions, private http: HttpClient) {}
}
