import * as CulturalOfferActions from '../../cultural-offer/store/cultural-offer.actions';
import {CulturalofferModel} from '../../../models/culturaloffer.model';

export interface State {
  culturalOffers: any;
  selectedOffer: CulturalofferModel;
  categoriesSelect: any;
  subcategoriesSelect: any;
  errorActionMessage: string;
  successActionMessage: string;
}

const initialState: State = {
  culturalOffers : {content: []},
  selectedOffer: null,
  errorActionMessage: null,
  successActionMessage: null,
  categoriesSelect: {content: []},
  subcategoriesSelect: null,
};


export function CulturalOfferReducer(
  state = initialState,
  action: CulturalOfferActions.CulturalOfferActions
) {
  switch (action.type) {
    case CulturalOfferActions.GET_CULTURALOFFER_PAGE_SUCCESS:
      return {
        ...state,
        culturalOffers : action.payload,
        errorActionMessage: null,
        successActionMessage: null
      };
    case CulturalOfferActions.ERROR_ACTION:
      return {
        ...state,
        errorActionMessage: action.payload,
        successActionMessage: null
      };
    case CulturalOfferActions.SUCCESS_ACTION:
      return {
        ...state,
        errorActionMessage: null,
        successActionMessage: action.payload
      };
    case CulturalOfferActions.FILTER_SUCCESS_ACTION:
      return {
        ...state,
        culturalOffers : action.payload,
      };
    case CulturalOfferActions.UPDATE_OFFER_ACTION_SUCCESS:
      console.log(action.payload);
      return {
        ...state,
        selectedOffer : action.payload,
        errorActionMessage: null,
        successActionMessage: 'Cultural offer saved successfully.'
      };
    case CulturalOfferActions.GET_CATEGORIES_SELECT_SUCCESS:
      return {
        ...state,
        categoriesSelect : action.payload,
        errorActionMessage: null,
        successActionMessage: null
      };
    case CulturalOfferActions.GET_ONE_OFFER_ACTION_SUCCESS:
      return {
        ...state,
        subcategoriesSelect : action.payload.subcategories,
        selectedOffer: action.payload.culturalOffer,
        categoriesSelect: action.payload.categories,
        errorActionMessage: null,
        successActionMessage: null
      };
    case CulturalOfferActions.GET_SUBCATEGORIES_AND_CATEGORIES_SUCCESS:
      return {
        ...state,
        categoriesSelect : action.payload.categories,
        subcategoriesSelect: action.payload.subcategories,
        errorActionMessage: null,
        successActionMessage: null
      };
    case CulturalOfferActions.CATEGORY_CHANGED_SUCCESS:
      return {
        ...state,
        subcategoriesSelect: action.payload,
        errorActionMessage: null,
        successActionMessage: null
      };
    case CulturalOfferActions.CLEAR_SELECTED_OFFER_ACTION:
      return {
        ...state,
        selectedOffer: null,
      };
    default:
      return {
        ...state,
        errorActionMessage: null,
        successActionMessage: null
      };

  }
}
