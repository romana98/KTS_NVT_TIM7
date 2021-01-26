import {Component, OnDestroy, OnInit} from '@angular/core';
import {CulturalofferModel} from '../../../../models/culturaloffer.model';
import {Subscription} from 'rxjs';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../../store/app.reducer';
import {MatSnackBar} from '@angular/material/snack-bar';
import * as CulturalOfferActions from '../../store/cultural-offer.actions';
import {Router} from '@angular/router';

@Component({
  selector: 'app-cultural-offer-mainpage',
  templateUrl: './cultural-offer-mainpage.component.html',
  styleUrls: ['./cultural-offer-mainpage.component.css']
})
export class CulturalOfferMainpageComponent implements OnInit, OnDestroy {

  page = 0;
  pageSize = 10;
  getResponse = {content: [], numberOfElements: 0, totalElements: 0, totalPages: 0, number: 0};
  culturalOffers: CulturalofferModel[];
  success: string = null;
  error: string = null;
  storeSub: Subscription;

  offerClicked: CulturalofferModel = null;
  offerDetailed: CulturalofferModel = null;

  filterType: string;

  constructor(private store: Store<fromApp.AppState>,
              public snackBar: MatSnackBar, private router: Router) {}

  ngOnInit(): void {
    this.filterType = 'all';
    this.store.dispatch(new CulturalOfferActions.GetCulturalOfferPage({ page: this.page, size: this.pageSize }));
    this.storeSub = this.store.select('culturaloffer').subscribe(state => {
      this.getResponse = state.culturalOffers;
      this.culturalOffers = state.culturalOffers.content;
      this.offerClicked = state.culturalOffers.content[0];
      this.offerDetailed = state.culturalOffers.content[0];
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

  onDelete(id: number){
    this.store.dispatch(new CulturalOfferActions.DeleteCulturalOffer({id}));
  }

  onPagination(page: number){
    this.page = page;
    this.store.dispatch(new CulturalOfferActions.GetCulturalOfferPage({page: this.page, size: this.pageSize }));
  }

  showSuccessAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new CulturalOfferActions.ClearAction());
    this.store.dispatch(new CulturalOfferActions.GetCulturalOfferPage({page: this.page, size: this.pageSize }));
  }

  showErrorAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new CulturalOfferActions.ClearAction());
  }

  onFilterChanged(newValue: any){
      this.store.dispatch(new CulturalOfferActions.FilterCulturalOffersAction(
        {page: this.page, page_size: this.pageSize, parameter: this.filterType, value: newValue.value}));
  }

  onRowClick(offerId: number){
    const result = this.culturalOffers.filter( value => value.id === offerId );
    this.offerClicked = result[0];
  }

  ngOnDestroy() {
    if (this.storeSub) {
      this.storeSub.unsubscribe();
    }
  }

  goToDetailed(offerId: number) {
    const result = this.culturalOffers.filter( value => value.id === offerId );
    this.offerDetailed = result[0];
    this.store.dispatch(new CulturalOfferActions.GoToDetailed(this.offerDetailed));
    this.router.navigate(['/detailed-cultural-offer'], {queryParams: {offer_id: offerId}});
  }
}
