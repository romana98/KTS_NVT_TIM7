import * as AdminActions from './administrator.actions';
import {UserModel} from '../../../models/user.model';

export interface State {
  admins: any;
  error: string;
  success: string;
  bar: boolean;
  user: UserModel;
}

const initialState: State = {
  admins: {content: []},
  error: null,
  success: null,
  bar: false,
  user: localStorage.getItem('signed-in-user') === null ? new UserModel('', '', '') : getUser()
};

function getUser() {
  const user = JSON.parse(localStorage.getItem('signed-in-user'));
  return new UserModel(user.username, user.email, user.password);
}

export function administratorReducer(
  state = initialState,
  action: AdminActions.AdministratorActions
) {
  switch (action.type) {
    case AdminActions.DELETE_ADMIN:
      return {
        ...state,
        error: null,
        success: null
      };
    case AdminActions.ADMIN_FAIL:
      return {
        ...state,
        error: action.payload
      };
    case AdminActions.ADMIN_SUCCESS:
      return {
        ...state,
        success: action.payload,
        bar: false,
      };
    case AdminActions.ADMIN_SUCCESS_EDIT:
      return {
        ...state,
        success: action.payload,
        bar: false,
      };
    case AdminActions.ADD_ADMIN:
      return {
        ...state,
        bar: false,
      };
    case AdminActions.EDIT_ADMIN:
      return {
        ...state,
        bar: false,
      };
    case AdminActions.GET_ADMINS_SUCCESS:
      return {
        ...state,
        admins: action.payload
      };
    case AdminActions.GET_ADMIN_SUCCESS:
      return {
        ...state,
        user: action.payload
      };
    case AdminActions.CLEAR_ERROR:
      return {
        ...state,
        error: null
      };
    case AdminActions.CLEAR_SUCCESS:
      return {
        ...state,
        success: null
      };
    case AdminActions.GET_ADMIN_PAGE:
    case AdminActions.GET_ADMIN:
    default:
      return {
        ...state
      };

  }
}
