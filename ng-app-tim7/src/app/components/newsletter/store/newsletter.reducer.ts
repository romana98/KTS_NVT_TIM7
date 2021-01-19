import { NewsletterModel } from 'src/app/models/newsletter.model';
import * as NewsletterActions from './newsletter.actions';

export interface State {
  newsletters: any;
  error: string;
  success: string;
  bar: boolean;
  newsletter: NewsletterModel;
  categoriesSelect: any;
  subcategoriesSelect: any;
  offersSelect: any;
  categoriesSubscribed: any;
  newslettersSubscribed: any;
}

export const initialState: State = {
  newsletters: {content: []},
  error: null,
  success: null,
  newsletter: {id: 0, name: '', description: '', publishedDate: null, culturalOfferId: 0, picture: ''},
  bar: false,
  categoriesSelect: {content: []},
  subcategoriesSelect: {content: []},
  offersSelect: {content: []},
  categoriesSubscribed: [],
  newslettersSubscribed: {content: []},
};

export function newsletterReducer(
  state = initialState,
  action: NewsletterActions.NewsletterActions
) {
  switch (action.type) {
    case NewsletterActions.DELETE_NEWSLETTER:
      return {
        ...state,
        error: null,
        success: null
      };
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
    case NewsletterActions.ADD_NEWSLETTER:
      return {
        ...state,
        bar: true,
      };
    case NewsletterActions.UPDATE_NEWSLETTER:
      return {
        ...state,
        bar: true,
      };
    case NewsletterActions.GET_NEWSLETTERS_SUCCESS:
      return {
        ...state,
        newsletters: action.payload
      };
    case NewsletterActions.GET_NEWSLETTER_SUCCESS:
      return {
        ...state,
        newsletter: action.payload
      };
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
    case NewsletterActions.GET_CATEGORIES_SELECT_SUCCESS:
      const newCategories = {content: state.categoriesSelect.content.concat(action.payload.content)};
      return {
        ...state,
        categoriesSelect: newCategories
      };
    case NewsletterActions.GET_SUBCATEGORIES_SELECT_SUCCESS:
      const newSubcategories = !action.payload.number ? {content: action.payload.content} :
        {content: state.subcategoriesSelect.content.concat(action.payload.content)};
      return {
        ...state,
        subcategoriesSelect: newSubcategories,
        offersSelect: {content: [], numberOfElements: 0, totalElements: 0, totalPages: 0, number: 0}
      };
    case NewsletterActions.GET_OFFERS_SELECT_SUCCESS:
      const newOffers = !action.payload.number ? {content: action.payload.content} :
        {content: state.offersSelect.content.concat(action.payload.content)};
      return {
        ...state,
        offersSelect: newOffers
      };
    case NewsletterActions.GET_CATEGORIES_SUBSCRIBED_SUCCESS:
      return {
        ...state,
        categoriesSubscribed: action.payload
      };
    case NewsletterActions.GET_NEWSLETTERS_SUBSCRIBED_SUCCESS:
      return {
        ...state,
        newslettersSubscribed: action.payload
      };
    case NewsletterActions.UNSUBSCRIBE:
      return {
        ...state,
        bar: true,
      };
    case NewsletterActions.GET_CATEGORIES_SELECT:
    case NewsletterActions.GET_SUBCATEGORIES_SELECT:
    case NewsletterActions.GET_OFFERS_SELECT:
    case NewsletterActions.GET_NEWSLETTER_PAGE:
    case NewsletterActions.GET_NEWSLETTER:
    case NewsletterActions.GET_CATEGORIES_SUBSCRIBED:
    case NewsletterActions.GET_NEWSLETTERS_SUBSCRIBED:
    default:
      return {
        ...state
      };

  }
}
