import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Store} from '@ngrx/store';
import * as fromApp from '../../store/app.reducer';
// import * as AuthActions from '../sign-in/store/auth.actions';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private store: Store<fromApp.AppState>
  ) {
    this.form = this.fb.group({
      username : [null, Validators.required],
      password: [null, Validators.required]
    });
  }

  ngOnInit() {
  }

  submit() {
    const auth: any = {};
    auth.username = this.form.value.username;
    auth.password = this.form.value.password;

    // this.store.dispatch(new AuthActions.LoginStart({ email: email, password: password })

    /*this.authenticationService.login(auth).subscribe(
      result => {
        this.snackBar.open('Successful login!', {
          duration: 2000,
        });
        localStorage.setItem('user', JSON.stringify(result));
        this.router.navigate(['wines']);
      },
      error => {
        this.snackBar.open(error.error, {
          duration: 2000,
        });
      }
*/
  }
}
