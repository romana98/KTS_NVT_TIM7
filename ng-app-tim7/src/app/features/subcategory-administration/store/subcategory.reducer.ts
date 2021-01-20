import {SubcategoryModel} from '../../../models/subcategory.model';
import * as SubcategoryActions from './subcategory.actions';

export interface State {
  subcategories: any;
  subcategory: SubcategoryModel;
  error: string;
  success: string;
  categoriesSelect: any;
}

export const initialState: State = {
  subcategories: {content: []},
  subcategory: new SubcategoryModel(-1, '', -1, ''),
  error: null,
  success: null,
  categoriesSelect: {content: []}
};

export function subcategoryReducer(state = initialState, action: SubcategoryActions.SubcategoryActions){
  switch (action.type) {
    case SubcategoryActions.GET_CATEGORIES_PAGE_SUCCESS:
      const newArray = {content: state.categoriesSelect.content.concat(action.payload.content)};
      return{
        ...state,
        categoriesSelect: newArray
      };
    case SubcategoryActions.EDIT_SUBCATEGORY_SUCCESS:
      return{
        ...state,
        success: action.payload,
        subcategory: new SubcategoryModel(-1, '', -1, '')
      };
    case SubcategoryActions.SUBCATEGORY_SUCCESS:
      return{
        ...state,
        success: action.payload,
        categoriesSelect: {content: []}
      };
    case SubcategoryActions.EDIT_SUBCATEGORY:
      return{
        ...state
      };
    case SubcategoryActions.GET_SUBCATEGORY_SUCCESS:
      return{
        ...state,
        subcategory: action.payload
      };
    case SubcategoryActions.GET_SUBCATEGORY:
      return{
        ...state
      };
    case SubcategoryActions.DELETE_SUBCATEGORY:
      return{
        ...state
      };
    case SubcategoryActions.CLEAR_ERROR:
      return{
        ...state,
        error: null
      };
    case SubcategoryActions.CLEAR_SUCCESS:
      return{
        ...state,
        success: null
      };
    case SubcategoryActions.ADD_SUBCATEGORY:
      return {
        ...state
      };
    case SubcategoryActions.GET_SUBCATEGORIES_PAGE_SUCCESS:
      return {
        ...state,
        subcategories: action.payload
      };
    case SubcategoryActions.SUBCATEGORY_FAIL:
      return {
        ...state,
        error: action.payload
      };
    case SubcategoryActions.GET_CATEGORIES_PAGE:
    case SubcategoryActions.GET_SUBCATEGORIES_PAGE:
    default:
      return {
        ...state
      };

  }
}
