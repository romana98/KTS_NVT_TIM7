import {Routes} from '@angular/router';
import {DashboardAdministratorComponent} from '../components/administrator/dashboard-administrator/dashboard-administrator.component';
import {AdministratorGuard} from '../guards/administrator.service';
import {AddAdministratorComponent} from '../components/administrator/add-administrator/add-administrator.component';

export const adminRoutes: Routes = [
  {
    path: 'administrator/dashboard',
    component: DashboardAdministratorComponent,
    canActivate: [AdministratorGuard]
  },
  {
    path: 'administrator/add-administrator',
    component: AddAdministratorComponent,
    canActivate: [AdministratorGuard]
  }
];
