import { Action } from '@ngrx/store';
import {NewsletterModel} from '../../../models/newsletter.model';

export const GET_NEWSLETTER_PAGE = '[Newsletter] Get newsletter page';
export const GET_NEWSLETTER = '[Newsletter] Get newsletter';
export const DELETE_NEWSLETTER = '[Newsletter] Delete newsletter';
export const ADD_NEWSLETTER = '[Newsletter] Add newsletter';
export const UPDATE_NEWSLETTER = '[Newsletter] Update newsletter';
export  const NEWSLETTER_FAIL = '[Newsletter] Newsletter fail';
export  const NEWSLETTER_SUCCESS = '[Newsletter] Newsletter success';
export  const GET_NEWSLETTERS_SUCCESS = '[Newsletter] Get newsletters success';
export  const GET_NEWSLETTER_SUCCESS = '[Newsletter] Get newsletter success';
export const CLEAR_ERROR = '[Newsletter] Clear Error';
export const CLEAR_SUCCESS = '[Newsletter] Clear Success';
export const GET_CATEGORIES_SELECT = '[Newsletter] Get categories select';
export const GET_CATEGORIES_SELECT_SUCCESS = '[Newsletter] Get categories select success';
export const GET_SUBCATEGORIES_SELECT = '[Newsletter] Get subcategories select';
export const GET_SUBCATEGORIES_SELECT_SUCCESS = '[Newsletter] Get subcategories select success';
export const GET_OFFERS_SELECT = '[Newsletter] Get offers select';
export const GET_OFFERS_SELECT_SUCCESS = '[Newsletter] Get offers select success';
export const GET_CATEGORIES_SUBSCRIBED = '[Newsletter] Get categories subscribed';
export const GET_CATEGORIES_SUBSCRIBED_SUCCESS = '[Newsletter] Get categories subscribed success';
export const GET_NEWSLETTERS_SUBSCRIBED = '[Newsletter] Get newsletters subscribed';
export const GET_NEWSLETTERS_SUBSCRIBED_SUCCESS = '[Newsletter] Get newsletters subscribed success';
export const UNSUBSCRIBE = "[Newsletter] Unsubscribe";


export class GetNewsletterPage implements Action {
  readonly type = GET_NEWSLETTER_PAGE;

  constructor(public payload: { page: number, size: number }) {}
}

export class GetNewsletter implements Action {
  readonly type = GET_NEWSLETTER;

  constructor(public payload: any) {}
}

export class GetNewslettersSuccess implements Action {
  readonly type = GET_NEWSLETTERS_SUCCESS;

  constructor(public payload: any) {}
}

export class GetCategoriesSelect implements Action {
  readonly type = GET_CATEGORIES_SELECT;

  constructor(public payload: { page: number, size: number }) {}
}

export class GetCategoriesSelectSuccess implements Action {
  readonly type = GET_CATEGORIES_SELECT_SUCCESS;

  constructor(public payload: any) {}
}

export class GetSubcategoriesSelect implements Action {
  readonly type = GET_SUBCATEGORIES_SELECT;

  constructor(public payload: { page: number, size: number, category: number }) {}
}

export class GetSubcategoriesSelectSuccess implements Action {
  readonly type = GET_SUBCATEGORIES_SELECT_SUCCESS;

  constructor(public payload: any) {}
}

export class GetOffersSelect implements Action {
  readonly type = GET_OFFERS_SELECT;

  constructor(public payload: { page: number, size: number, subcategory: number }) {}
}

export class GetOffersSelectSuccess implements Action {
  readonly type = GET_OFFERS_SELECT_SUCCESS;

  constructor(public payload: any) {}
}

export class GetNewsletterSuccess implements Action {
  readonly type = GET_NEWSLETTER_SUCCESS;

  constructor(public payload: NewsletterModel) {}
}

export class DeleteNewsletter implements Action {
  readonly type = DELETE_NEWSLETTER;

  constructor(public payload: number) {}
}

export class AddNewsletter implements Action {
  readonly type = ADD_NEWSLETTER;

  constructor(public payload: { name: string; description: string, picture: string, publishedDate: Date, culturalOfferId: number }) {}
}

export class UpdateNewsletter implements Action {
  readonly type = UPDATE_NEWSLETTER;

  constructor(public payload: { newsletter: NewsletterModel }) {}
}

export class Unsubscribe implements Action {
  readonly type = UNSUBSCRIBE;

  constructor(public payload: { idOffer: number, idUser: number }) {}
}

export class NewsletterSuccess implements Action {
  readonly type = NEWSLETTER_SUCCESS;

  constructor(public payload: string) {}
}

export class NewsletterFail implements Action {
  readonly type = NEWSLETTER_FAIL;

  constructor(public payload: string) {}
}

export class ClearError implements Action {
  readonly type = CLEAR_ERROR;
}

export class ClearSuccess implements Action {
  readonly type = CLEAR_SUCCESS;
}

export class GetCategoriesSubscribed implements Action {
  readonly type = GET_CATEGORIES_SUBSCRIBED;

  constructor(public payload: { id: number }) {}
}

export class GetCategoriesSubscribedSuccess implements Action {
  readonly type = GET_CATEGORIES_SUBSCRIBED_SUCCESS;

  constructor(public payload: any) {}
}

export class GetNewslettersSubscribed implements Action {
  readonly type = GET_NEWSLETTERS_SUBSCRIBED;

  constructor(public payload: { page: number, size: number, catId: number, id: number }) {}
}

export class GetNewslettersSubscribedSuccess implements Action {
  readonly type = GET_NEWSLETTERS_SUBSCRIBED_SUCCESS;

  constructor(public payload: any) {}
}

export type NewsletterActions =
  | GetNewsletterPage
  | GetNewsletter
  | DeleteNewsletter
  | AddNewsletter
  | NewsletterSuccess
  | NewsletterFail
  | ClearSuccess
  | ClearError
  | GetNewslettersSuccess
  | GetCategoriesSelect
  | GetCategoriesSelectSuccess
  | GetSubcategoriesSelect
  | GetSubcategoriesSelectSuccess
  | GetOffersSelect
  | GetOffersSelectSuccess
  | GetNewsletterSuccess
  | UpdateNewsletter
  | GetCategoriesSubscribed
  | GetCategoriesSubscribedSuccess
  | GetNewslettersSubscribed
  | GetNewslettersSubscribedSuccess
  | Unsubscribe;
