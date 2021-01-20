import {initialState, administratorReducer} from './administrator.reducer';
import * as AdminActions from './administrator.actions';

describe('Administrator Reducer', () => {

  describe('undefined action', () => {
    it('should return the default state', () => {
      const action = { type: 'NOOP' } as any;
      const result = administratorReducer(undefined, action);

      expect(result).toEqual(initialState);
    });
  });

  describe('[Administrator] Get admin', () => {
    it('should get administrator info', () => {
      const action = new AdminActions.GetUser();
      const result = administratorReducer(initialState, action);

      expect(result).toEqual({
        ...initialState
      });
    });
  });

  describe('[Administrator] Get admin page', () => {
    it('should get administrators - pagination', () => {
      const action = new AdminActions.GetAdminPage({ page: 0, size: 10 });
      const result = administratorReducer(initialState, action);

      expect(result).toEqual({
        ...initialState
      });
    });
  });

  describe('[Administrator] Delete admin', () => {
    it('should delete administrator', () => {
      const action = new AdminActions.DeleteAdmin(1);
      const result = administratorReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        error: null,
        success: null
      });
    });
  });

  describe('[Administrator] Admin fail', () => {
    it('should set error message', () => {
      const action = new AdminActions.AdminFail('error message');
      const result = administratorReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        error: action.payload
      });
    });
  });

  describe('[Administrator] Admin success', () => {
    it('should set success message', () => {
      const action = new AdminActions.AdminSuccess('success message');
      const result = administratorReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        success: action.payload,
        bar: false
      });
    });
  });

  describe('[Administrator] Admin success edit', () => {
    it('should set edit administrator success message', () => {
      const action = new AdminActions.AdminSuccessEdit('success message');
      const result = administratorReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        success: action.payload,
        bar: false
      });
    });
  });

  describe('[Administrator] Add admin', () => {
    it('should add administrator', () => {
      const action = new AdminActions.AddAdmin({username: 'username', email: 'email', password: 'password'});
      const result = administratorReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        bar: true
      });
    });
  });

  describe('[Administrator] Edit admin', () => {
    it('should edit administrator profile', () => {
      const action = new AdminActions.EditProfile({id: 1, username: 'username', email: 'email', password: 'password'});
      const result = administratorReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        bar: true
      });
    });
  });

  describe('[Administrator] Get admins success', () => {
    it('should set administrators', () => {
      const action = new AdminActions.GetAdminsSuccess('success message');
      const result = administratorReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        admins: action.payload
      });
    });
  });

  describe('[Administrator] Get admin success', () => {
    it('should set administrator', () => {
      const action = new AdminActions.GetAdminSuccess({username: 'username', email: 'email', password: 'password'});
      const result = administratorReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        user: action.payload
      });
    });
  });

  describe('[Administrator] Clear Success', () => {
    it('should clear success message', () => {
      const action = new AdminActions.ClearSuccess();
      const result = administratorReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        error: null
      });
    });
  });

  describe('[Administrator] Clear Error', () => {
    it('should clear error message', () => {
      const action = new AdminActions.ClearError();
      const result = administratorReducer(initialState, action);

      expect(result).toEqual({
        ...initialState,
        error: null
      });
    });
  });
});
