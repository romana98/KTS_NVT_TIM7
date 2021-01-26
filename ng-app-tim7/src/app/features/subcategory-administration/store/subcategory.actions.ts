import { Action } from '@ngrx/store';
import {SubcategoryModel} from '../../../models/subcategory.model';

export const GET_SUBCATEGORIES_PAGE = '[Subcategory] Get subcategories page';
export const GET_SUBCATEGORIES_PAGE_SUCCESS = '[Subcategory] Get subcategories page success';
export const SUBCATEGORY_FAIL = '[Subcategory] Fail';
export const ADD_SUBCATEGORY = '[Subcategory] Add subcategory';
export const CLEAR_SUCCESS = '[Subcategory] Clear success';
export const CLEAR_ERROR = '[Subcategory] Clear error';
export const DELETE_SUBCATEGORY = '[Subcategory] Delete subcategory';
export const GET_SUBCATEGORY = '[Subcategory] Get subcategory';
export const GET_SUBCATEGORY_SUCCESS = '[Subcategory] Get subcategory success';
export const EDIT_SUBCATEGORY = '[Subcategory] Edit subcategory';
export const SUBCATEGORY_SUCCESS = '[Subcategory] Success';
export const EDIT_SUBCATEGORY_SUCCESS = '[Subcategory] Edit subcategory success';
export const GET_CATEGORIES_PAGE = '[Subcategory] Get categories page';
export const GET_CATEGORIES_PAGE_SUCCESS = '[Subcategory] Get categories page success';

export class GetCategoriesByPageSuccess implements Action {
  readonly type = GET_CATEGORIES_PAGE_SUCCESS;

  constructor(public payload: any) {}
}

export class GetCategoriesPage implements Action {
  readonly type = GET_CATEGORIES_PAGE;
  constructor(public payload: { page: number, size: number }) {}
}

export class EditSubcategorySuccess implements Action{
  readonly type = EDIT_SUBCATEGORY_SUCCESS;
  constructor(public payload: string) {}
}

export class SubcategorySuccess implements Action{
  readonly type = SUBCATEGORY_SUCCESS;
  constructor(public payload: string) {}
}

export class EditSubcategory implements Action{
  readonly type = EDIT_SUBCATEGORY;
  constructor(public payload: SubcategoryModel) {}
}

export class GetSubcategory implements Action{
  readonly type = GET_SUBCATEGORY;
  constructor(public payload: number) {}
}

export class GetSubcategorySuccess implements Action{
  readonly type = GET_SUBCATEGORY_SUCCESS;
  constructor(public payload: SubcategoryModel) {}
}

export class DeleteSubcategory implements Action{
  readonly type = DELETE_SUBCATEGORY;
  constructor(public payload: number){}
}

export class ClearSuccess implements Action{
  readonly type = CLEAR_SUCCESS;
  constructor() {}
}

export class ClearError implements Action{
  readonly type = CLEAR_ERROR;
  constructor() {}
}

export class AddSubcategory implements Action {
  readonly type = ADD_SUBCATEGORY;
  constructor(public payload: {name: string, categoryName: string}) {}
}

export class GetSubcategoriesByPageSuccess implements Action {
  readonly type = GET_SUBCATEGORIES_PAGE_SUCCESS;

  constructor(public payload: any) {}
}

export class GetSubcategoriesPage implements Action {
  readonly type = GET_SUBCATEGORIES_PAGE;
  constructor(public payload: { page: number, size: number }) {}
}

export class SubcategoryFail implements Action {
  readonly type = SUBCATEGORY_FAIL;

  constructor(public payload: string) {}
}

export type SubcategoryActions =
  | GetSubcategoriesByPageSuccess
  | SubcategoryFail
  | GetSubcategoriesPage
  | AddSubcategory
  | ClearError
  | ClearSuccess
  | DeleteSubcategory
  | GetSubcategory
  | GetSubcategorySuccess
  | EditSubcategory
  | SubcategorySuccess
  | EditSubcategorySuccess
  | GetCategoriesPage
  | GetCategoriesByPageSuccess;
