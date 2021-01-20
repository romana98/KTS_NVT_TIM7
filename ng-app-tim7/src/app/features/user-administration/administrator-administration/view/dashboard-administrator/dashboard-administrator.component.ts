import {Component, OnDestroy, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../../../store/app.reducer';
import * as AdminActions from '../../store/administrator.actions';
import {Subscription} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-dashboard-administrator',
  templateUrl: './dashboard-administrator.component.html',
  styleUrls: ['./dashboard-administrator.component.css']
})
export class DashboardAdministratorComponent implements OnInit, OnDestroy {
  page = 0;
  pageSize = 10;
  admins = {content: [], numberOfElements: 0, totalElements: 0, totalPages: 0, number: 0};
  success: string = null;
  error: string = null;
  storeSub: Subscription;
  constructor(private store: Store<fromApp.AppState>,
              public snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.store.dispatch(new AdminActions.GetAdminPage({ page: this.page, size: this.pageSize }));
    this.storeSub = this.store.select('administrator').subscribe(state => {
      this.admins = state.admins;

      this.success = state.success;
      this.error = state.error;
      if (this.success) {
        this.showSuccessAlert(this.success);
      }
      if (this.error) {
        this.showErrorAlert(this.error);
      }
    });
  }
  onDelete(id: number){
    this.store.dispatch(new AdminActions.DeleteAdmin(id));
  }
  onPagination(page: number){
    this.page = page;
    this.store.dispatch(new AdminActions.GetAdminPage({page: this.page, size: this.pageSize }));
  }

  showSuccessAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 2000 });
    this.store.dispatch(new AdminActions.ClearSuccess());
    this.store.dispatch(new AdminActions.GetAdminPage({page: this.page, size: this.pageSize }));
  }

  showErrorAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 2000 });
    this.store.dispatch(new AdminActions.ClearError());
  }

  ngOnDestroy() {
    if (this.storeSub) {
      this.storeSub.unsubscribe();
    }
  }
}
