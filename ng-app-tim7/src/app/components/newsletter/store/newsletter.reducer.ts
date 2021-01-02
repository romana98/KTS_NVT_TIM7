import * as NewsletterActions from './newsletter.actions';
//import {UserModel} from '../../../models/user.model';
//import {SignedInModel} from '../../../models/signed-in.model';

export interface State {
  newsletters: any;
  error: string;
  success: string;
  bar: boolean;
  //user: UserModel;
}

const initialState: State = {
  newsletters: {content: []},
  error: null,
  success: null,
  bar: false,
  //user: localStorage.getItem('signed-in-user') === null ? null : getUser(),
};

/*function getUser() {
  const user = JSON.parse(localStorage.getItem('signed-in-user'));
  return new UserModel(user.username, user.email, user.password);
}*/

export function newsletterReducer(
  state = initialState,
  action: NewsletterActions.NewsletterActions
) {
  switch (action.type) {
    /*case AdminActions.DELETE_ADMIN:
      return {
        ...state,
        error: null,
        success: null
      };*/
    case NewsletterActions.NEWSLETTER_FAIL:
      return {
        ...state,
        error: action.payload
      };
    case NewsletterActions.NEWSLETTER_SUCCESS:
      return {
        ...state,
        success: action.payload,
        bar: false,
      };
    /*case AdminActions.ADD_ADMIN:
      return {
        ...state,
        bar: false,
      };*/
    /*case AdminActions.EDIT_ADMIN:
      return {
        ...state,
        bar: false,
      };*/
    case NewsletterActions.GET_NEWSLETTERS_SUCCESS:
      return {
        ...state,
        newsletters: action.payload
      };
    /*case NewsletterActions.GET_NEWSLETTER_SUCCESS:
      return {
        ...state,
        user: action.payload
      };*/
    case NewsletterActions.CLEAR_ERROR:
      return {
        ...state,
        error: null
      };
    case NewsletterActions.CLEAR_SUCCESS:
      return {
        ...state,
        success: null
      };
    case NewsletterActions.GET_NEWSLETTER_PAGE:
    //case NewsletterActions.GET_NEWSLETTER:
    default:
      return {
        ...state
      };

  }
}
