import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import {MatSnackBar} from '@angular/material/snack-bar';
import * as CulturalOfferActions from '../../cultural-offer/store/cultural-offer.actions';
import {CulturalofferModel} from '../../../models/culturaloffer.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-cultural-offer-dashboard',
  templateUrl: './cultural-offer-dashboard.component.html',
  styleUrls: ['./cultural-offer-dashboard.component.css']
})
export class CulturalOfferDashboardComponent implements OnInit, OnDestroy{

  page = 0;
  pageSize = 10;
  getResponse = {content: [], numberOfElements: 0, totalElements: 0, totalPages: 0, number: 0};
  culturalOffers: CulturalofferModel[];
  success: string = null;
  error: string = null;
  private storeSub: Subscription;

  constructor(private router: Router, private store: Store<fromApp.AppState>,
              private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.store.dispatch(new CulturalOfferActions.GetCulturalOfferPage({ page: this.page, size: this.pageSize }));
    this.storeSub = this.store.select('culturaloffer').subscribe(state => {
      this.getResponse = state.culturalOffers;
      this.culturalOffers = state.culturalOffers.content;
      this.error = state.errorActionMessage;
      this.success = state.successActionMessage;

      if (this.error !== null){
        this.showErrorAlert(this.error);
      }

      if (this.success !== null){
        this.showSuccessAlert(this.success);
      }

    });

  }
  onClick(id: number){
    this.router.navigate(['administrator/editCulturalOffer', { id: id }]);
  }

  onDelete(id: number){
    this.store.dispatch(new CulturalOfferActions.DeleteCulturalOffer({id: id, page: this.page, page_size: this.pageSize}));
  }

  onPagination(page: number){
    this.page = page;
    this.store.dispatch(new CulturalOfferActions.GetCulturalOfferPage({page: this.page, size: this.pageSize }));
  }

  private showSuccessAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new CulturalOfferActions.ClearAction());
    this.store.dispatch(new CulturalOfferActions.GetCulturalOfferPage({page: this.page, size: this.pageSize }));
  }

  private showErrorAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new CulturalOfferActions.ClearAction());
  }

  ngOnDestroy() {
    if (this.storeSub) {
      this.storeSub.unsubscribe();
    }
  }

}
