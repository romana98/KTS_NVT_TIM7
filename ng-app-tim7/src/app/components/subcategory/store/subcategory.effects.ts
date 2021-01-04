import { Injectable } from '@angular/core';
import { Actions, ofType, Effect } from '@ngrx/effects';
import {switchMap, catchError, map} from 'rxjs/operators';
import { of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import * as SubcategoryActions from '../store/subcategory.actions';
import {SubcategoryModel} from '../../../models/subcategory.model';

const handleSuccess = (type: string) => {
  let message = '';
  if (type === 'add'){
    message = 'Subcategory added successfully.';
  }else if (type === 'delete'){
    message = 'Subcategory deleted successfully.';
  }else{
    message = 'Subcategory updated successfully.';
    return new SubcategoryActions.EditSubcategorySuccess(message);
  }
  return new SubcategoryActions.SubcategorySuccess(message);
};

const handleError = (errorRes: any, type: string) => {
  let errorMessage = '';
  if (type === 'add'){
    errorMessage = 'Subcategory with that name already exists!';
  }else if (type === 'edit'){
    errorMessage = 'Subcategory with that name already exists!';
  }else if (type === 'delete'){
    errorMessage = 'Deleting failed! Check if selected subcategory has its cultural offers!';
  }else if (type === 'edit'){
    errorMessage = 'Editing failed! Subcategory name already exists!';
  }
  else{ // get All
    errorMessage = 'An unknown error has occurred!';
  }
  return of(new SubcategoryActions.SubcategoryFail(errorMessage));
};

@Injectable()
export class SubcategoryEffects{
  @Effect()
  subcategories = this.actions$.pipe(
    ofType(SubcategoryActions.GET_SUBCATEGORIES_PAGE),
    switchMap((data: SubcategoryActions.GetSubcategoriesPage) => {
      return this.http
        .get(
          'http://localhost:8080/subcategories/by-page?page=' + data.payload.page + '&size=' + data.payload.size
        )
        .pipe(
          map(dataRes => {
            return new SubcategoryActions.GetSubcategoriesByPageSuccess(dataRes);
          }),
          catchError(errorRes => {
            return handleError(errorRes, 'getAll');
          })
        );
    })
  );
  @Effect()
  add = this.actions$.pipe(
    ofType(SubcategoryActions.ADD_SUBCATEGORY),
    switchMap((subcategory: SubcategoryActions.AddSubcategory) => {
      return this.http
        .post<SubcategoryModel>(
          'http://localhost:8080/subcategories',
          {
            name: subcategory.payload.name,
            categoryName: subcategory.payload.categoryName
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
    ofType(SubcategoryActions.DELETE_SUBCATEGORY),
    switchMap((data: SubcategoryActions.DeleteSubcategory) => {
      return this.http
        .delete(
          'http://localhost:8080/subcategories/' + data.payload
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
  subcategory = this.actions$.pipe(
    ofType(SubcategoryActions.GET_SUBCATEGORY),
    switchMap((data: SubcategoryActions.GetSubcategoriesPage) => {
      return this.http
        .get<SubcategoryModel>(
          'http://localhost:8080/subcategories/by-id/' + data.payload
        )
        .pipe(
          map(dataRes => {
            const subcategory = new SubcategoryModel(dataRes.id, dataRes.name, dataRes.categoryId, dataRes.categoryName);
            return new SubcategoryActions.GetSubcategorySuccess(subcategory);
          }),
          catchError(errorRes => {
            return handleError(errorRes, 'getSubcategory');
          })
        );
    })
  );
  @Effect()
  edit = this.actions$.pipe(
    ofType(SubcategoryActions.EDIT_SUBCATEGORY),
    switchMap((subcategory: SubcategoryActions.EditSubcategory) => {
      return this.http
        .put<SubcategoryModel>(
          'http://localhost:8080/subcategories',
          {
            id: subcategory.payload.id,
            name: subcategory.payload.name,
            categoryName: subcategory.payload.categoryName,
            categoryId: subcategory.payload.categoryId
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
  @Effect()
  categoriesSelect = this.actions$.pipe(
    ofType(SubcategoryActions.GET_CATEGORIES_PAGE),
    switchMap((data: SubcategoryActions.GetCategoriesPage) => {
      return this.http
        .get(
          'http://localhost:8080/categories/by-page?page=' + data.payload.page + '&size=' + data.payload.size
        )
        .pipe(
          map(dataRes => {
            return new SubcategoryActions.GetCategoriesByPageSuccess(dataRes);
          }),
          catchError(errorRes => {
            return handleError(errorRes, 'getAll');
          })
        );
    })
  );
  constructor(private actions$: Actions, private http: HttpClient) {}
}
