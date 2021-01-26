import {Routes} from '@angular/router';
import {DashboardAdministratorComponent} from '../features/user-administration/administrator-administration/view/dashboard-administrator/dashboard-administrator.component';
import {AdministratorGuard} from '../guards/administrator.service';
import {AddAdministratorComponent} from '../features/user-administration/administrator-administration/view/add-administrator/add-administrator.component';
import {ViewProfileComponent} from '../features/user-administration/view/view-profile/view-profile.component';
import {EditProfileComponent} from '../features/user-administration/view/edit-profile/edit-profile.component';
import {CategoryDashboardComponent} from '../features/category-administration/view/category-dashboard/category-dashboard.component';
import {SubcategoryDashboardComponent} from '../features/subcategory-administration/view/subcategory-dashboard/subcategory-dashboard.component';
import {CulturalOfferDashboardComponent} from '../features/cultural-offer-administration/view/cultural-offer-dashboard/cultural-offer-dashboard.component';
import {CulturalOfferAddpageComponent} from '../features/cultural-offer-administration/view/cultural-offer-addpage/cultural-offer-addpage.component';

export const adminRoutes: Routes = [
  {
    path: 'administrator/editCulturalOffer',
    component: CulturalOfferAddpageComponent,
  },
  {
    path: 'administrator/addCulturalOffer',
    component: CulturalOfferAddpageComponent,
  },
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
