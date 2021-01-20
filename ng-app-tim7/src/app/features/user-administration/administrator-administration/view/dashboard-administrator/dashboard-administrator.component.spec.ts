import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardAdministratorComponent } from './dashboard-administrator.component';
import {Store, StoreModule} from '@ngrx/store';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import * as fromApp from '../../../../../store/app.reducer';
import {MatDividerModule} from '@angular/material/divider';
import {TableComponent} from '../../../../../shared/table/table.component';
import {PaginationComponent} from '../../../../../shared/pagination/pagination.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatTableModule} from '@angular/material/table';
import * as AdminActions from '../../store/administrator.actions';
import {of} from 'rxjs';

describe('DashboardAdministratorComponent', () => {
  let component: DashboardAdministratorComponent;
  let fixture: ComponentFixture<DashboardAdministratorComponent>;
  let store: Store;

  beforeEach(async(() => {

    TestBed.configureTestingModule({
      declarations: [ DashboardAdministratorComponent, TableComponent, PaginationComponent ],
      imports: [StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule, MatDividerModule, MatPaginatorModule, MatTableModule],
      providers: [Store]
    })
    .compileComponents();

    store = TestBed.inject(Store);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardAdministratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should subscribe to store in ngOnInit lifecycle', () => {
      const action = new AdminActions.GetAdminPage({ page: component.page, size: component.pageSize });
      const spy = spyOn(store, 'dispatch');
      component.ngOnInit();
      fixture.detectChanges();
      expect(component.error).toBe(null);
      expect(component.success).toBe(null);
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('onDelete()', () => {
    it('should dispatch DeleteAdmin action', () => {
      const id = 0;
      const action = new AdminActions.DeleteAdmin(id);
      const spy = spyOn(store, 'dispatch');

      component.onDelete(id);
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('onPagination()', () => {
    it('should change page and dispatch GetAdminPage action', () => {
      const page = 1;
      // component.page = page;
      const action = new AdminActions.GetAdminPage({ page, size: component.pageSize });
      const spy = spyOn(store, 'dispatch');

      component.onPagination(page);
      fixture.detectChanges();
      expect(component.page).toEqual(page);
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('showErrorAlert()', () => {
    it('should dispatch ClearError action', () => {
      const action = new AdminActions.ClearError();
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      component.showErrorAlert('error message');
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.snackBar.open).toHaveBeenCalledWith('error message', 'Ok', {duration: 2000});
      expect(component.error).toEqual(null);
    });
  });

  describe('showSuccessAlert()', () => {
    it('should dispatch ClearSuccess action', () => {
      const action = new AdminActions.ClearSuccess();
      const actionPage = new AdminActions.GetAdminPage({ page: component.page, size: component.pageSize });
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      component.showSuccessAlert('success message');
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(spy).toHaveBeenCalledWith(actionPage);
      expect(component.snackBar.open).toHaveBeenCalledWith('success message', 'Ok', {duration: 2000});
      expect(component.success).toEqual(null);
    });
  });

  describe('ngOnDestroy()', () => {
    it('should unsubscribe to store in ngOnDestroy lifecycle', () => {

      component.storeSub = of(true).subscribe();

      component.ngOnDestroy();

      expect(component.storeSub.closed).toBeTruthy();
    });
  });
});
