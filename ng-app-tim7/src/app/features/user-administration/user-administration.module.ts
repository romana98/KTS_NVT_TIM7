import {NgModule} from '@angular/core';
import {AddAdministratorComponent} from './administrator-administration/view/add-administrator/add-administrator.component';
import {DashboardAdministratorComponent} from './administrator-administration/view/dashboard-administrator/dashboard-administrator.component';
import {EditProfileComponent} from './view/edit-profile/edit-profile.component';
import {ViewProfileComponent} from './view/view-profile/view-profile.component';
import {MaterialModule} from '../../shared/material.module';
import {SharedModule} from '../../shared/shared.module';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatCardModule} from '@angular/material/card';
import {EffectsModule} from '@ngrx/effects';
import {ActivateAccountEffects} from '../activate-account/store/activate-account.effects';
import {AdministratorEffects} from './administrator-administration/store/administrator.effects';
import {RegisteredEffects} from './registered-administration/store/registered.effects';

@NgModule({
  declarations: [AddAdministratorComponent, DashboardAdministratorComponent, EditProfileComponent, ViewProfileComponent],
  imports: [
    MaterialModule,
    SharedModule,
    MatProgressBarModule,
    MatCardModule
  ],
  providers: []
})
export class UserAdministrationModule { }
