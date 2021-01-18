import { initialState, signUpReducer } from './sign-up.reducer';
import * as SignUpActions from './sign-up.actions';

describe('Sign-up Reducer', () => {

  describe('undefined action', () => {
    it('should return the default state', () => {
      const action = { type: 'NOOP' } as any;
      const result = signUpReducer(undefined, action);

      expect(result).toEqual(initialState);
    });
  });

  describe('[SignUp] Sign up', () => {
    it('should start sign up process', () => {
      const action = new SignUpActions.SignUpStart({ username: 'username', email: 'email', password: 'password' });
      const result = signUpReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        error: null,
        success: null,
        bar: true
      });
    });
  });

  describe('[SignUp] Sign-up success', () => {
    it('should sign up user', () => {
      const action = new SignUpActions.SignUpSuccess('success message');
      const result = signUpReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        success: action.payload,
        bar: false
      });
    });
  });

  describe('[SignUp] Sign-up fail', () => {
    it('should toggle error state for sign up', () => {
      const action = new SignUpActions.SignUpFail('fail message');
      const result = signUpReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        error: action.payload,
        bar: false
      });
    });
  });

  describe('[SignUp] Clear Error', () => {
    it('should clear error state for sign up', () => {
      const action = new SignUpActions.ClearError();
      const result = signUpReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        error: null
      });
    });
  });

  describe('[SignUp] Clear Success', () => {
    it('should clear success state for sign up', () => {
      const action = new SignUpActions.ClearSuccess();
      const result = signUpReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        success: null
      });
    });
  });

});
