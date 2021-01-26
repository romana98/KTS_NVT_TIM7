import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../../store/app.reducer';
import {MatSnackBar} from '@angular/material/snack-bar';
import * as SignUpActions from '../../store/sign-up.actions';
import {validateMatchPassword} from '../../../../validator/custom-validator-match-password';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit, OnDestroy {

  form: FormGroup;
  error: string = null;
  success: string = null;
  bar = false;

  storeSub: Subscription;

  constructor(
    private fb: FormBuilder,
    private store: Store<fromApp.AppState>,
    public snackBar: MatSnackBar
  ) {
    this.form = this.fb.group({
      username : [null, Validators.required],
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required, Validators.minLength(8)]],
      passwordConfirm: [null, Validators.required]
    },
      {
        validator:  validateMatchPassword('password', 'passwordConfirm')
      });
  }

  ngOnInit() {
    this.storeSub = this.store.select('signUp').subscribe(state => {
      this.error = state.error;
      this.success = state.success;
      this.bar = state.bar;
      if (this.error) {
        this.showErrorAlert(this.error);
      }

      if (this.success) {
        this.showSuccessAlert(this.success);
      }
      if (this.bar){
        this.form.disable();
      }else{
        this.form.enable();
      }
    });
  }

  submit() {
    const user: any = {};
    user.username = this.form.value.username;
    user.email = this.form.value.email;
    user.password = this.form.value.password;

    this.store.dispatch(new SignUpActions.SignUpStart({ username: user.username, email: user.email, password: user.password }));
  }

  showErrorAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 2000 });
    this.store.dispatch(new SignUpActions.ClearError());
  }

  showSuccessAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 2000 });
    this.store.dispatch(new SignUpActions.ClearSuccess());
  }

  ngOnDestroy() {
    if (this.storeSub) {
      this.storeSub.unsubscribe();
    }
  }

}
