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
    DashboardNewsletterComponent,
  ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        AppRoutingModule,
        StoreModule.forRoot(fromApp.appReducer, {metaReducers}),
        EffectsModule.forRoot([AuthEffects, SignUpEffects, ActivateAccountEffects, AdministratorEffects, RegisteredEffects, NewsletterEffects]),
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
        MatCardModule
    ],
  providers: [{
    provide: HTTP_INTERCEPTORS, useClass: HttpAuthInterceptor, multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
