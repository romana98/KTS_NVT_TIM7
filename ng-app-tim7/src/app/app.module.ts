import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {HttpAuthInterceptor} from './interceptors/http-auth.interceptor';
import {SharedModule} from './shared/shared.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {StoreModule} from '@ngrx/store';
import {metaReducers} from './store/app.reducer';
import * as fromApp from './store/app.reducer';
import {StoreDevtoolsModule} from '@ngrx/store-devtools';
import {environment} from '../environments/environment';
import {CategoryAdministrationModule} from './features/category-administration/category-administration.module';
import {ActivateAccountModule} from './features/activate-account/activate-account.module';
import {CulturalOfferAdministrationModule} from './features/cultural-offer-administration/cultural-offer-administration.module';
import {NewsletterAdministrationModule} from './features/newsletter-administration/newsletter-administration.module';
import {SignInModule} from './features/sign-in/sign-in.module';
import {SignUpModule} from './features/sign-up/sign-up.module';
import {SubcategoryAdministrationModule} from './features/subcategory-administration/subcategory-administration.module';
import {UserAdministrationModule} from './features/user-administration/user-administration.module';
import {NavigationModule} from './navigation/navigation.module';
import {EffectsModule} from '@ngrx/effects';
import {AuthEffects} from './features/sign-in/store/sign-in.effects';
import {NewsletterEffects} from './features/newsletter-administration/store/newsletter.effects';
import {SignUpEffects} from './features/sign-up/store/sign-up.effects';
import {ActivateAccountEffects} from './features/activate-account/store/activate-account.effects';
import {AdministratorEffects} from './features/user-administration/administrator-administration/store/administrator.effects';
import {RegisteredEffects} from './features/user-administration/registered-administration/store/registered.effects';
import {CategoryEffects} from './features/category-administration/store/category.effects';
import {SubcategoryEffects} from './features/subcategory-administration/store/subcategory.effects';
import {CulturalOfferEffects} from './features/cultural-offer-administration/store/cultural-offer.effects';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    NavigationModule,
    BrowserModule,
    BrowserAnimationsModule,
    SharedModule,
    ActivateAccountModule,
    CulturalOfferAdministrationModule,
    NewsletterAdministrationModule,
    SignInModule,
    SignUpModule,
    SubcategoryAdministrationModule,
    UserAdministrationModule,
    CategoryAdministrationModule,
    StoreModule.forRoot(fromApp.appReducer, {metaReducers}),
    EffectsModule.forRoot([AuthEffects, SignUpEffects, ActivateAccountEffects, AdministratorEffects, RegisteredEffects,
      CategoryEffects, SubcategoryEffects, NewsletterEffects, CulturalOfferEffects]),
    StoreDevtoolsModule.instrument({maxAge: 25, logOnly: environment.production}),
    StoreDevtoolsModule.instrument({maxAge: 25, logOnly: environment.production})
  ],
  exports: [],
  providers: [{
    provide: HTTP_INTERCEPTORS, useClass: HttpAuthInterceptor, multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
