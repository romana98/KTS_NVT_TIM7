import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, FormGroupDirective, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {Store} from '@ngrx/store';
import {MatSnackBar} from '@angular/material/snack-bar';
import * as CategoryActions from '../store/category.actions';
import * as fromApp from '../../../store/app.reducer';
import {CategoryModel} from '../../../models/category.model';

@Component({
  selector: 'app-category-subcategory-dashboard',
  templateUrl: './category-dashboard.component.html',
  styleUrls: ['./category-dashboard.component.css']
})
export class CategoryDashboardComponent implements OnInit, OnDestroy {
  @ViewChild(FormGroupDirective) formGroupDirective: FormGroupDirective;
  page = 0;
  pageSize = 10;
  categories = {content: [], numberOfElements: 0, totalElements: 0, totalPages: 0, number: 0};
  category = null;
  success: string = null;
  error: string = null;
  form: FormGroup;
  formEdit: FormGroup;
  isHidden = true;
  storeSub: Subscription;
  constructor(
    private fb: FormBuilder,
    private store: Store<fromApp.AppState>,
    public snackBar: MatSnackBar
  ) {
    this.form = this.fb.group({
      categoryNameInput : [null, Validators.required]
    });
    this.formEdit = this.fb.group({
      categoryNameEdit : [null, Validators.required]
    });
  }
  ngOnInit(): void {
    this.store.dispatch(new CategoryActions.GetCategoriesPage({page: this.page, size: this.pageSize}));
    this.storeSub = this.store.select('category').subscribe(state => {
      this.categories = state.categories;
      this.category = state.category;
      this.formEdit.controls.categoryNameEdit.setValue(state.category.name);
      this.isHidden = state.category.id === -1;
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

  showSuccessAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new CategoryActions.ClearSuccess());
    this.store.dispatch(new CategoryActions.GetCategoriesPage({page: this.page, size: this.pageSize}));
    setTimeout(() => this.formGroupDirective.resetForm(), 0);
  }

  showErrorAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new CategoryActions.ClearError());
  }

  ngOnDestroy(): void {
    if (this.storeSub) {
      this.storeSub.unsubscribe();
    }
  }

  onPagination(page: number) {
    this.page = page;
    this.store.dispatch(new CategoryActions.GetCategoriesPage({page: this.page, size: this.pageSize }));
  }

  addCategory() {
    this.store.dispatch(new CategoryActions.AddCategory({name: this.form.value.categoryNameInput}));
  }

  deleteCategory(id: number) {
    this.store.dispatch(new CategoryActions.DeleteCategory(id));
  }

  editCategory() {
    this.isHidden = true;
    this.store.dispatch(new CategoryActions.EditCategory(new CategoryModel(this.category.id, this.formEdit.value.categoryNameEdit)));
  }

  editModeOn(id: number) {
    this.store.dispatch(new CategoryActions.GetCategory(id));
  }
}
