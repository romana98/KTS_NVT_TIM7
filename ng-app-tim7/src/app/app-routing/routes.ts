import {Routes} from '@angular/router';
import {SignInGuard} from '../guards/sign-in.service';
import {SignInComponent} from '../components/sign-in/sign-in.component';
import {SignUpComponent} from '../components/sign-up/sign-up.component';
import {ActivateAccountComponent} from '../components/activate-account/activate-account.component';


export const routes: Routes = [
  {
    path: 'sign-in',
    component: SignInComponent,
    canActivate: [SignInGuard]
  },
  {
    path: 'sign-up',
    component: SignUpComponent,
    canActivate: [SignInGuard]
  },
  {
    path: 'activateAccount',
    component: ActivateAccountComponent,
    canActivate: [SignInGuard]
  }
];
