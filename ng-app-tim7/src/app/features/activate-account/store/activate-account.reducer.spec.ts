import * as ActivateAccActions from './activate-account.actions';
import {initialState, activateAccountReducer} from './activate-account.reducer';

describe('Activate Account Reducer', () => {

  describe('undefined action', () => {
    it('should return the default state', () => {
      const action = { type: 'NOOP' } as any;
      const result = activateAccountReducer(undefined, action);

      expect(result).toEqual(initialState);
    });
  });

  describe('[Activate] Activate account', () => {
    it('should activate user account', () => {
      const action = new ActivateAccActions.ActivateStart({id: 10});
      const result = activateAccountReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        error: null,
        success: null
      });
    });
  });

  describe('[Activate] Activation success', () => {
    it('should set success message', () => {
      const action = new ActivateAccActions.ActivateSuccess('success message');
      const result = activateAccountReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        success: action.payload
      });
    });
  });

  describe('[Activate] Activation fail', () => {
    it('should set error message', () => {
      const action = new ActivateAccActions.ActivateFail('error message');
      const result = activateAccountReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        error: action.payload
      });
    });
  });

  describe('[Activate] Clear Error', () => {
    it('should clear error message', () => {
      const action = new ActivateAccActions.ClearError();
      const result = activateAccountReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        error: null
      });
    });
  });

  describe('[Activate] Clear Success', () => {
    it('should clear error message', () => {
      const action = new ActivateAccActions.ClearSuccess();
      const result = activateAccountReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        success: null
      });
    });
  });
});
