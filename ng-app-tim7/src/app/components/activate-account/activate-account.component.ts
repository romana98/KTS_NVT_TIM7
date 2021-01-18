import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import * as ActivateAccountActions from './store/activate-account.actions';
import {Store} from '@ngrx/store';
import * as fromApp from '../../store/app.reducer';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.css']
})
export class ActivateAccountComponent implements OnInit, OnDestroy {

  private storeSub: Subscription;
  error: string;
  success: string;
  id: number;

  constructor(private activatedRoute: ActivatedRoute,
              private store: Store<fromApp.AppState>,
              private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.storeSub = this.store.select('activate').subscribe(state => {
      this.error = state.error;
      this.success = state.success;
      if (this.error) {
        this.showErrorAlert(this.error);
      }
      if (this.success) {
        this.showSuccessAlert(this.success);
      }
    });
    this.activatedRoute.queryParams.subscribe(params => {
      this.id = params.id;

      this.store.dispatch(new ActivateAccountActions.ActivateStart({ id: this.id }));
    });
  }

  private showErrorAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new ActivateAccountActions.ClearError());
  }

  private showSuccessAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new ActivateAccountActions.ClearSuccess());
  }

  ngOnDestroy() {
    if (this.storeSub) {
      this.storeSub.unsubscribe();
    }
  }

}
