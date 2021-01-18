import {Component, OnDestroy, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import * as NewsletterActions from '../store/newsletter.actions';
import {Subscription} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';

@Component({
  selector: 'app-subscribed-newsletter',
  templateUrl: './subscribed-newsletter.component.html',
  styleUrls: ['./subscribed-newsletter.component.css']
})
export class SubscribedNewsletterComponent implements OnInit, OnDestroy {

  categoriesSubscribed = [];
  success: string = null;
  error: string = null;
  private storeSub: Subscription;

  constructor(private store: Store<fromApp.AppState>,
              private snackBar: MatSnackBar,
              private router: Router) {}

  ngOnInit(): void {
    this.store.dispatch(new NewsletterActions.GetCategoriesSubscribed({ id: JSON.parse(localStorage.getItem('user')).id }));
    this.storeSub = this.store.select('newsletter').subscribe(state => {
      this.categoriesSubscribed = state.categoriesSubscribed;

      this.success = state.success;
      this.error = state.error;
      if (this.success) {
        this.showSuccessAlert(this.success);
      }
      if (this.error) {
        this.showErrorAlert(this.error);
      }
    });
  }

  private showSuccessAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new NewsletterActions.ClearSuccess());
  }

  private showErrorAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new NewsletterActions.ClearError());
  }

  ngOnDestroy() {
    if (this.storeSub) {
      this.storeSub.unsubscribe();
    }
  }

}
