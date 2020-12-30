import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, ofType, Effect } from '@ngrx/effects';
import {switchMap, catchError, map, tap} from 'rxjs/operators';
import { of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import * as AdminActions from '../store/administrator.actions';

const handleAuthentication = () => {
  const message = 'Administrator deleted.';
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
            return handleAuthentication();
          }),
          catchError(errorRes => {
            return handleError(errorRes);
          })
        );
    })
  );

  constructor(private actions$: Actions, private http: HttpClient, private router: Router) {}
}
