import * as CulturalOfferActions from '../../cultural-offer/store/cultural-offer.actions';

export interface State {
  culturalOffers: any;
  errorActionMessage: string;
  successActionMessage: string;
}

const initialState: State = {
  culturalOffers : {content: []},
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

    default:
      return {
        ...state,
        errorActionMessage: null,
        successActionMessage: null
      };

  }
}
