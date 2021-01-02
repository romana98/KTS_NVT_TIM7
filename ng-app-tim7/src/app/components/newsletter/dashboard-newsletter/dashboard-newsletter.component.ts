import {Component, OnDestroy, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import * as NewsletterActions from '../store/newsletter.actions';
import {Subscription} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-dashboard-newsletter',
  templateUrl: './dashboard-newsletter.component.html',
  styleUrls: ['./dashboard-newsletter.component.css']
})
export class DashboardNewsletterComponent implements OnInit, OnDestroy {

  page = 0;
  pageSize = 15;
  newsletters = {content: [], numberOfElements: 0, totalElements: 0, totalPages: 0, number: 0};
  success: string = null;
  error: string = null;
  private storeSub: Subscription;
  constructor(private store: Store<fromApp.AppState>,
              private snackBar: MatSnackBar) {}

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
    //this.store.dispatch(new AdminActions.DeleteAdmin(id));
  }
  onPagination(page: number){
    this.page = page;
    this.store.dispatch(new NewsletterActions.GetNewsletterPage({page: this.page, size: this.pageSize }));
  }

  private showSuccessAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new NewsletterActions.ClearSuccess());
    this.store.dispatch(new NewsletterActions.GetNewsletterPage({page: this.page, size: this.pageSize }));
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
