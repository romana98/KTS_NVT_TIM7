import {ActionReducer, ActionReducerMap, MetaReducer} from '@ngrx/store';
import { localStorageSync } from 'ngrx-store-localstorage';

import * as fromAuth from '../components/sign-in/store/sign-in.reducer';

export interface AppState {
  auth: fromAuth.State;
}

export const appReducer: ActionReducerMap<AppState> = {
  auth: fromAuth.signInReducer
};

const reducerKeys = ['user'];
export function localStorageSyncReducer(reducer: ActionReducer<any>): ActionReducer<any> {
  return localStorageSync({keys: reducerKeys})(reducer);
}
export const metaReducers: MetaReducer<AppState>[] = [localStorageSyncReducer];
