import {Routes} from '@angular/router';
import {SignInComponent} from '../components/sign-in/sign-in.component';
import {SignUpComponent} from '../components/sign-up/sign-up.component';
import {SignInGuard} from '../guards/sign-in.service';


export const routes: Routes = [
  {
    path: 'sign-in',
    component: SignInComponent,
    canActivate: [SignInGuard]
  },
  {
    path: 'sign-up',
    component: SignUpComponent
  }
];
