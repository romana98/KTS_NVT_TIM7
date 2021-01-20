import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryDashboardComponent } from './category-dashboard.component';
import {Store, StoreModule} from '@ngrx/store';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import * as fromApp from '../../../../store/app.reducer';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDividerModule} from '@angular/material/divider';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {PaginationComponent} from '../../../../shared/pagination/pagination.component';
import {TableComponent} from '../../../../shared/table/table.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatTableModule} from '@angular/material/table';
import {of} from 'rxjs';
import * as CategoryActions from '../../store/category.actions';
import {CategoryModel} from '../../../../models/category.model';

describe('CategoryDashboardComponent', () => {
  let component: CategoryDashboardComponent;
  let fixture: ComponentFixture<CategoryDashboardComponent>;
  let store: Store;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CategoryDashboardComponent, PaginationComponent, TableComponent ],
      imports: [FormsModule, ReactiveFormsModule,  StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule, MatDividerModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatPaginatorModule, MatInputModule, MatTableModule ],
      providers: [Store]
    })
    .compileComponents();
    store = TestBed.inject(Store);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoryDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should subscribe to store in ngOnInit lifecycle', () => {
      const action = new CategoryActions.GetCategoriesPage({page: component.page, size: component.pageSize});
      const spy = spyOn(store, 'dispatch');
      component.ngOnInit();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.error).toBe(null);
    });
  });

  describe('ngOnDestroy()', () => {
    it('should unsubscribe to store in ngOnDestroy lifecycle', () => {

      component.storeSub = of(true).subscribe();

      component.ngOnDestroy();

      expect(component.storeSub.closed).toBeTruthy();
    });
  });

  describe('showErrorAlert()', () => {
    it('should dispatch ClearError action', () => {
      const action = new CategoryActions.ClearError();
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      component.showErrorAlert('error message');
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.snackBar.open).toHaveBeenCalledWith('error message', 'Ok', {duration: 3000});
      expect(component.error).toEqual(null);
    });
  });

  describe('showSuccessAlert()', () => {
    it('should dispatch ClearSuccess action', () => {
      const action = new CategoryActions.ClearSuccess();
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      component.showSuccessAlert('success message');
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.snackBar.open).toHaveBeenCalledWith('success message', 'Ok', {duration: 3000});
      expect(component.success).toEqual(null);
    });
  });

  describe('onPagination(page: number)', () => {
    it('should dispatch GetCategoriesPage action', () => {
      const action = new CategoryActions.GetCategoriesPage({page: 1, size: 10});
      const spy = spyOn(store, 'dispatch');

      component.onPagination(1);
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.page).toEqual(1);
    });
  });

  describe('addCategory()', () => {
    it('should dispatch AddCategory action', () => {
      component.form.controls.categoryNameInput.setValue('new category name');

      const action = new CategoryActions.AddCategory({name: component.form.value.categoryNameInput});
      const spy = spyOn(store, 'dispatch');

      component.addCategory();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('deleteCategory(id: number)', () => {
    it('should dispatch DeleteCategory action', () => {
      const action = new CategoryActions.DeleteCategory(1);
      const spy = spyOn(store, 'dispatch');

      component.deleteCategory(1);
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('editCategory()', () => {
    it('should dispatch EditCategory action', () => {
      component.formEdit.controls.categoryNameEdit.setValue('new category name');
      component.category = new CategoryModel(1, component.formEdit.value.categoryNameEdit);

      const action = new CategoryActions.EditCategory(component.category);
      const spy = spyOn(store, 'dispatch');

      component.editCategory();
      fixture.detectChanges();
      expect(component.isHidden).toEqual(true);
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('editModeOn(id: number)', () => {
    it('should dispatch GetCategory action', () => {
      const action = new CategoryActions.GetCategory(1);
      const spy = spyOn(store, 'dispatch');

      component.editModeOn(1);
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });
});
