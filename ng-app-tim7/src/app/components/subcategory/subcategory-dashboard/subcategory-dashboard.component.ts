import {AfterViewInit, Component, HostListener, NgZone, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, FormGroupDirective, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import {MatSnackBar} from '@angular/material/snack-bar';
import * as SubcategoryActions from '../../subcategory/store/subcategory.actions';
import {SubcategoryModel} from '../../../models/subcategory.model';
// import {CdkVirtualScrollViewport} from '@angular/cdk/scrolling';

@Component({
  selector: 'app-subcategory-dashboard',
  templateUrl: './subcategory-dashboard.component.html',
  styleUrls: ['./subcategory-dashboard.component.css']
})
export class SubcategoryDashboardComponent implements OnInit, OnDestroy {
  @ViewChild(FormGroupDirective) formGroupDirective: FormGroupDirective;
  // @ViewChild('scroller') scroller: CdkVirtualScrollViewport;
  page = 0;
  pageSize = 15;
  pageSizeCategories = 10;
  pageCategory = 0;
  subcategories = {content: [], numberOfElements: 0, totalElements: 0, totalPages: 0, number: 0};
  subcategory = null;
  success: string = null;
  error: string = null;
  form: FormGroup;
  formEdit: FormGroup;
  isHidden = true;
  private storeSub: Subscription;
  categories = {content: [], numberOfElements: 0, totalElements: 0, totalPages: 0, number: 0};
  constructor(
    private zone: NgZone,
    private fb: FormBuilder,
    private store: Store<fromApp.AppState>,
    private snackBar: MatSnackBar
  ) {
    this.form = this.fb.group({
      subcategoryNameInput : [null, Validators.required],
      categoryNameSelect : [null, Validators.required]
    });
    this.formEdit = this.fb.group({
      categoryName : [null, Validators.required],
      subcategoryNameEdit : [null, Validators.required]
    });
  }
  ngOnInit(): void {
    this.store.dispatch(new SubcategoryActions.GetSubcategoriesPage({page: this.page, size: this.pageSize}));
    this.store.dispatch(new SubcategoryActions.GetCategoriesPage({page: this.pageCategory, size: this.pageSizeCategories}));
    this.storeSub = this.store.select('subcategory').subscribe(state => {
      this.subcategories = state.subcategories;
      this.subcategory = state.subcategory;
      this.categories = state.categoriesSelect;
      this.formEdit.controls.categoryName.setValue(state.subcategory.categoryName);
      this.formEdit.controls.subcategoryNameEdit.setValue(state.subcategory.name);
      this.isHidden = state.subcategory.id === -1;
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

  private showSuccessAlert(message: string) {
    this.pageCategory = 0;
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new SubcategoryActions.ClearSuccess());
    this.store.dispatch(new SubcategoryActions.GetSubcategoriesPage({page: this.page, size: this.pageSize}));
    this.store.dispatch(new SubcategoryActions.GetCategoriesPage({page: this.pageCategory, size: this.pageSizeCategories}));
    setTimeout(() => this.formGroupDirective.resetForm(), 0);
  }

  private showErrorAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new SubcategoryActions.ClearError());
  }

  ngOnDestroy(): void {
    if (this.storeSub) {
      this.storeSub.unsubscribe();
    }
  }

  onPagination(page: number) {
    this.page = page;
    this.store.dispatch(new SubcategoryActions.GetSubcategoriesPage({page: this.page, size: this.pageSize }));
  }

  editModeOn(id: number) {
    this.store.dispatch(new SubcategoryActions.GetSubcategory(id));
  }

  deleteSubcategory(id: number) {
    this.store.dispatch(new SubcategoryActions.DeleteSubcategory(id));
  }

  editSubcategory() {
    this.isHidden = true;
    this.store.dispatch(new SubcategoryActions.EditSubcategory(new SubcategoryModel(this.subcategory.id,
      this.formEdit.value.subcategoryNameEdit,
      this.subcategory.categoryId, this.subcategory.categoryName)));
  }

  addSubcategory() {
    console.log(this.form.value.categoryNameSelect);
    this.store.dispatch(new SubcategoryActions.AddSubcategory({name: this.form.value.subcategoryNameInput,
      categoryName: this.form.value.categoryNameSelect}));
  }
  getNextBatch() {
    this.pageCategory++;
    this.store.dispatch(new SubcategoryActions.GetCategoriesPage({page: this.pageCategory, size: this.pageSizeCategories}));
  }
}
