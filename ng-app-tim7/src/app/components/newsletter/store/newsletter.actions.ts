import { Action } from '@ngrx/store';
import {UserModel} from '../../../models/user.model';

export const GET_NEWSLETTER_PAGE = '[Newsletter] Get newsletter page';
export const GET_NEWSLETTER = '[Newsletter] Get newsletter';
export const DELETE_NEWSLETTER = '[Newsletter] Delete newsletter';
export const ADD_NEWSLETTER = '[Newsletter] Add newsletter';
export const EDIT_NEWSLETTER = '[Newsletter] Edit newsletter';
export  const NEWSLETTER_FAIL = '[Newsletter] Newsletter fail';
export  const NEWSLETTER_SUCCESS = '[Newsletter] Newsletter success';
export  const GET_NEWSLETTERS_SUCCESS = '[Newsletter] Get newsletters success';
export  const GET_NEWSLETTER_SUCCESS = '[Newsletter] Get newsletter success';
export const CLEAR_ERROR = '[Newsletter] Clear Error';
export const CLEAR_SUCCESS = '[Newsletter] Clear Success';


export class GetNewsletterPage implements Action {
  readonly type = GET_NEWSLETTER_PAGE;

  constructor(public payload: { page: number, size: number }) {}
}

/*export class GetNewsletter implements Action {
  readonly type = GET_NEWSLETTER;
}*/

export class GetNewslettersSuccess implements Action {
  readonly type = GET_NEWSLETTERS_SUCCESS;

  constructor(public payload: any) {}
}

/*export class GetNewsletterSuccess implements Action {
  readonly type = GET_NEWSLETTER_SUCCESS;

  constructor(public payload: UserModel) {}
}*/

/*export class DeleteNewsletter implements Action {
  readonly type = DELETE_NEWSLETTER;

  constructor(public payload: number) {}
}*/

/*export class AddAdmin implements Action {
  readonly type = ADD_NEWSLETTER;

  constructor(public payload: { username: string; email: string, password: string }) {}
}*/

/*export class EditProfile implements Action {
  readonly type = EDIT_ADMIN;

  constructor(public payload: { id: number; username: string; email: string, password: string }) {}
}*/

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

export type NewsletterActions =
  | GetNewsletterPage
//  | GetNewsletter
//  | DeleteAdmin
//  | AddAdmin
  | NewsletterSuccess
  | NewsletterFail
  | ClearSuccess
  | ClearError
  | GetNewslettersSuccess
//  | GetNewsletterSuccess
//  | EditProfile;
