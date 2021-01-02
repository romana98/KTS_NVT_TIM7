import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import {MatSnackBar} from '@angular/material/snack-bar';
import {validateMatchPassword} from '../../../validator/custom-validator-match-password';
import * as AdminActions from '../../administrator/store/administrator.actions';
import * as RegisteredActions from '../../registered/store/registered.actions';
import {UserModel} from '../../../models/user.model';
import {validateLength} from '../../../validator/custom-validator-zero-min-eight-length';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit, OnDestroy {

  user: string;
  private storeSub: Subscription;
  form: FormGroup;
  error: string = null;
  success: string = null;
  bar = false;
  Actions: any;
  userType;
  constructor(
    private fb: FormBuilder,
    private store: Store<fromApp.AppState>,
    private snackBar: MatSnackBar
  ) {
    this.form = this.fb.group({
        email: [null, [Validators.required, Validators.email]],
        password: [''],
        passwordConfirm: ['']
      },
      {
        validator:  [validateMatchPassword('password', 'passwordConfirm'), validateLength('password')]
      });

    this.userType = JSON.parse(localStorage.getItem('user')).role.slice(5).toLowerCase();
    this.Actions = this.userType === 'administrator' ? AdminActions : RegisteredActions;
  }

  ngOnInit(): void {
    this.storeSub = this.store.select(this.userType).subscribe(state => {
      this.user = state.user.username;
      this.form.controls.email.setValue(state.user.email);
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
    user.email = this.form.value.email;
    user.password = this.form.value.password;
    this.store.dispatch(new this.Actions.EditProfile(
      { id: JSON.parse(localStorage.getItem('user')).id,  username: this.user, email: user.email, password: user.password }));
  }

  private showErrorAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 2000 });
    this.store.dispatch(new this.Actions.ClearError());
  }

  private showSuccessAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new this.Actions.ClearSuccess());
  }

  ngOnDestroy() {
    if (this.storeSub) {
      this.storeSub.unsubscribe();
    }
  }

}
