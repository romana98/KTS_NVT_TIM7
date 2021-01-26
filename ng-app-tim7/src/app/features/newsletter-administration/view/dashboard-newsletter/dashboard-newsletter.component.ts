import {Component, OnDestroy, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../../store/app.reducer';
import * as NewsletterActions from '../../store/newsletter.actions';
import {Subscription} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';

@Component({
  selector: 'app-dashboard-newsletter',
  templateUrl: './dashboard-newsletter.component.html',
  styleUrls: ['./dashboard-newsletter.component.css']
})
export class DashboardNewsletterComponent implements OnInit, OnDestroy {

  page = 0;
  pageSize = 10;
  newsletters = {content: [], numberOfElements: 0, totalElements: 0, totalPages: 0, number: 0};
  success: string = null;
  error: string = null;
  storeSub: Subscription;
  constructor(private store: Store<fromApp.AppState>,
              public snackBar: MatSnackBar,
              private router: Router) {}

  ngOnInit(): void {
    this.store.dispatch(new NewsletterActions.GetNewsletterPage({ page: this.page, size: this.pageSize }));
    this.storeSub = this.store.select('newsletter').subscribe(state => {
      this.newsletters = state.newsletters;

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

  onDelete(id: number){
    this.store.dispatch(new NewsletterActions.DeleteNewsletter(id));
  }

  onClick(id: number) {
    this.router.navigate(['/newsletter/update-newsletter', id]);
  }

  onPagination(page: number){
    this.page = page;
    this.store.dispatch(new NewsletterActions.GetNewsletterPage({page: this.page, size: this.pageSize }));
  }

  showSuccessAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new NewsletterActions.ClearSuccess());
    this.store.dispatch(new NewsletterActions.GetNewsletterPage({page: this.page, size: this.pageSize }));
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
