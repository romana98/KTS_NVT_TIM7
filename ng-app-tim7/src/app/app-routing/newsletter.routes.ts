import {Routes} from '@angular/router';
import { AddNewsletterComponent } from '../features/newsletter-administration/view/add-newsletter/add-newsletter.component';
import {DashboardNewsletterComponent} from '../features/newsletter-administration/view/dashboard-newsletter/dashboard-newsletter.component';
import { SubscribedNewsletterComponent } from '../features/newsletter-administration/view/subscribed-newsletter/subscribed-newsletter.component';
import { UpdateNewsletterComponent } from '../features/newsletter-administration/view/update-newsletter/update-newsletter.component';
import {AdministratorGuard} from '../guards/administrator.service';
import { RegisteredGuard } from '../guards/registered.service';

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
  {
    path: 'newsletter/subscribed-newsletter',
    component: SubscribedNewsletterComponent,
    canActivate: [RegisteredGuard]
  },
];
