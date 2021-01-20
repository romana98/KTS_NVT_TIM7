import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import {Store} from '@ngrx/store';
import * as fromApp from '../store/app.reducer';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdministratorGuard implements CanActivate {

  constructor(
    public store: Store<fromApp.AppState>,
    public router: Router
  ) { }

  canActivate(): Observable<boolean> {
    return this.store.select('auth').pipe(
      map(authState => {
        console.log(authState.user);
        return authState.user;
      }),
      map(user => {
        if (user.getRole() !== 'ROLE_ADMINISTRATOR') {
          this.router.navigate(['/']);
          return false;
        }
        return true;
      })
    );
  }
}
