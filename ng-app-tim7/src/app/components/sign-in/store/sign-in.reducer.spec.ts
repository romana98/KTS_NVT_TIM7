import {initialState, signInReducer} from './sign-in.reducer';
import * as AuthActions from './sign-in.actions';
import {SignedInModel} from '../../../models/signed-in.model';

describe('Sign-in Reducer', () => {

  describe('undefined action', () => {
    it('should return the default state', () => {
      const action = { type: 'NOOP' } as any;
      const result = signInReducer(undefined, action);

      expect(result).toEqual(initialState);
    });
  });

  describe('[Auth] Sign in', () => {
    it('should start sign in user', () => {
      const action = new AuthActions.SignInStart({ username: 'username', password: 'password' });
      const result = signInReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        authError: null,
      });
    });
  });

  describe('[Auth] Sign-in fail', () => {
    it('should toggle error state for sign in', () => {
      const action = new AuthActions.AuthenticateFail('fail message');
      const result = signInReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        user: null,
        authError: action.payload,
      });
    });
  });

  describe('[Auth] Sign-in success', () => {
    it('should sign-in user', () => {
      const action = new AuthActions.AuthenticateSuccess({ username: 'username', id: 1, accessToken: 'token', role: 'role' });
      const result = signInReducer(initialState, action);

      const user = new SignedInModel(
        action.payload.username,
        action.payload.id,
        action.payload.accessToken,
        action.payload.role
      );
      expect(result).toEqual({
        ...initialState,
        user
      });
    });
  });

  describe('[Auth] Sign out', () => {
    it('should sign out user', () => {
      const action = new AuthActions.SignOut();
      const result = signInReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        user: null
      });
    });
  });

  describe('[Auth] Clear Error', () => {
    it('should clear error state for sign in', () => {
      const action = new AuthActions.ClearError();
      const result = signInReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        authError: null
      });
    });
  });

});
