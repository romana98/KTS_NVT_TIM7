import { Action } from '@ngrx/store';

export const GET_CULTURALOFFER_PAGE = '[CulturalOffer] Get cultural-offer page';
export const GET_CULTURALOFFER_PAGE_SUCCESS = '[CulturalOffer] Get cultural-offer page success';
export const ERROR_ACTION = '[CulturalOffer] Action Error';
export const DELETE_CULTURALOFFER = '[CulturalOffer] Delete cultural-offer';
export const SUCCESS_ACTION = '[CulturalOffer] Action Success';
export const CLEAR_ACTION = '[CulturalOffer] Action clean';
export const FILTER_ACTION = '[CulturalOffer] Action Filter';
export const FILTER_SUCCESS_ACTION = '[CulturalOffer] Action Filter Success';
export const GET_ONE_OFFER_ACTION = '[CulturalOffer] Get one offer';
export const GET_ONE_OFFER_ACTION_SUCCESS = '[CulturalOffer] get one offer Success';
export const UPDATE_OFFER_ACTION = '[CulturalOffer] Add one offer';
export const UPDATE_OFFER_ACTION_SUCCESS = '[CulturalOffer] Add one offer success';
export const GET_CATEGORIES_SELECT = '[CulturalOffer] Get categories';
export const GET_CATEGORIES_SELECT_SUCCESS = '[CulturalOffer] Get categories success';
export const GET_SUBCATEGORIES_SELECT = '[CulturalOffer] Get subcategories';
export const GET_SUBCATEGORIES_AND_CATEGORIES = '[CulturalOffer] Get subcategories and categories.';
export const GET_SUBCATEGORIES_AND_CATEGORIES_SUCCESS = '[CulturalOffer] Get subcategories and categories success.';
export const GET_INITIAL_CATEGORIES_SELECT = '[CulturalOffer] Get initial categories success.';
export const CATEGORY_CHANGED = '[CulturalOffer] Category changed.';
export const CATEGORY_CHANGED_SUCCESS = '[CulturalOffer] Category changed success.';
export const CLEAR_SELECTED_OFFER_ACTION = '[CulturalOffer] Clear selected offer action.';


export class GetCulturalOfferPage implements Action {
  type: string = GET_CULTURALOFFER_PAGE;

  constructor(public payload: { page: number, size: number }) {}
}

export class GetCulturalOfferPageSuccess implements Action {
  type: string = GET_CULTURALOFFER_PAGE_SUCCESS;

  constructor(public payload: any) {}
}

export class DeleteCulturalOffer implements Action {
  type: string = DELETE_CULTURALOFFER;

  constructor(public payload: { id: number, page: number, page_size: number}) {}
}

export class ErrorAction implements Action {
  type: string = ERROR_ACTION;

  constructor(public payload: string) {}
}

export class SuccessAction implements Action {
  type: string = SUCCESS_ACTION;

  constructor(public payload: string) {}
}

export class ClearAction implements Action {
  type: string = CLEAR_ACTION;

  constructor() {}
}

export class FilterCulturalOffersAction implements Action {
  type: string = FILTER_ACTION;

  constructor(public payload: { parameter: string, value: string, page: number, page_size: number}) {}
}

export class FilterSuccessAction implements Action{
  type: string = FILTER_SUCCESS_ACTION;

  constructor(public payload: any) {}
}

export class GetOneOfferAction {
  type: string = GET_ONE_OFFER_ACTION;

  constructor(public payload: number) {}
}

export class UpdateOfferAction {
  type: string = UPDATE_OFFER_ACTION;

  constructor(public payload: any) {}
}

export class UpdateOfferActionSuccess {
  type: string = UPDATE_OFFER_ACTION_SUCCESS;

  constructor(public payload: any) {}
}

export class GetCategories {
  type: string = GET_CATEGORIES_SELECT;

  constructor(public payload: { page: number, page_size: number}) {}
}

export class GetInitialCategories {
  type: string = GET_INITIAL_CATEGORIES_SELECT;

  constructor(public payload: { page: number, page_size: number, subcategories: any, culturalOffer: any}) {}
}

export class GetCategoriesSuccess {
  type: string = GET_CATEGORIES_SELECT_SUCCESS;

  constructor(public payload: any) {}
}

export class GetSubCategories {
  type: string = GET_SUBCATEGORIES_SELECT;
  categoryId: any;

  constructor(public payload: {page: number, page_size: number, culturalOffer: any}) {
    this.categoryId = payload.culturalOffer.category;
  }
}

export class GetSubCategoriesAndCategories {
  type: string = GET_SUBCATEGORIES_AND_CATEGORIES;
  categoryId: number;

  constructor(public payload: {page: number, page_size: number, categories: any}) {
    this.categoryId = payload.categories.content[0].id;
  }
}

export class GetSubCategoriesAndCategoriesSuccess {
  type: string = GET_SUBCATEGORIES_AND_CATEGORIES_SUCCESS;

  constructor(public payload: {categories: any, subcategories: any}) {}
}

export class GetOneOfferActionSuccess {
  type: string = GET_ONE_OFFER_ACTION_SUCCESS;

  constructor(public payload: {categories: any, subcategories: any; culturalOffer: any}) { }
}

export class CategoryChangedAction {
  type: string = CATEGORY_CHANGED;

  constructor(public payload: {categoryId: number, page: number, page_size: number} ) { }
}

export class CategoryChangedActionSuccess {
  type: string = CATEGORY_CHANGED_SUCCESS;

  constructor(public payload: any) { }
}

export class ClearSelectedOfferAction {
  type: string = CLEAR_SELECTED_OFFER_ACTION;

  constructor() { }
}

export type CulturalOfferActions =
  | GetCulturalOfferPage
  | GetCulturalOfferPageSuccess
  | DeleteCulturalOffer
  | ErrorAction
  | SuccessAction
  | FilterCulturalOffersAction
  | FilterSuccessAction
  | GetCategories
  | GetSubCategories
  | GetCategoriesSuccess
  | GetSubCategoriesAndCategories
  | GetSubCategoriesAndCategoriesSuccess;
