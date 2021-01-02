import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import {MatSnackBar} from '@angular/material/snack-bar';
import {validateMatchPassword} from '../../../validator/custom-validator-match-password';
import * as AdminActions from '../store/administrator.actions';

@Component({
  selector: 'app-add-administrator',
  templateUrl: './add-administrator.component.html',
  styleUrls: ['./add-administrator.component.css']
})
export class AddAdministratorComponent implements OnInit, OnDestroy {

  private storeSub: Subscription;
  form: FormGroup;
  error: string = null;
  success: string = null;
  bar = false;
  constructor(
    private fb: FormBuilder,
    private store: Store<fromApp.AppState>,
    private snackBar: MatSnackBar
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

  ngOnInit(): void {
    this.storeSub = this.store.select('administrator').subscribe(state => {
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

    this.store.dispatch(new AdminActions.AddAdmin({ username: user.username, email: user.email, password: user.password }));
  }

  private showErrorAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 2000 });
    this.store.dispatch(new AdminActions.ClearError());
  }

  private showSuccessAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new AdminActions.ClearSuccess());
  }

  ngOnDestroy() {
    if (this.storeSub) {
      this.storeSub.unsubscribe();
    }
  }

}
