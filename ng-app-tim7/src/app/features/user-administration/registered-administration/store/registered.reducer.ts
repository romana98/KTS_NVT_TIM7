import * as RegActions from './registered.actions';
import {UserModel} from '../../../../models/user.model';

export interface State {
  error: string;
  success: string;
  bar: boolean;
  user: UserModel;
}

export const initialState: State = {
  error: null,
  success: null,
  bar: false,
  user: localStorage.getItem('signed-in-user') === null ? new UserModel('', '', '') : getUser()
};

function getUser() {
  const user = JSON.parse(localStorage.getItem('signed-in-user'));
  return new UserModel(user.username, user.email, user.password);
}

export function registeredReducer(
  state = initialState,
  action: RegActions.RegisteredActions
) {
  switch (action.type) {

    case RegActions.REG_FAIL:
      return {
        ...state,
        error: action.payload
      };
    case RegActions.REG_SUCCESS:
      return {
        ...state,
        success: action.payload,
        bar: false,
      };
    case RegActions.EDIT_REG:
      return {
        ...state,
        bar: false,
      };
    case RegActions.GET_REG_SUCCESS:
      return {
        ...state,
        user: action.payload
      };
    case RegActions.CLEAR_ERROR:
      return {
        ...state,
        error: null
      };
    case RegActions.CLEAR_SUCCESS:
      return {
        ...state,
        success: null
      };
    case RegActions.GET_REG:
    default:
      return {
        ...state
      };

  }
}
