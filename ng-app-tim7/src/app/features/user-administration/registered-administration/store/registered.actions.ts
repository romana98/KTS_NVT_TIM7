import { Action } from '@ngrx/store';
import {UserModel} from '../../../../models/user.model';

export const GET_REG = '[Registered] Get registered';
export const EDIT_REG = '[Registered] Edit registered';
export  const REG_FAIL = '[Registered] Registered fail';
export  const REG_SUCCESS = '[Registered] Registered success';
export  const GET_REG_SUCCESS = '[Registered] Get registered success';
export const CLEAR_ERROR = '[Registered] Clear Error';
export const CLEAR_SUCCESS = '[Registered] Clear Success';


export class GetUser implements Action {
  readonly type = GET_REG;
}

export class EditProfile implements Action {
  readonly type = EDIT_REG;

  constructor(public payload: { id: number; username: string; email: string, password: string }) {}
}

export class GetRegisteredSuccess implements Action {
  readonly type = GET_REG_SUCCESS;

  constructor(public payload: UserModel) {}
}

export class RegisteredSuccess implements Action {
  readonly type = REG_SUCCESS;

  constructor(public payload: string) {}
}

export class RegisteredFail implements Action {
  readonly type = REG_FAIL;

  constructor(public payload: string) {}
}

export class ClearError implements Action {
  readonly type = CLEAR_ERROR;
}

export class ClearSuccess implements Action {
  readonly type = CLEAR_SUCCESS;
}

export type RegisteredActions =
  | GetUser
  | RegisteredSuccess
  | RegisteredFail
  | ClearSuccess
  | ClearError
  | GetRegisteredSuccess
  | EditProfile;
