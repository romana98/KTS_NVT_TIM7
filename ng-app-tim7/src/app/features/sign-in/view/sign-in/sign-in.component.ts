import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../../store/app.reducer';
import * as AuthActions from '../../store/sign-in.actions';
import {Subscription} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit, OnDestroy {

  form: FormGroup;
  error = '';

  storeSub: Subscription;

  constructor(
    private fb: FormBuilder,
    public store: Store<fromApp.AppState>,
    public snackBar: MatSnackBar
  ) {
    this.form = this.fb.group({
      username : [null, Validators.required],
      password: [null, Validators.required]
    });
  }

  ngOnInit() {
    this.storeSub = this.store.select('auth').subscribe(authState => {
      this.error = authState.authError;
      if (this.error) {
        this.showErrorAlert(this.error);
      }
    });
  }

  submit() {
    const auth: any = {};
    auth.username = this.form.value.username;
    auth.password = this.form.value.password;

    this.store.dispatch(new AuthActions.SignInStart({ username: auth.username, password: auth.password }));
  }

  showErrorAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 2000 });
    this.store.dispatch(new AuthActions.ClearError());
  }

  ngOnDestroy() {
    if (this.storeSub) {
      this.storeSub.unsubscribe();
    }
  }
}
