import {initialState, registeredReducer} from './registered.reducer';
import * as RegisteredActions from './registered.actions';

describe('Registered Reducer', () => {

  describe('undefined action', () => {
    it('should return the default state', () => {
      const action = { type: 'NOOP' } as any;
      const result = registeredReducer(undefined, action);

      expect(result).toEqual(initialState);
    });
  });

  describe('[Registered] Get registered', () => {
    it('should start getting registered user', () => {
      const action = new RegisteredActions.GetUser();
      const result = registeredReducer(initialState, action);

      expect(result).toEqual({
        ...initialState
      });
    });
  });

  describe('[Registered] Edit registered', () => {
    it('should edit registered user', () => {
      const action = new RegisteredActions.EditProfile({ id: 1, username: 'username', email: 'email', password: 'password' });
      const result = registeredReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        bar: false
      });
    });
  });

  describe('[Registered] Get registered success', () => {
    it('should get registered user', () => {
      const user = { username: 'username', email: 'email', password: 'password' };
      const action = new RegisteredActions.GetRegisteredSuccess(user);
      const result = registeredReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        user: action.payload
      });
    });
  });

  describe('[Registered] Registered success', () => {
    it('should set success message', () => {
      const action = new RegisteredActions.RegisteredSuccess('success message');
      const result = registeredReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        success: action.payload,
        bar: false
      });
    });
  });

  describe('[Registered] Registered fail', () => {
    it('should set error message', () => {
      const action = new RegisteredActions.RegisteredFail('error message');
      const result = registeredReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        error: action.payload,
        bar: false
      });
    });
  });

  describe('[Registered] Clear Success', () => {
    it('should clear success message', () => {
      const action = new RegisteredActions.ClearSuccess();
      const result = registeredReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        error: null
      });
    });
  });

  describe('[Registered] Clear Error', () => {
    it('should clear error message', () => {
      const action = new RegisteredActions.ClearError();
      const result = registeredReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        error: null
      });
    });
  });
});
