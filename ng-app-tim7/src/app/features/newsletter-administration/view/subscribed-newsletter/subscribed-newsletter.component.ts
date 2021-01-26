import {Component, OnDestroy, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../../store/app.reducer';
import * as NewsletterActions from '../../store/newsletter.actions';
import {Subscription} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-subscribed-newsletter',
  templateUrl: './subscribed-newsletter.component.html',
  styleUrls: ['./subscribed-newsletter.component.css']
})
export class SubscribedNewsletterComponent implements OnInit, OnDestroy {

  categoriesSubscribed = [];
  success: string = null;
  error: string = null;
  storeSub: Subscription;

  constructor(private store: Store<fromApp.AppState>,
              public snackBar: MatSnackBar) {}

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

  getCategories(): void {
    this.store.dispatch(new NewsletterActions.GetCategoriesSubscribed({ id: JSON.parse(localStorage.getItem('user')).id }));
  }

  showSuccessAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new NewsletterActions.ClearSuccess());
    this.store.dispatch(new NewsletterActions.GetCategoriesSubscribed({ id: JSON.parse(localStorage.getItem('user')).id }));
  }

  showErrorAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new NewsletterActions.ClearError());
  }

  ngOnDestroy() {
    if (this.storeSub) {
      this.storeSub.unsubscribe();
    }
  }

}
