import {ActionReducer, ActionReducerMap, MetaReducer} from '@ngrx/store';
import { localStorageSync } from 'ngrx-store-localstorage';

import * as fromAuth from '../features/sign-in/store/sign-in.reducer';
import * as fromSignUp from '../features/sign-up/store/sign-up.reducer';
import * as fromActivate from '../features/activate-account/store/activate-account.reducer';
import * as fromAdmin from '../features/user-administration/administrator-administration/store/administrator.reducer';
import * as fromReg from '../features/user-administration/registered-administration/store/registered.reducer';
import * as fromNewsletter from '../features/newsletter-administration/store/newsletter.reducer';
import * as fromCategory from '../features/category-administration/store/category.reducer';
import * as fromSubcategory from '../features/subcategory-administration/store/subcategory.reducer';
import * as fromCulturalOffer from '../features/cultural-offer-administration/store/cultural-offer.reducer';

export interface AppState {
  auth: fromAuth.State;
  signUp: fromSignUp.State;
  activate: fromActivate.State;
  administrator: fromAdmin.State;
  registered: fromReg.State;
  newsletter: fromNewsletter.State;
  category: fromCategory.State;
  subcategory: fromSubcategory.State;
  culturaloffer: fromCulturalOffer.State;
}

export const appReducer: ActionReducerMap<AppState> = {
  auth: fromAuth.signInReducer,
  signUp: fromSignUp.signUpReducer,
  activate: fromActivate.activateAccountReducer,
  administrator: fromAdmin.administratorReducer,
  registered: fromReg.registeredReducer,
  category: fromCategory.categoryReducer,
  subcategory: fromSubcategory.subcategoryReducer,
  newsletter: fromNewsletter.newsletterReducer,
  culturaloffer: fromCulturalOffer.CulturalOfferReducer
};

const reducerKeys = ['user', 'signed-in-user'];
export function localStorageSyncReducer(reducer: ActionReducer<any>): ActionReducer<any> {
  return localStorageSync({keys: reducerKeys})(reducer);
}
export const metaReducers: MetaReducer<AppState>[] = [localStorageSyncReducer];
