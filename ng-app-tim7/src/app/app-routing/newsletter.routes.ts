import {Routes} from '@angular/router';
import {DashboardNewsletterComponent} from '../components/newsletter/dashboard-newsletter/dashboard-newsletter.component';
import {AdministratorGuard} from '../guards/administrator.service';
import {AddNewsletterComponent} from '../components/newsletter/add-newsletter/add-newsletter.component';
import {UpdateNewsletterComponent} from '../components/newsletter/update-newsletter/update-newsletter.component';
//import {ViewProfileComponent} from '../components/user/view-profile/view-profile.component';
//import {EditProfileComponent} from '../components/user/edit-profile/edit-profile.component';

export const newsletterRoutes: Routes = [
  {
    path: 'newsletter/dashboard',
    component: DashboardNewsletterComponent,
    canActivate: [AdministratorGuard]
  },
  {
    path: 'newsletter/add-newsletter',
    component: AddNewsletterComponent,
    canActivate: [AdministratorGuard]
  },
  {
    path: 'newsletter/update-newsletter/:id',
    component: UpdateNewsletterComponent,
    canActivate: [AdministratorGuard]
  },
];
