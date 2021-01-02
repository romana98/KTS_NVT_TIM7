import {Routes} from '@angular/router';
import {ViewProfileComponent} from '../components/user/view-profile/view-profile.component';
import {RegisteredGuard} from '../guards/registered.service';
import {EditProfileComponent} from '../components/user/edit-profile/edit-profile.component';

export const regRoutes: Routes = [

  {
    path: 'registered/view-profile',
    component: ViewProfileComponent,
    canActivate: [RegisteredGuard]
  },
  {
    path: 'registered/edit-profile',
    component: EditProfileComponent,
    canActivate: [RegisteredGuard]
  }
];
