import {initialState, CulturalOfferReducer} from './cultural-offer.reducer';
import {CulturalofferModel} from '../../../models/culturaloffer.model';
import * as CulturalOfferActions from './cultural-offer.actions';

describe('Cultural offer reducer', () => {
  describe('undefined action', () => {
    it('should return the default state and set the error and success to null', () => {
      const action = { type: 'NOOP' } as any;
      const result = CulturalOfferReducer(undefined, action);
      expect(result).toEqual({
        ...initialState,
        errorActionMessage: null,
        successActionMessage: null
      });
    });
  });
  describe('Go To Detailed', () => {
    it('should set the detailed cultural offer info', () => {
      const offer = new CulturalofferModel(1, 'name', 'opis',
        new Date(), new Date(), 1, 1, 'subcategory', 'category', 1, 1.1, 2.1, [], 'location');
      const action = new CulturalOfferActions.GoToDetailed(offer);
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        culturalOfferDetailed: action.payload,
        successActionMessage: null,
        errorActionMessage: null
      });
    });
  });
  describe('Get comments', () => {
    it('should return the default state and set the error and success to null', () => {
      const action = new CulturalOfferActions.GetComments({page: 0, size: 1, offerId: 1});
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        errorActionMessage: null,
        successActionMessage: null
      });
    });
  });
  describe('Get comments success', () => {
    it('should add the fetched comments to the state', () => {
      const action = new CulturalOfferActions.GetCommentsSuccess(
        {content: [{description: 'description', registeredUser: 'registered', picturesId: ['picture'], publishedDate: new Date()}]});
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        comments: action.payload,
        successActionMessage: null,
        errorActionMessage: null
      });
    });
    it('should add the fetched comments(empty) to the state', () => {
      const action = new CulturalOfferActions.GetCommentsSuccess(
        {content: []});
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        comments: {content: [{description: '', registeredUser: '', picturesId: [''], publishedDate: new Date()}]},
        successActionMessage: null,
        errorActionMessage: null
      });
    });
  });
  describe('Get newsletters', () => {
    it('should return the default state and set the error and success to null', () => {
      const action = new CulturalOfferActions.GetNewsletters({page: 0, size: 1, offerId: 1});
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        errorActionMessage: null,
        successActionMessage: null
      });
    });
  });
  describe('Get newsletters success', () => {
    it('should add the fetched newsletters to the state', () => {
      const action = new CulturalOfferActions.GetNewslettersSuccess(
        {content: [{description: 'description', name: 'name', picture: 'picture'}]});
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        newsletters: action.payload,
        successActionMessage: null,
        errorActionMessage: null
      });
    });
    it('should add the fetched newsletters(empty) to the state', () => {
      const action = new CulturalOfferActions.GetNewslettersSuccess(
        {content: []});
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        newsletters: {content: [{description: '', name: '', picture: ''}]},
        successActionMessage: null,
        errorActionMessage: null
      });
    });
  });
  describe('Get average rating', () => {
    it('should return the default state and set the error and success to null', () => {
      const action = new CulturalOfferActions.GetAverageRating({offerId: 1});
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        errorActionMessage: null,
        successActionMessage: null
      });
    });
  });
  describe('Get average rating success', () => {
    it('should add the average rate to the state', () => {
      const action = new CulturalOfferActions.GetAverageRatingSuccess({rate: 2.2});
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        averageRating: action.payload.rate,
        successActionMessage: null,
        errorActionMessage: null
      });
    });
  });
  describe('Rate', () => {
    it('should return the default state and set the error and success to null', () => {
      const action = new CulturalOfferActions.Rate({offerId: 1, rate: 2.0, registeredId: 1});
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        successActionMessage: null,
        errorActionMessage: null
      });
    });
  });
  describe('Already rated', () => {
    it('should return the default state and set the error and success to null', () => {
      const action = new CulturalOfferActions.AlreadyRated(1);
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        successActionMessage: null,
        errorActionMessage: null
      });
    });
  });
  describe('Already rated success', () => {
    it('should add the rate to the state if already rated', () => {
      const action = new CulturalOfferActions.AlreadyRatedSuccess({rate: 2.0});
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        alreadyRated: action.payload.rate,
        successActionMessage: null,
        errorActionMessage: null
      });
    });
  });
  describe('Create comment', () => {
    it('should return the default state and set the error and success to null', () => {
      const action = new CulturalOfferActions.CreateComment(
        {description: 'description', publishedDate: new Date(), registeredId: 1, culturalOfferId: 1, picturesId: ['picture']});
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        successActionMessage: null,
        errorActionMessage: null
      });
    });
  });
  describe('Subscribe', () => {
    it('should return the default state and set the error and success to null', () => {
      const action = new CulturalOfferActions.Subscribe({offerId: 1, userId: 1});
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        successActionMessage: null,
        errorActionMessage: null
      });
    });
  });
  describe('Unsubscribe', () => {
    it('should return the default state and set the error and success to null', () => {
      const action = new CulturalOfferActions.Unsubscribe({offerId: 1, userId: 1});
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        successActionMessage: null,
        errorActionMessage: null
      });
    });
  });
  describe('Already subscribed', () => {
    it('should return the default state and set the error and success to null', () => {
      const action = new CulturalOfferActions.AlreadySubscribed(1);
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        successActionMessage: null,
        errorActionMessage: null
      });
    });
  });
  describe('Already subscribed value', () => {
    it('should set the alreadySubscribed', () => {
      const action = new CulturalOfferActions.AlreadySubscribedValue(true);
      const result = CulturalOfferReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        alreadySubscribed: action.payload,
        successActionMessage: null,
        errorActionMessage: null
      });
    });
  });
});
