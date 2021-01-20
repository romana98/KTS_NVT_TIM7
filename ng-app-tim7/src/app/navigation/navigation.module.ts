import {NgModule} from '@angular/core';
import {NavigationComponent} from './navigation.component';
import {NavigationAdministratorComponent} from './navigation-administrator/navigation-administrator.component';
import {NavigationRegisteredComponent} from './navigation-registered/navigation-registered.component';
import {NavigationNonSignedInComponent} from './navigation-non-signed-in/navigation-non-signed-in.component';
import {MaterialModule} from '../shared/material.module';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {AppRoutingModule} from '../app-routing/app-routing.module';

@NgModule({
  declarations: [
    NavigationComponent,
    NavigationAdministratorComponent,
    NavigationRegisteredComponent,
    NavigationNonSignedInComponent
  ],
  imports: [
    AppRoutingModule,
    MaterialModule,
    MatToolbarModule,
    MatIconModule
  ],
  exports: [NavigationComponent,
    NavigationAdministratorComponent,
    NavigationRegisteredComponent,
    NavigationNonSignedInComponent,
    AppRoutingModule
  ],
  providers: []
})
export class NavigationModule { }
