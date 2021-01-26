import {NgModule} from '@angular/core';
import {DialogNewsletterComponent} from './components/dialog-newsletter/dialog-newsletter.component';
import {CategoryNewsletterComponent} from './components/category-newsletter/category-newsletter.component';
import {CardNewsletterComponent} from './components/card-newsletter/card-newsletter.component';
import {MaterialModule} from '../../shared/material.module';
import {MatDialogModule} from '@angular/material/dialog';
import {SharedModule} from '../../shared/shared.module';
import {MatCardModule} from '@angular/material/card';
import {EffectsModule} from '@ngrx/effects';
import {ActivateAccountEffects} from '../activate-account/store/activate-account.effects';
import {NewsletterEffects} from './store/newsletter.effects';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatDividerModule} from '@angular/material/divider';
import {AddNewsletterComponent} from './view/add-newsletter/add-newsletter.component';
import {UpdateNewsletterComponent} from './view/update-newsletter/update-newsletter.component';
import {DashboardNewsletterComponent} from './view/dashboard-newsletter/dashboard-newsletter.component';
import {SubscribedNewsletterComponent} from './view/subscribed-newsletter/subscribed-newsletter.component';
import {MatSelectModule} from '@angular/material/select';
import {MatIconModule} from '@angular/material/icon';
import {MatSelectInfiniteScrollModule} from 'ng-mat-select-infinite-scroll';
import {MatTabsModule} from '@angular/material/tabs';

@NgModule({
  declarations: [
    DialogNewsletterComponent,
    CategoryNewsletterComponent,
    CardNewsletterComponent,
    AddNewsletterComponent,
    UpdateNewsletterComponent,
    DashboardNewsletterComponent,
    SubscribedNewsletterComponent
  ],
  imports: [
    MatSelectInfiniteScrollModule,
    MatProgressBarModule,
    MaterialModule,
    MatDialogModule,
    SharedModule,
    MatCardModule,
    MatSelectModule,
    MatIconModule,
    MatTabsModule
  ],
  providers: []
})
export class NewsletterAdministrationModule { }
