import {AfterContentInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import * as CulturalOfferActions from '../store/cultural-offer.actions';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import {Subscription} from 'rxjs';
import {CulturalofferModel} from '../../../models/culturaloffer.model';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-cultural-offer-addpage',
  templateUrl: './cultural-offer-addpage.component.html',
  styleUrls: ['./cultural-offer-addpage.component.css']
})
export class CulturalOfferAddpageComponent implements OnInit, AfterContentInit {

  success: string = null;
  error: string = null;
  private storeSub: Subscription;
  culturalOffer: CulturalofferModel = {
    id: -1,
    name: '',
    description: '',
    startDate: -1,
    endDate: -1,
    subcategory: -1,
    location: -1,
    subcategoryName: '',
    categoryName: '',
    category: -1,
    latitude: -1,
    longitude: -1,
    pictures: []
  } as CulturalofferModel;

  categoryForm = new FormGroup({
    name: new FormControl(''),
    category: new FormControl(''),
    subcategory: new FormControl(''),
    startDate: new FormControl(''),
    endDate: new FormControl(''),
    description: new FormControl(''),
  });

  constructor(private route: ActivatedRoute, private store: Store<fromApp.AppState>, private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    const offerId: number = +this.route.snapshot.paramMap.get('id');
    if (offerId !== 0){
      this.store.dispatch(new CulturalOfferActions.GetOneOfferAction(offerId));
      this.storeSub = this.store.select('culturaloffer').subscribe(state => {
        this.culturalOffer = state.culturalOffers;
        this.error = state.errorActionMessage;
        this.success = state.successActionMessage;

        if (this.error !== null){
          this.showErrorAlert(this.error);
        }

      });
    }

  }

  private showErrorAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new CulturalOfferActions.ClearAction());
  }

  ngAfterContentInit(): void {

  }



}
