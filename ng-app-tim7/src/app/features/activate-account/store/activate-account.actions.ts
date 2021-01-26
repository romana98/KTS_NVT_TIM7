import { Action } from '@ngrx/store';

export const ACTIVATE = '[Activate] Activate account';
export const ACTIVATE_SUCCESS = '[Activate] Activation success';
export const ACTIVATE_FAIL = '[Activate] Activation fail';
export const CLEAR_ERROR = '[Activate] Clear Error';
export const CLEAR_SUCCESS = '[Activate] Clear Success';

export class ActivateSuccess implements Action {
  readonly type = ACTIVATE_SUCCESS;

  constructor(public payload: string) {}
}

export class ActivateStart implements Action {
  readonly type = ACTIVATE;

  constructor(public payload: { id: number }) {}
}

export class ActivateFail implements Action {
  readonly type = ACTIVATE_FAIL;

  constructor(public payload: string) {}
}

export class ClearError implements Action {
  readonly type = CLEAR_ERROR;
}

export class ClearSuccess implements Action {
  readonly type = CLEAR_SUCCESS;
}

export type ActivateAccountActions =
  | ActivateStart
  | ActivateSuccess
  | ActivateFail
  | ClearError
  | ClearSuccess;
