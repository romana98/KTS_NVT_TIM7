import {Routes} from '@angular/router';
import {ViewProfileComponent} from '../features/user-administration/view/view-profile/view-profile.component';
import {RegisteredGuard} from '../guards/registered.service';
import {EditProfileComponent} from '../features/user-administration/view/edit-profile/edit-profile.component';
import {CulturalOfferMainpageComponent} from '../features/cultural-offer-administration/view/cultural-offer-mainpage/cultural-offer-mainpage.component';

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
