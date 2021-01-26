import {NgModule} from '@angular/core';
import {ActivateAccountComponent} from './components/activate-account-component/activate-account.component';
import {ActivateAccountEffects} from './store/activate-account.effects';
import {EffectsModule} from '@ngrx/effects';


@NgModule({
  declarations: [ActivateAccountComponent],
  imports: [],
  providers: []
})
export class ActivateAccountModule { }
