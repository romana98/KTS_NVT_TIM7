import {Routes} from '@angular/router';
import {SignInGuard} from '../guards/sign-in.service';
import {SignInComponent} from '../features/sign-in/view/sign-in/sign-in.component';
import {SignUpComponent} from '../features/sign-up/view/sign-up-view/sign-up.component';
import {ActivateAccountComponent} from '../features/activate-account/components/activate-account-component/activate-account.component';
import {adminRoutes} from './administrator.routes';
import {regRoutes} from './registered.routes';
import {newsletterRoutes} from './newsletter.routes';
import {CulturalOfferMainpageComponent} from '../features/cultural-offer-administration/view/cultural-offer-mainpage/cultural-offer-mainpage.component';
import {CulturalOfferDetailedViewComponent} from '../features/cultural-offer-administration/view/cultural-offer-detailed-view/cultural-offer-detailed-view.component';

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
    component: CulturalOfferMainpageComponent
  },
  {
    path: 'detailed-cultural-offer',
    component: CulturalOfferDetailedViewComponent
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
