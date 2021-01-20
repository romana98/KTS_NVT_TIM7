import { Action } from '@ngrx/store';
import {CategoryModel} from '../../../models/category.model';

export const GET_CATEGORIES_PAGE = '[Category] Get categories page';
export const GET_CATEGORIES_PAGE_SUCCESS = '[Category] Get categories page success';
export const CATEGORY_FAIL = '[Category] Fail';
export const ADD_CATEGORY = '[Category] Add category';
export const CLEAR_SUCCESS = '[Category] Clear success';
export const CLEAR_ERROR = '[Category] Clear error';
export const DELETE_CATEGORY = '[Category] Delete category';
export const GET_CATEGORY = '[Category] Get category';
export const GET_CATEGORY_SUCCESS = '[Category] Get category success';
export const EDIT_CATEGORY = '[Category] Edit category';
export const CATEGORY_SUCCESS = '[Category] Success';
export const EDIT_CATEGORY_SUCCESS = '[Category] Edit category success';

export class EditCategorySuccess implements Action{
  readonly type = EDIT_CATEGORY_SUCCESS;
  constructor(public payload: string) {}
}

export class CategorySuccess implements Action{
  readonly type = CATEGORY_SUCCESS;
  constructor(public payload: string) {}
}

export class EditCategory implements Action{
  readonly type = EDIT_CATEGORY;
  constructor(public payload: CategoryModel) {}
}

export class GetCategory implements Action{
  readonly type = GET_CATEGORY;
  constructor(public payload: number) {}
}

export class GetCategorySuccess implements Action{
  readonly type = GET_CATEGORY_SUCCESS;
  constructor(public payload: CategoryModel) {}
}

export class DeleteCategory implements Action{
  readonly type = DELETE_CATEGORY;
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

export class AddCategory implements Action {
  readonly type = ADD_CATEGORY;
  constructor(public payload: {name: string}) {}
}

export class GetCategoriesByPageSuccess implements Action {
  readonly type = GET_CATEGORIES_PAGE_SUCCESS;

  constructor(public payload: any) {}
}

export class GetCategoriesPage implements Action {
  readonly type = GET_CATEGORIES_PAGE;
  constructor(public payload: { page: number, size: number }) {}
}

export class CategoryFail implements Action {
  readonly type = CATEGORY_FAIL;

  constructor(public payload: string) {}
}

export type CategoryActions =
  | GetCategoriesByPageSuccess
  | CategoryFail
  | GetCategoriesPage
  | AddCategory
  | ClearError
  | ClearSuccess
  | DeleteCategory
  | GetCategory
  | GetCategorySuccess
  | EditCategory
  | CategorySuccess
  | EditCategorySuccess;
