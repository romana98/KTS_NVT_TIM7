import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavigationComponent } from './navigation/navigation.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import { StoreModule } from '@ngrx/store';
import { SignInComponent } from './components/sign-in/sign-in.component';
import {AppRoutingModule} from './app-routing/app-routing.module';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import {ReactiveFormsModule} from '@angular/forms';
import { EffectsModule } from '@ngrx/effects';
import * as fromApp from './store/app.reducer';
import { AuthEffects } from './components/sign-in/store/sign-in.effects';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MatDividerModule} from '@angular/material/divider';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { environment } from '../environments/environment';
import {MatIconModule} from '@angular/material/icon';
import { NavigationAdministratorComponent } from './navigation/navigation-administrator/navigation-administrator.component';
import { NavigationRegisteredComponent } from './navigation/navigation-registered/navigation-registered.component';
import { NavigationNonSignedInComponent } from './navigation/navigation-non-signed-in/navigation-non-signed-in.component';
import {metaReducers} from './store/app.reducer';
import {SignUpEffects} from './components/sign-up/store/sign-up.effects';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import { ActivateAccountComponent } from './components/activate-account/activate-account.component';
import {ActivateAccountEffects} from './components/activate-account/store/activate-account.effects';
import { TableComponent } from './components/common/table/table.component';
import {MatTableModule} from '@angular/material/table';
import { DashboardAdministratorComponent } from './components/administrator/dashboard-administrator/dashboard-administrator.component';
import {AdministratorEffects} from './components/administrator/store/administrator.effects';
import {HttpAuthInterceptor} from './interceptors/http-auth.interceptor';
import {MatPaginatorModule} from '@angular/material/paginator';
import { PaginationComponent } from './components/common/pagination/pagination.component';
import { AddAdministratorComponent } from './components/administrator/add-administrator/add-administrator.component';
import { ViewProfileComponent } from './components/user/view-profile/view-profile.component';
import {MatCardModule} from '@angular/material/card';
import {RegisteredEffects} from './components/registered/store/registered.effects';
import { EditProfileComponent } from './components/user/edit-profile/edit-profile.component';
import { DashboardNewsletterComponent } from './components/newsletter/dashboard-newsletter/dashboard-newsletter.component';
import {NewsletterEffects} from './components/newsletter/store/newsletter.effects';
import { CategoryDashboardComponent } from './components/category/category-dashboard/category-dashboard.component';
import {CategoryEffects} from './components/category/store/category.effects';
import { SubcategoryDashboardComponent } from './components/subcategory/subcategory-dashboard/subcategory-dashboard.component';
import {SubcategoryEffects} from './components/subcategory/store/subcategory.effects';
import {MatSelectModule} from '@angular/material/select';
import {MatSelectInfiniteScrollModule} from 'ng-mat-select-infinite-scroll';
import {ScrollingModule} from '@angular/cdk/scrolling';
import { UpdateNewsletterComponent } from './components/newsletter/update-newsletter/update-newsletter.component';
import { AddNewsletterComponent } from './components/newsletter/add-newsletter/add-newsletter.component';
import { CulturalOfferDashboardComponent } from './components/cultural-offer/cultural-offer-dashboard/cultural-offer-dashboard.component';



@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    SignInComponent,
    SignUpComponent,
    NavigationAdministratorComponent,
    NavigationRegisteredComponent,
    NavigationNonSignedInComponent,
    ActivateAccountComponent,
    TableComponent,
    DashboardAdministratorComponent,
    PaginationComponent,
    AddAdministratorComponent,
    ViewProfileComponent,
    EditProfileComponent,
    CategoryDashboardComponent,
    SubcategoryDashboardComponent,
    DashboardNewsletterComponent,
    AddNewsletterComponent,
    UpdateNewsletterComponent,
    CulturalOfferDashboardComponent
  ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        AppRoutingModule,
        StoreModule.forRoot(fromApp.appReducer, {metaReducers}),
        EffectsModule.forRoot([AuthEffects, SignUpEffects, ActivateAccountEffects, AdministratorEffects, RegisteredEffects,
            CategoryEffects, SubcategoryEffects, NewsletterEffects]),
        StoreDevtoolsModule.instrument({maxAge: 25, logOnly: environment.production}),
        ReactiveFormsModule,
        MatToolbarModule,
        MatDividerModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatSnackBarModule,
        MatIconModule,
        MatProgressBarModule,
        MatTableModule,
        MatPaginatorModule,
        MatCardModule,
        MatSelectModule,
        MatSelectInfiniteScrollModule,
        ScrollingModule
    ],
  providers: [{
    provide: HTTP_INTERCEPTORS, useClass: HttpAuthInterceptor, multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
