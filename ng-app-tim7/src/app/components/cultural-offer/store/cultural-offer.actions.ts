import { Action } from '@ngrx/store';

export const GET_CULTURALOFFER_PAGE = '[CulturalOffer] Get cultural-offer page';
export const GET_CULTURALOFFER_PAGE_SUCCESS = '[CulturalOffer] Get cultural-offer page success';
export const ERROR_ACTION = '[CulturalOffer] Action Error';
export const DELETE_CULTURALOFFER = '[CulturalOffer] Delete cultural-offer';
export const SUCCESS_ACTION = '[CulturalOffer] Action Success';
export const CLEAR_ACTION = '[CulturalOffer] Action clean';
export const FILTER_ACTION = '[CulturalOffer] Action Filter';
export const FILTER_SUCCESS_ACTION = '[CulturalOffer] Action Filter Success';



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


export type CulturalOfferActions =
  | GetCulturalOfferPage
  | GetCulturalOfferPageSuccess
  | DeleteCulturalOffer
  | ErrorAction
  | SuccessAction
  | FilterCulturalOffersAction
  | FilterSuccessAction;
