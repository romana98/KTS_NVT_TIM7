import {initialState, categoryReducer} from './category.reducer';
import * as CategoryActions from './category.actions';
import {CategoryModel} from '../../../models/category.model';

describe('Category Reducer', () => {

  describe('undefined action', () => {
    it('should return the default state', () => {
      const action = { type: 'NOOP' } as any;
      const result = categoryReducer(undefined, action);
      expect(result).toEqual(initialState);
    });
  });
  describe('[Category] Get categories page', () => {
    it('should return the default state', () => {
      const action = new CategoryActions.GetCategoriesPage({page: 0, size: 10});
      const result = categoryReducer(initialState, action);
      expect(result).toEqual(initialState);
    });
  });
  describe('[Category] Get categories page success', () => {
    it('should add the fetched categories to state', () => {
      const action = new CategoryActions.GetCategoriesByPageSuccess({content: [{id: 1, name: 'category'}]});
      const result = categoryReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        categories: action.payload
      });
    });
  });
  describe('[Category] Fail', () => {
    it('should add the error message to state', () => {
      const action = new CategoryActions.CategoryFail('error message');
      const result = categoryReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        error: action.payload
      });
    });
  });
  describe('[Category] Add category', () => {
    it('should return the default state', () => {
      const action = new CategoryActions.AddCategory({name: 'new category'});
      const result = categoryReducer(initialState, action);
      expect(result).toEqual(initialState);
    });
  });
  describe('[Category] Clear success', () => {
    it('should clear the success attribute in state', () => {
      const action = new CategoryActions.ClearSuccess();
      const result = categoryReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        success: null
      });
    });
  });
  describe('[Category] Clear error', () => {
    it('should clear the error attribute in state', () => {
      const action = new CategoryActions.ClearError();
      const result = categoryReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        error: null
      });
    });
  });
  describe('[Category] Delete category', () => {
    it('should return the default state', () => {
      const action = new CategoryActions.DeleteCategory(1);
      const result = categoryReducer(initialState, action);
      expect(result).toEqual(initialState);
    });
  });
  describe('[Category] Get category', () => {
    it('should return the default state', () => {
      const action = new CategoryActions.GetCategory(1);
      const result = categoryReducer(initialState, action);
      expect(result).toEqual(initialState);
    });
  });
  describe('[Category] Get category success', () => {
    it('should add the fetched category to the state', () => {
      const foundCategory = new CategoryModel(1, 'category');
      const action = new CategoryActions.GetCategorySuccess(foundCategory);
      const result = categoryReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        category: foundCategory
      });
    });
  });
  describe('[Category] Edit category', () => {
    it('should return the default state', () => {
      const action = new CategoryActions.EditCategory(new CategoryModel(1, 'new name'));
      const result = categoryReducer(initialState, action);
      expect(result).toEqual(initialState);
    });
  });
  describe('[Category] Success', () => {
    it('should add the success message to state', () => {
      const action = new CategoryActions.CategorySuccess('success message');
      const result = categoryReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        success: action.payload
      });
    });
  });
  describe('[Category] Edit category success', () => {
    it('should add the success message to state, as well as reset the category', () => {
      const action = new CategoryActions.EditCategorySuccess('success message');
      const result = categoryReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        success: action.payload,
        category: new CategoryModel(-1, '')
      });
    });
  });
});
