import { ActionReducerMap } from '@ngrx/store';

import * as fromAuth from '../components/sign-in/store/sign-in.reducer';

export interface AppState {
  auth: fromAuth.State;
}

export const appReducer: ActionReducerMap<AppState> = {
  auth: fromAuth.signInReducer
};
