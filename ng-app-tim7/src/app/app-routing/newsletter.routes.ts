import {Routes} from '@angular/router';
import {DashboardNewsletterComponent} from '../components/newsletter/dashboard-newsletter/dashboard-newsletter.component';
import {AdministratorGuard} from '../guards/administrator.service';
//import {AddAdministratorComponent} from '../components/administrator/add-administrator/add-administrator.component';
//import {ViewProfileComponent} from '../components/user/view-profile/view-profile.component';
//import {EditProfileComponent} from '../components/user/edit-profile/edit-profile.component';

export const newsletterRoutes: Routes = [
  {
    path: 'newsletter/dashboard',
    component: DashboardNewsletterComponent,
    canActivate: [AdministratorGuard]
  }
];
