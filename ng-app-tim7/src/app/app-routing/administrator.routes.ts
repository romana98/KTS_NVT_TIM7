import {Routes} from '@angular/router';
import {DashboardAdministratorComponent} from '../components/administrator/dashboard-administrator/dashboard-administrator.component';
import {AdministratorGuard} from '../guards/administrator.service';

export const adminRoutes: Routes = [
  {
    path: 'administrator/dashboard',
    component: DashboardAdministratorComponent,
    canActivate: [AdministratorGuard]
  }
];
