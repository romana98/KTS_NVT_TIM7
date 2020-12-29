import * as ActivateAccountActions from './activate-account.actions';

export interface State {
  error: string;
  success: string;
}

const initialState: State = {
  error: null,
  success: null
};

export function activateAccountReducer(
  state = initialState,
  action: ActivateAccountActions.ActivateAccountActions
) {
  switch (action.type) {
    case ActivateAccountActions.ACTIVATE_SUCCESS:
      return {
        ...state,
        success: action.payload
      };
    case ActivateAccountActions.ACTIVATE:
      return {
        ...state,
        error: null,
        success: null
      };
    case ActivateAccountActions.ACTIVATE_FAIL:
      return {
        ...state,
        error: action.payload
      };
    case ActivateAccountActions.CLEAR_ERROR:
      return {
        ...state,
        error: null
      };
    case ActivateAccountActions.CLEAR_SUCCESS:
      return {
        ...state,
        success: null
      };
    default:
      return {
        ...state
      };

  }
}
