import * as CatActions from './category.actions';
import {CategoryModel} from '../../../models/category.model';

export interface State {
  categories: any;
  category: CategoryModel;
  error: string;
  success: string;
}

const initialState: State = {
  categories: {content: []},
  category: new CategoryModel(-1, ''),
  error: null,
  success: null
};

export function categoryReducer(state = initialState, action: CatActions.CategoryActions){
  switch (action.type) {
    case CatActions.EDIT_CATEGORY_SUCCESS:
      return{
        ...state,
        success: action.payload,
        category: new CategoryModel(-1, '')
      };
    case CatActions.CATEGORY_SUCCESS:
      return{
        ...state,
        success: action.payload
      };
    case CatActions.EDIT_CATEGORY:
      return{
        ...state
      };
    case CatActions.GET_CATEGORY_SUCCESS:
      return{
        ...state,
        category: action.payload
      };
    case CatActions.GET_CATEGORY:
      return{
        ...state
      };
    case CatActions.DELETE_CATEGORY:
      return{
        ...state
      };
    case CatActions.CLEAR_ERROR:
      return{
        ...state,
        error: null
      };
    case CatActions.CLEAR_SUCCESS:
      return{
        ...state,
        success: null
      };
    case CatActions.ADD_CATEGORY:
      return {
        ...state
      };
    case CatActions.GET_CATEGORIES_PAGE_SUCCESS:
      return {
        ...state,
        categories: action.payload
      };
    case CatActions.CATEGORY_FAIL:
      return {
        ...state,
        error: action.payload
      };
    case CatActions.GET_CATEGORIES_PAGE:
    default:
      return {
        ...state
      };

  }
}
