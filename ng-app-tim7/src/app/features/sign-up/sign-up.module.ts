import {NgModule} from '@angular/core';
import {SignUpComponent} from './view/sign-up-view/sign-up.component';
import {MaterialModule} from '../../shared/material.module';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {EffectsModule} from '@ngrx/effects';
import {ActivateAccountEffects} from '../activate-account/store/activate-account.effects';
import {SignUpEffects} from './store/sign-up.effects';

@NgModule({
  declarations: [SignUpComponent],
  imports: [
    MaterialModule,
    MatProgressBarModule
  ],
  providers: []
})
export class SignUpModule { }
