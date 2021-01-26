import {NgModule} from '@angular/core';
import {CategoryDashboardComponent} from './view/category-dashboard/category-dashboard.component';
import {MaterialModule} from '../../shared/material.module';
import {SharedModule} from '../../shared/shared.module';
import {EffectsModule} from '@ngrx/effects';
import {ActivateAccountEffects} from '../activate-account/store/activate-account.effects';
import {CategoryEffects} from './store/category.effects';

@NgModule({
  declarations: [CategoryDashboardComponent],
  imports: [
    MaterialModule,
    SharedModule
  ],
  providers: [],
  exports: [CategoryDashboardComponent]
})
export class CategoryAdministrationModule { }
