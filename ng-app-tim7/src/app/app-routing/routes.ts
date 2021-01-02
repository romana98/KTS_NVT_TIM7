import {Routes} from '@angular/router';
import {SignInGuard} from '../guards/sign-in.service';
import {SignInComponent} from '../components/sign-in/sign-in.component';
import {SignUpComponent} from '../components/sign-up/sign-up.component';
import {ActivateAccountComponent} from '../components/activate-account/activate-account.component';
import {adminRoutes} from './administrator.routes';
import {regRoutes} from './registered.routes';
import {newsletterRoutes} from './newsletter.routes';


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
  },
  {
    path: '',
    children: adminRoutes
  },
  {
    path: '',
    children: regRoutes
  },
  {
    path: '',
    children: newsletterRoutes
  }
];
