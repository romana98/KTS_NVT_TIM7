import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubcategoryDashboardComponent } from './subcategory-dashboard.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Store, StoreModule} from '@ngrx/store';
import * as fromApp from '../../../../store/app.reducer';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDividerModule} from '@angular/material/divider';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatInputModule} from '@angular/material/input';
import {MatTableModule} from '@angular/material/table';
import {MatSelectModule} from '@angular/material/select';
import {TableComponent} from '../../../../shared/table/table.component';
import {PaginationComponent} from '../../../../shared/pagination/pagination.component';
import {of} from 'rxjs';
import * as SubcategoryActions from '../../store/subcategory.actions';
import {SubcategoryModel} from '../../../../models/subcategory.model';

describe('SubcategoryDashboardComponent', () => {
  let component: SubcategoryDashboardComponent;
  let fixture: ComponentFixture<SubcategoryDashboardComponent>;
  let store: Store;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubcategoryDashboardComponent, TableComponent, PaginationComponent ],
      imports: [FormsModule, ReactiveFormsModule,  StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule, MatDividerModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatPaginatorModule, MatInputModule, MatTableModule, MatSelectModule ],
      providers: [Store]
    })
    .compileComponents();
    store = TestBed.inject(Store);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubcategoryDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  describe('ngOnInit()', () => {
    it('should subscribe to store in ngOnInit lifecycle', () => {
      const action1 = new SubcategoryActions.GetSubcategoriesPage({page: component.page, size: component.pageSize});
      const action2 = new SubcategoryActions.GetCategoriesPage({page: component.pageCategory, size: component.pageSizeCategories});
      const spy = spyOn(store, 'dispatch');
      component.ngOnInit();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action1);
      expect(spy).toHaveBeenCalledWith(action2);
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
      const action = new SubcategoryActions.ClearError();
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
      const action = new SubcategoryActions.ClearSuccess();
      const action2 = new SubcategoryActions.GetSubcategoriesPage({page: 0, size: 10});
      const action3 = new SubcategoryActions.GetCategoriesPage({page: 0, size: 10});
      const spy = spyOn(store, 'dispatch');
      spyOn(component.snackBar, 'open');

      component.showSuccessAlert('success message');
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(spy).toHaveBeenCalledWith(action2);
      expect(spy).toHaveBeenCalledWith(action3);
      expect(component.snackBar.open).toHaveBeenCalledWith('success message', 'Ok', {duration: 3000});
      expect(component.success).toEqual(null);
    });
  });
  describe('onPagination(page: number)', () => {
    it('should dispatch GetSubcategoriesPage action', () => {
      const action = new SubcategoryActions.GetSubcategoriesPage({page: 1, size: 10});
      const spy = spyOn(store, 'dispatch');

      component.onPagination(1);
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.page).toEqual(1);
    });
  });
  describe('editModeOn(id: number)', () => {
    it('should dispatch GetSubcategory action', () => {
      const action = new SubcategoryActions.GetSubcategory(1);
      const spy = spyOn(store, 'dispatch');

      component.editModeOn(1);
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });
  describe('deleteSubcategory(id: number)', () => {
    it('should dispatch DeleteSubcategory action', () => {
      const action = new SubcategoryActions.DeleteSubcategory(1);
      const spy = spyOn(store, 'dispatch');

      component.deleteSubcategory(1);
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });
  describe('editSubcategory()', () => {
    it('should dispatch EditSubcategory action', () => {
      component.formEdit.controls.subcategoryNameEdit.setValue('new subcategory name');
      component.formEdit.controls.categoryName.setValue('category');
      component.subcategory = new SubcategoryModel(
        1, component.formEdit.value.subcategoryNameEdit, 1, component.formEdit.value.categoryName);

      const action = new SubcategoryActions.EditSubcategory(component.subcategory);
      const spy = spyOn(store, 'dispatch');

      component.editSubcategory();
      fixture.detectChanges();
      expect(component.isHidden).toEqual(true);
      expect(spy).toHaveBeenCalledWith(action);
    });
  });
  describe('addSubcategory()', () => {
    it('should dispatch AddCategory action', () => {
      component.form.controls.subcategoryNameInput.setValue('new subcategory name');
      component.form.controls.categoryNameSelect.setValue('category');

      const action = new SubcategoryActions.AddSubcategory({
        name: component.form.value.subcategoryNameInput, categoryName: component.form.value.categoryNameSelect});
      const spy = spyOn(store, 'dispatch');

      component.addSubcategory();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });
  describe('getNextBatch()', () => {
    it('should dispatch GetCategoriesPage action', () => {
      component.pageCategory = 1;
      const action = new SubcategoryActions.GetCategoriesPage({page: component.pageCategory + 1, size: 10});
      const spy = spyOn(store, 'dispatch');

      component.getNextBatch();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
      expect(component.pageCategory).toEqual(2);
    });
  });
});
