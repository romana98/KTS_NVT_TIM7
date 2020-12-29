import { Action } from '@ngrx/store';

export const SIGN_UP = '[SignUp] Sign up';
export const SIGN_UP_SUCCESS = '[SignUp] Sign-up success';
export const SIGN_UP_FAIL = '[SignUp] Sign-up fail';
export const CLEAR_ERROR = '[SignUp] Clear Error';
export const CLEAR_SUCCESS = '[SignUp] Clear Success';

export class SignUpSuccess implements Action {
  readonly type = SIGN_UP_SUCCESS;

  constructor(public payload: string) {}
}

export class SignUpStart implements Action {
  readonly type = SIGN_UP;

  constructor(public payload: { username: string; email: string, password: string }) {}
}

export class SignUpFail implements Action {
  readonly type = SIGN_UP_FAIL;

  constructor(public payload: string) {}
}

export class ClearError implements Action {
  readonly type = CLEAR_ERROR;
}

export class ClearSuccess implements Action {
  readonly type = CLEAR_SUCCESS;
}

export type SignUpActions =
  | SignUpStart
  | SignUpSuccess
  | ClearSuccess
  | SignUpFail
  | ClearError;
