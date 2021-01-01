import { Action } from '@ngrx/store';
import {UserModel} from '../../../models/user.model';

export const GET_ADMIN_PAGE = '[Administrator] Get admin page';
export const GET_ADMIN = '[Administrator] Get admin';
export const DELETE_ADMIN = '[Administrator] Delete admin';
export const ADD_ADMIN = '[Administrator] Add admin';
export const EDIT_ADMIN = '[Administrator] Edit admin';
export  const ADMIN_FAIL = '[Administrator] Admin fail';
export  const ADMIN_SUCCESS = '[Administrator] Admin success';
export  const ADMIN_SUCCESS_EDIT = '[Administrator] Admin success edit';
export  const GET_ADMINS_SUCCESS = '[Administrator] Get admins success';
export  const GET_ADMIN_SUCCESS = '[Administrator] Get admin success';
export const CLEAR_ERROR = '[Administrator] Clear Error';
export const CLEAR_SUCCESS = '[Administrator] Clear Success';


export class GetAdminPage implements Action {
  readonly type = GET_ADMIN_PAGE;

  constructor(public payload: { page: number, size: number }) {}
}

export class GetUser implements Action {
  readonly type = GET_ADMIN;
}

export class GetAdminsSuccess implements Action {
  readonly type = GET_ADMINS_SUCCESS;

  constructor(public payload: any) {}
}

export class GetAdminSuccess implements Action {
  readonly type = GET_ADMIN_SUCCESS;

  constructor(public payload: UserModel) {}
}

export class DeleteAdmin implements Action {
  readonly type = DELETE_ADMIN;

  constructor(public payload: number) {}
}

export class AddAdmin implements Action {
  readonly type = ADD_ADMIN;

  constructor(public payload: { username: string; email: string, password: string }) {}
}

export class EditProfile implements Action {
  readonly type = EDIT_ADMIN;

  constructor(public payload: { id: number; username: string; email: string, password: string }) {}
}

export class AdminSuccess implements Action {
  readonly type = ADMIN_SUCCESS;

  constructor(public payload: string) {}
}

export class AdminSuccessEdit implements Action {
  readonly type = ADMIN_SUCCESS_EDIT;

  constructor(public payload: string) {}
}

export class AdminFail implements Action {
  readonly type = ADMIN_FAIL;

  constructor(public payload: string) {}
}

export class ClearError implements Action {
  readonly type = CLEAR_ERROR;
}

export class ClearSuccess implements Action {
  readonly type = CLEAR_SUCCESS;
}

export type AdministratorActions =
  | GetAdminPage
  | GetUser
  | DeleteAdmin
  | AddAdmin
  | AdminSuccess
  | AdminFail
  | ClearSuccess
  | ClearError
  | GetAdminsSuccess
  | GetAdminSuccess
  | EditProfile
  | AdminSuccessEdit;
