import { Input } from '@angular/core';
import {Component, OnDestroy, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import * as NewsletterActions from '../store/newsletter.actions';
import {Subscription} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from "@angular/router";

@Component({
  selector: 'app-category-newsletter',
  templateUrl: './category-newsletter.component.html',
  styleUrls: ['./category-newsletter.component.css']
})
export class CategoryNewsletterComponent implements OnInit, OnDestroy {

  @Input() category: any;
  page = 0;
  pageSize = 5;
  newslettersSubscribed = {content: [], numberOfElements: 0, totalElements: 0, totalPages: 0, number: 0};
  success: string = null;
  error: string = null;
  private storeSub: Subscription;
  
  constructor(private store: Store<fromApp.AppState>,
    private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.store.dispatch(new NewsletterActions.GetNewslettersSubscribed({ page: this.page, size: this.pageSize, catId: this.category.id, id: JSON.parse(localStorage.getItem('user')).id }));
    this.storeSub = this.store.select('newsletter').subscribe(state => {
      this.newslettersSubscribed = state.newslettersSubscribed;

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

  onPagination(page: number){
    this.page = page;
    this.store.dispatch(new NewsletterActions.GetNewslettersSubscribed({ page: this.page, size: this.pageSize, catId: this.category.id, id: JSON.parse(localStorage.getItem('user')).id }));
  }

  unsubscribe(idOffer: number) {
    this.store.dispatch(new NewsletterActions.Unsubscribe({ idOffer: idOffer, idUser: JSON.parse(localStorage.getItem('user')).id }));
    this.store.dispatch(new NewsletterActions.GetNewslettersSubscribed({ page: this.page, size: this.pageSize, catId: this.category.id, id: JSON.parse(localStorage.getItem('user')).id }));
  }

  private showSuccessAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new NewsletterActions.ClearSuccess());
    this.store.dispatch(new NewsletterActions.GetNewslettersSubscribed({ page: this.page, size: this.pageSize, catId: this.category.id, id: JSON.parse(localStorage.getItem('user')).id }));
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
