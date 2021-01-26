import { Injectable } from '@angular/core';
import { Actions, ofType, Effect } from '@ngrx/effects';
import {switchMap, catchError, map} from 'rxjs/operators';
import { of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import * as CategoryActions from './category.actions';
import {CategoryModel} from '../../../models/category.model';

const handleSuccess = (type: string) => {
  let message = '';
  if (type === 'add'){
    message = 'Category added successfully.';
  }else if (type === 'delete'){
    message = 'Category deleted successfully.';
  }else{
    message = 'Category updated successfully.';
    return new CategoryActions.EditCategorySuccess(message);
  }
  return new CategoryActions.CategorySuccess(message);
};

const handleError = (errorRes: any, type: string) => {
  let errorMessage = '';
  if (type === 'add'){
    errorMessage = 'Category with that name already exists!';
  }else if (type === 'edit'){
    errorMessage = 'Category with that name already exists!';
  }else if (type === 'delete'){
    errorMessage = 'Deleting failed! Check if selected category has subcategories!';
  }else if (type === 'edit'){
    errorMessage = 'Editing failed! Category name already exists!';
  }
  else{ // get All
    errorMessage = 'An unknown error has occurred!';
  }
  return of(new CategoryActions.CategoryFail(errorMessage));
};

@Injectable()
export class CategoryEffects{
  @Effect()
  categories = this.actions$.pipe(
    ofType(CategoryActions.GET_CATEGORIES_PAGE),
    switchMap((data: CategoryActions.GetCategoriesPage) => {
      return this.http
        .get(
          'http://localhost:8080/categories/by-page?page=' + data.payload.page + '&size=' + data.payload.size
        )
        .pipe(
          map(dataRes => {
            return new CategoryActions.GetCategoriesByPageSuccess(dataRes);
          }),
          catchError(errorRes => {
            return handleError(errorRes, 'getAll');
          })
        );
    })
  );
  @Effect()
  add = this.actions$.pipe(
    ofType(CategoryActions.ADD_CATEGORY),
    switchMap((category: CategoryActions.AddCategory) => {
      return this.http
        .post<CategoryModel>(
          'http://localhost:8080/categories',
          {
            name: category.payload.name
          }
        )
        .pipe(
          map(() => {
            return handleSuccess('add');
          }),
          catchError(errorRes => {
            return handleError(errorRes, 'add');
          })
        );
    })
  );
  @Effect()
  delete = this.actions$.pipe(
    ofType(CategoryActions.DELETE_CATEGORY),
    switchMap((data: CategoryActions.DeleteCategory) => {
      return this.http
        .delete(
          'http://localhost:8080/categories/' + data.payload
        )
        .pipe(
          map(() => {
            return handleSuccess('delete');
          }),
          catchError(errorRes => {
            return handleError(errorRes, 'delete');
          })
        );
    })
  );
  @Effect()
  category = this.actions$.pipe(
    ofType(CategoryActions.GET_CATEGORY),
    switchMap((data: CategoryActions.GetCategoriesPage) => {
      return this.http
        .get<CategoryModel>(
          'http://localhost:8080/categories/by-id/' + data.payload
        )
        .pipe(
          map(dataRes => {
            const category = new CategoryModel(dataRes.id, dataRes.name);
            return new CategoryActions.GetCategorySuccess(category);
          }),
          catchError(errorRes => {
            return handleError(errorRes, 'getCategory');
          })
        );
    })
  );
  @Effect()
  edit = this.actions$.pipe(
    ofType(CategoryActions.EDIT_CATEGORY),
    switchMap((category: CategoryActions.EditCategory) => {
      return this.http
        .put<CategoryModel>(
          'http://localhost:8080/categories',
          {
            id: category.payload.id,
            name: category.payload.name
          }
        )
        .pipe(
          map(() => {
            return handleSuccess('edit');
          }),
          catchError(errorRes => {
            return handleError(errorRes, 'edit');
          })
        );
    })
  );
  constructor(private actions$: Actions, private http: HttpClient) {}
}

