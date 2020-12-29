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
import {HttpClientModule} from '@angular/common/http';
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
@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    SignInComponent,
    SignUpComponent,
    NavigationAdministratorComponent,
    NavigationRegisteredComponent,
    NavigationNonSignedInComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    StoreModule.forRoot(fromApp.appReducer, {metaReducers}),
    EffectsModule.forRoot([AuthEffects]),
    StoreDevtoolsModule.instrument({maxAge: 25, logOnly: environment.production}),
    ReactiveFormsModule,
    MatToolbarModule,
    MatDividerModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule,
    MatIconModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
