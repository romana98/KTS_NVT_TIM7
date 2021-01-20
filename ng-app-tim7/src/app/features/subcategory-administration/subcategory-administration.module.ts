import {NgModule} from '@angular/core';
import {SubcategoryDashboardComponent} from './view/subcategory-dashboard/subcategory-dashboard.component';
import {MaterialModule} from '../../shared/material.module';
import {MatSelectModule} from '@angular/material/select';
import {SharedModule} from '../../shared/shared.module';
import {MatSelectInfiniteScrollModule} from 'ng-mat-select-infinite-scroll';
import {EffectsModule} from '@ngrx/effects';
import {ActivateAccountEffects} from '../activate-account/store/activate-account.effects';
import {SubcategoryEffects} from './store/subcategory.effects';

@NgModule({
  declarations: [SubcategoryDashboardComponent],
  imports: [
    MaterialModule,
    MatSelectModule,
    SharedModule,
    MatSelectInfiniteScrollModule
  ],
  providers: []
})
export class SubcategoryAdministrationModule { }
