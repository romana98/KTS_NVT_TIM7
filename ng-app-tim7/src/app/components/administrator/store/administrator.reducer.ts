import * as AdminActions from './administrator.actions';

export interface State {
  admins: any;
  error: string;
  success: string;
}

const initialState: State = {
  admins: {content: []},
  error: null,
  success: null
};

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
        success: action.payload
      };
    case AdminActions.GET_ADMINS_SUCCESS:
      return {
        ...state,
        admins: action.payload
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
    default:
      return {
        ...state
      };

  }
}
