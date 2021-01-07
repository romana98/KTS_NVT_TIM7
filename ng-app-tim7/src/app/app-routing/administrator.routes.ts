import {Routes} from '@angular/router';
import {DashboardAdministratorComponent} from '../components/administrator/dashboard-administrator/dashboard-administrator.component';
import {AdministratorGuard} from '../guards/administrator.service';
import {AddAdministratorComponent} from '../components/administrator/add-administrator/add-administrator.component';
import {ViewProfileComponent} from '../components/user/view-profile/view-profile.component';
import {EditProfileComponent} from '../components/user/edit-profile/edit-profile.component';
import {CategoryDashboardComponent} from '../components/category/category-dashboard/category-dashboard.component';
import {SubcategoryDashboardComponent} from '../components/subcategory/subcategory-dashboard/subcategory-dashboard.component';
import {CulturalOfferDashboardComponent} from '../components/cultural-offer/cultural-offer-dashboard/cultural-offer-dashboard.component';

export const adminRoutes: Routes = [
  {
    path: 'administrator/culturalOfferDashboard',
    component: CulturalOfferDashboardComponent,
    canActivate: [AdministratorGuard]
  },
  {
    path: 'administrator/dashboard',
    component: DashboardAdministratorComponent,
    canActivate: [AdministratorGuard]
  },
  {
    path: 'administrator/add-administrator',
    component: AddAdministratorComponent,
    canActivate: [AdministratorGuard]
  },
  {
    path: 'administrator/view-profile',
    component: ViewProfileComponent,
    canActivate: [AdministratorGuard]
  },
  {
    path: 'administrator/edit-profile',
    component: EditProfileComponent,
    canActivate: [AdministratorGuard]
  },
  {
    path: 'administrator/manage-categories/dashboard',
    component: CategoryDashboardComponent,
    canActivate: [AdministratorGuard]
  },
  {
    path: 'administrator/manage-subcategories/dashboard',
    component: SubcategoryDashboardComponent,
    canActivate: [AdministratorGuard]
  }
];
