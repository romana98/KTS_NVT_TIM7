import {ActionReducer, ActionReducerMap, MetaReducer} from '@ngrx/store';
import { localStorageSync } from 'ngrx-store-localstorage';

import * as fromAuth from '../components/sign-in/store/sign-in.reducer';
import * as fromSignUp from '../components/sign-up/store/sign-up.reducer';
import * as fromActivate from '../components/activate-account/store/activate-account.reducer';
import * as fromAdmin from '../components/administrator/store/administrator.reducer';
import * as fromReg from '../components/registered/store/registered.reducer';
import * as fromNewsletter from '../components/newsletter/store/newsletter.reducer';
import * as fromCategory from '../components/category/store/category.reducer';
import * as fromSubcategory from '../components/subcategory/store/subcategory.reducer';

export interface AppState {
  auth: fromAuth.State;
  signUp: fromSignUp.State;
  activate: fromActivate.State;
  administrator: fromAdmin.State;
  registered: fromReg.State;
  newsletter: fromNewsletter.State;
  category: fromCategory.State;
  subcategory: fromSubcategory.State;
}

export const appReducer: ActionReducerMap<AppState> = {
  auth: fromAuth.signInReducer,
  signUp: fromSignUp.signUpReducer,
  activate: fromActivate.activateAccountReducer,
  administrator: fromAdmin.administratorReducer,
  registered: fromReg.registeredReducer,
  category: fromCategory.categoryReducer,
  subcategory: fromSubcategory.subcategoryReducer
  newsletter: fromNewsletter.newsletterReducer,
};

const reducerKeys = ['user', 'signed-in-user'];
export function localStorageSyncReducer(reducer: ActionReducer<any>): ActionReducer<any> {
  return localStorageSync({keys: reducerKeys})(reducer);
}
export const metaReducers: MetaReducer<AppState>[] = [localStorageSyncReducer];
