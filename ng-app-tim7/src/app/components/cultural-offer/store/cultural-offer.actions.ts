import { Action } from '@ngrx/store';
import {CulturalofferModel} from '../../../models/culturaloffer.model';

export const GET_CULTURALOFFER_PAGE = '[CulturalOffer] Get cultural-offer page';
export const GET_CULTURALOFFER_PAGE_SUCCESS = '[CulturalOffer] Get cultural-offer page success';
export const ERROR_ACTION = '[CulturalOffer] Action Error';
export const DELETE_CULTURALOFFER = '[CulturalOffer] Delete cultural-offer';
export const SUCCESS_ACTION = '[CulturalOffer] Action Success';
export const CLEAR_ACTION = '[CulturalOffer] Action clean';
export const FILTER_ACTION = '[CulturalOffer] Action Filter';
export const FILTER_SUCCESS_ACTION = '[CulturalOffer] Action Filter Success';
export const GO_TO_DETAILED = '[CulturalOffer] Go To Detailed';
export const GET_COMMENTS = '[CulturalOffer] Get comments';
export const GET_COMMENTS_SUCCESS = '[CulturalOffer] Get Comments Success';
export const GET_NEWSLETTERS = '[CulturalOffer] Get newsletters';
export const GET_NEWSLETTERS_SUCCESS = '[CulturalOffer] Get newsletter success';
export const GET_AVERAGE_RATING = '[CulturalOffer] Get average rating';
export const GET_AVERAGE_RATING_SUCCESS = '[CulturalOffer] Get average rating success';
export const RATE = '[CulturalOffer] Rate';
export const ALREADY_RATED = '[CulturalOffer] Already rated';
export const ALREADY_RATED_SUCCESS = '[CulturalOffer] Already rated success';
export const CREATE_COMMENT = '[CulturalOffer] Create comment';
export const SUBSCRIBE = '[CulturalOffer] Subscribe';
export const UNSUBSCRIBE = '[CulturalOffer] Unsubscribe';
export const ALREADY_SUBSCRIBED = '[CulturalOffer] Already subscribed';
export const ALREADY_SUBSCRIBED_VALUE = '[CulturalOffer] Already subscribed value';
export const GET_ONE_OFFER_ACTION = '[CulturalOffer] Get one offer';
export const GET_ONE_OFFER_ACTION_SUCCESS = '[CulturalOffer] get one offer Success';

export class AlreadySubscribed implements Action{
  type: string = ALREADY_SUBSCRIBED;
  constructor(public payload: number) {
  }
}

export class AlreadySubscribedValue implements Action{
  type: string = ALREADY_SUBSCRIBED_VALUE;
  constructor(public payload: boolean) {
  }
}

export class Subscribe implements Action{
  type: string = SUBSCRIBE;
  constructor(public payload: {offerId: number, userId: number}) {
  }
}

export class Unsubscribe implements Action{
  type: string = UNSUBSCRIBE;
  constructor(public payload: {offerId: number, userId: number}) {
  }
}

export class CreateComment implements Action{
  type: string = CREATE_COMMENT;
  constructor(public payload: {description: string, publishedDate: Date,
    registeredId: number, picturesId: any, culturalOfferId: number}) {
  }
}

export class AlreadyRatedSuccess implements Action{
  type: string = ALREADY_RATED_SUCCESS;
  constructor(public payload: any) {
  }
}

export class AlreadyRated implements Action{
  type: string = ALREADY_RATED;
  constructor(public payload: number) {
  }
}

export class Rate implements Action{
  type: string = RATE;
  constructor(public payload: {offerId: number, rate: number, registeredId: number}) {
  }
}

export class GetAverageRating implements Action{
  type: string = GET_AVERAGE_RATING;
  constructor(public payload: {offerId: number}) {
  }
}

export class GetAverageRatingSuccess implements Action{
  type: string = GET_AVERAGE_RATING_SUCCESS;
  constructor(public payload: any) {
  }
}

export class GetNewsletters implements Action{
  type: string = GET_NEWSLETTERS;
  constructor(public payload: {page: number, size: number, offerId: number}) {
  }
}

export class GetNewslettersSuccess implements Action{
  type: string = GET_NEWSLETTERS_SUCCESS;
  constructor(public payload: any) {
  }
}

export class GetCommentsSuccess implements Action{
  type: string = GET_COMMENTS_SUCCESS;
  constructor(public payload: any) {
  }
}

export class GetComments implements Action {
  type: string = GET_COMMENTS;
  constructor(public payload: {page: number, size: number, offerId: number}) {
  }
}

export class GoToDetailed implements Action {
  type: string = GO_TO_DETAILED;
  constructor(public payload: CulturalofferModel) {}
}

export class GetCulturalOfferPage implements Action {
  type: string = GET_CULTURALOFFER_PAGE;

  constructor(public payload: { page: number, size: number }) {}
}

export class GetCulturalOfferPageSuccess implements Action {
  type: string = GET_CULTURALOFFER_PAGE_SUCCESS;

  constructor(public payload: any) {}
}

export class DeleteCulturalOffer implements Action {
  type: string = DELETE_CULTURALOFFER;

  constructor(public payload: { id: number, page: number, page_size: number}) {}
}

export class ErrorAction implements Action {
  type: string = ERROR_ACTION;

  constructor(public payload: string) {}
}

export class SuccessAction implements Action {
  type: string = SUCCESS_ACTION;

  constructor(public payload: string) {}
}

export class ClearAction implements Action {
  type: string = CLEAR_ACTION;

  constructor() {}
}

export class FilterCulturalOffersAction implements Action {
  type: string = FILTER_ACTION;

  constructor(public payload: { parameter: string, value: string, page: number, page_size: number}) {}
}

export class FilterSuccessAction implements Action{
  type: string = FILTER_SUCCESS_ACTION;

  constructor(public payload: any) {}
}

export class GetOneOfferAction {
  type: string = GET_ONE_OFFER_ACTION;

  constructor(public payload: number) {}
}

export class GetOneOfferActionSuccess {
  type: string = GET_ONE_OFFER_ACTION_SUCCESS;

  constructor(public payload: any) {}

}


export type CulturalOfferActions =
  | GetCulturalOfferPage
  | GetCulturalOfferPageSuccess
  | DeleteCulturalOffer
  | ErrorAction
  | SuccessAction
  | FilterCulturalOffersAction
  | GetComments
  | GetCommentsSuccess
  | GetNewsletters
  | GetNewslettersSuccess
  | GetAverageRating
  | GetAverageRatingSuccess
  | Rate
  | AlreadyRated
  | AlreadyRatedSuccess
  | CreateComment
  | Subscribe
  | Unsubscribe
  | AlreadySubscribed
  | AlreadySubscribedValue
  | FilterSuccessAction
  | GoToDetailed
  | GetOneOfferAction
  | GetOneOfferActionSuccess;
