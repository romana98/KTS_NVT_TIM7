import * as SignUpActions from './sign-up.actions';

export interface State {
  error: string;
  success: string;
  bar: boolean;
}

export const initialState: State = {
  error: null,
  success: null,
  bar: false
};

export function signUpReducer(
  state = initialState,
  action: SignUpActions.SignUpActions
) {
  switch (action.type) {
    case SignUpActions.SIGN_UP_SUCCESS:
      return {
        ...state,
        success: action.payload,
        bar: false
      };
    case SignUpActions.SIGN_UP:
      return {
        ...state,
        error: null,
        success: null,
        bar: true
      };
    case SignUpActions.SIGN_UP_FAIL:
      return {
        ...state,
        error: action.payload,
        bar: false
      };
    case SignUpActions.CLEAR_ERROR:
      return {
        ...state,
        error: null
      };
    case SignUpActions.CLEAR_SUCCESS:
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
