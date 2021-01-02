import { SignedInModel } from '../../../models/signed-in.model';
import * as AuthActions from './sign-in.actions';

export interface State {
  user: SignedInModel;
  authError: string;
}

const initialState: State = {
  user: localStorage.getItem('user') === null ? null : getUser(),
  authError: null
};

function getUser() {
  const user = JSON.parse(localStorage.getItem('user'));
  return new SignedInModel(user.username, user.id, user.accessToken, user.role);
}

export function signInReducer(
  state = initialState,
  action: AuthActions.SignInActions
) {
  switch (action.type) {
    case AuthActions.AUTHENTICATE_SUCCESS:
      const user = new SignedInModel(
        action.payload.username,
        action.payload.id,
        action.payload.accessToken,
        action.payload.role
      );
      return {
        ...state,
        user
      };
    case AuthActions.SIGN_IN:
      return {
        ...state,
        authError: null,
      };
    case AuthActions.AUTHENTICATE_FAIL:
      return {
        ...state,
        user: null,
        authError: action.payload,
      };
    case AuthActions.SIGN_OUT:
      return {
        ...state,
        user: null
      };
    case AuthActions.CLEAR_ERROR:
      return {
        ...state,
        authError: null
      };
    default:
      return {
        ...state
      };

  }
}
