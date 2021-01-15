import * as CulturalOfferActions from '../../cultural-offer/store/cultural-offer.actions';
import {CulturalofferModel} from '../../../models/culturaloffer.model';

export interface State {
  culturalOffers: any;
  selectedOffer: CulturalofferModel;
  errorActionMessage: string;
  successActionMessage: string;
}

const initialState: State = {
  culturalOffers : {content: []},
  selectedOffer: null,
  errorActionMessage: null,
  successActionMessage: null,
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
    case CulturalOfferActions.GET_ONE_OFFER_ACTION_SUCCESS:
      return {
        ...state,
        selectedOffer : action.payload,
      };
    default:
      return {
        ...state,
        errorActionMessage: null,
        successActionMessage: null
      };

  }
}
