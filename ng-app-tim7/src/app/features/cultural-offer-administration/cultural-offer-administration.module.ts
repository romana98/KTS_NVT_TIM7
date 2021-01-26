import {NgModule} from '@angular/core';
import {CulturalOfferMainpageComponent} from './view/cultural-offer-mainpage/cultural-offer-mainpage.component';
import {MatSelectModule} from '@angular/material/select';
import {SharedModule} from '../../shared/shared.module';
import {MaterialModule} from '../../shared/material.module';
import {CulturalOfferDetailedViewComponent} from './view/cultural-offer-detailed-view/cultural-offer-detailed-view.component';
import {CulturalOfferDashboardComponent} from './view/cultural-offer-dashboard/cultural-offer-dashboard.component';
import {CulturalOfferAddpageComponent} from './view/cultural-offer-addpage/cultural-offer-addpage.component';
import {MatIconModule} from '@angular/material/icon';
import {NgxStarRatingModule} from 'ngx-star-rating';
import {FormsModule} from '@angular/forms';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatSelectInfiniteScrollModule} from 'ng-mat-select-infinite-scroll';
import {EffectsModule} from '@ngrx/effects';
import {ActivateAccountEffects} from '../activate-account/store/activate-account.effects';
import {CulturalOfferEffects} from './store/cultural-offer.effects';
import {MatNativeDateModule} from '@angular/material/core';

@NgModule({
  declarations: [
    CulturalOfferMainpageComponent,
    CulturalOfferDetailedViewComponent,
    CulturalOfferDashboardComponent,
    CulturalOfferAddpageComponent
  ],
  imports: [
    SharedModule,
    MaterialModule,
    MatSelectModule,
    MatIconModule,
    NgxStarRatingModule,
    FormsModule,
    MatDatepickerModule,
    MatSelectInfiniteScrollModule,
    MatNativeDateModule
  ],
  providers: []
})
export class CulturalOfferAdministrationModule { }
