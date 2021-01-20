import {Component, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import * as fromApp from '../store/app.reducer';
import * as AuthActions from '../components/sign-in/store/sign-in.actions';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  constructor(private store: Store<fromApp.AppState>) { }
  role: string;

  ngOnInit(): void {
    this.store.select('auth').pipe(
      map(authState => {
        return authState.user;
      }),
      map(user => {
        const isAuth = !!user;
        if (isAuth) {
          return user.getRole();
        }
        return 'NO_ROLE';
      })
    ).subscribe(value => this.role = value);
  }

  signOut = ($event: void): void => {
    this.store.dispatch(new AuthActions.SignOut());
  }
}
