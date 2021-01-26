import {NgModule} from '@angular/core';
import {SignInComponent} from './view/sign-in/sign-in.component';
import {MaterialModule} from '../../shared/material.module';
import {EffectsModule} from '@ngrx/effects';
import {ActivateAccountEffects} from '../activate-account/store/activate-account.effects';
import {AuthEffects} from './store/sign-in.effects';
import {SharedModule} from '../../shared/shared.module';
import {MatDividerModule} from '@angular/material/divider';
import {ReactiveFormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';

@NgModule({
  declarations: [SignInComponent],
  imports: [
    MaterialModule
  ],
  exports: [],
  providers: []
})
export class SignInModule { }
