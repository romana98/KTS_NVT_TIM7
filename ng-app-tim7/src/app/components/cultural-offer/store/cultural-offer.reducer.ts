import * as CulturalOfferActions from '../../cultural-offer/store/cultural-offer.actions';
import {CulturalofferModel} from '../../../models/culturaloffer.model';

export interface State {
  culturalOffers: any;
  errorActionMessage: string;
  successActionMessage: string;
  culturalOfferDetailed: CulturalofferModel;
  comments: any;
  newsletters: any;
  averageRating: number;
  alreadyRated: number;
  alreadySubscribed: boolean;
}

const initialState: State = {
  culturalOffers : {content: []},
  errorActionMessage: null,
  successActionMessage: null,
  culturalOfferDetailed: null,
  comments : {content: [{description: '', registeredUser: '', picturesId: [''], publishedDate: new Date()}]},
  newsletters : {content: [{name: '', description: '', picture: '', publishedDate: new Date()}]},
  averageRating: 0.0,
  alreadyRated: 3,
  alreadySubscribed: false
};


export function CulturalOfferReducer(
  state = initialState,
  action: CulturalOfferActions.CulturalOfferActions
) {
  switch (action.type) {
    case CulturalOfferActions.ALREADY_SUBSCRIBED_VALUE:
      return{
        ...state,
        alreadySubscribed: action.payload,
        successActionMessage: null,
        errorActionMessage: null
      };
    case CulturalOfferActions.ALREADY_SUBSCRIBED:
      return{
        ...state,
        successActionMessage: null,
        errorActionMessage: null
      };
    case CulturalOfferActions.UNSUBSCRIBE:
      return{
        ...state,
        successActionMessage: null,
        errorActionMessage: null
      };
    case CulturalOfferActions.SUBSCRIBE:
      return{
        ...state,
        successActionMessage: null,
        errorActionMessage: null
      };
    case CulturalOfferActions.CREATE_COMMENT:
      return{
        ...state,
        successActionMessage: null,
        errorActionMessage: null
      };
    case CulturalOfferActions.ALREADY_RATED_SUCCESS:
      return{
        ...state,
        alreadyRated: action.payload.rate,
        successActionMessage: null,
        errorActionMessage: null
      };
    case CulturalOfferActions.ALREADY_RATED:
      return{
        ...state,
        successActionMessage: null,
        errorActionMessage: null
      };
    case CulturalOfferActions.RATE:
      return{
        ...state,
        successActionMessage: null,
        errorActionMessage: null
      };
    case CulturalOfferActions.GET_AVERAGE_RATING_SUCCESS:
      return{
        ...state,
        averageRating: action.payload.rate,
        successActionMessage: null,
        errorActionMessage: null
      };
    case CulturalOfferActions.GET_AVERAGE_RATING:
      return{
        ...state,
        successActionMessage: null,
        errorActionMessage: null
      };
    case CulturalOfferActions.GET_NEWSLETTERS_SUCCESS:
      if (action.payload.content.length === 0){
        const empty = {content: [{description: '', name: '', picture: ''}]};
        return{
          ...state,
          newsletters: empty,
          successActionMessage: null,
          errorActionMessage: null
        };
      }else{
        return{
          ...state,
          newsletters: action.payload,
          successActionMessage: null,
          errorActionMessage: null
        };
      }
    case CulturalOfferActions.GET_NEWSLETTERS:
      return {
        ...state,
        successActionMessage: null,
        errorActionMessage: null
      };
    case CulturalOfferActions.GET_COMMENTS_SUCCESS:
      if (action.payload.content.length === 0){
        const empty = {content: [{description: '', registeredUser: '', picturesId: [''], publishedDate: new Date()}]};
        return{
          ...state,
          comments: empty,
          successActionMessage: null,
          errorActionMessage: null
        };
      }else{
        return{
          ...state,
          comments: action.payload,
          successActionMessage: null,
          errorActionMessage: null
        };
      }
    case CulturalOfferActions.GET_COMMENTS:
      return {
        ...state,
        successActionMessage: null,
        errorActionMessage: null
      };
    case CulturalOfferActions.GO_TO_DETAILED:
      return {
        ...state,
        culturalOfferDetailed: action.payload,
        successActionMessage: null,
        errorActionMessage: null
      };
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
    case CulturalOfferActions.CLEAR_ACTION:
    default:
      return {
        ...state,
        errorActionMessage: null,
        successActionMessage: null
      };

  }
}
