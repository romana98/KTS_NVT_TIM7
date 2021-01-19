import {initialState, subcategoryReducer} from './subcategory.reducer';
import * as SubcategoryActions from './subcategory.actions';
import {SubcategoryModel} from '../../../models/subcategory.model';

describe('Subcategory Reducer', () => {
  describe('undefined action', () => {
    it('should return the default state', () => {
      const action = { type: 'NOOP' } as any;
      const result = subcategoryReducer(undefined, action);
      expect(result).toEqual(initialState);
    });
  });
  describe('[Subcategory] Get subcategories page', () => {
    it('should return the default state', () => {
      const action = new SubcategoryActions.GetSubcategoriesPage({page: 0, size: 10});
      const result = subcategoryReducer(initialState, action);
      expect(result).toEqual(initialState);
    });
  });
  describe('[Subcategory] Get subcategories page success', () => {
    it('should add the fetched subcategories to state', () => {
      const action = new SubcategoryActions.GetSubcategoriesByPageSuccess({content:
          [{id: 1, name: 'subcategory', categoryName: 'category', categoryId: 1}]});
      const result = subcategoryReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        subcategories: action.payload
      });
    });
  });
  describe('[Subcategory] Fail', () => {
    it('should add the error message to state', () => {
      const action = new SubcategoryActions.SubcategoryFail('error');
      const result = subcategoryReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        error: action.payload
      });
    });
  });
  describe('[Subcategory] Add subcategory', () => {
    it('should return the default state', () => {
      const action = new SubcategoryActions.AddSubcategory({name: 'subcategory', categoryName: 'category name'});
      const result = subcategoryReducer(initialState, action);
      expect(result).toEqual(initialState);
    });
  });
  describe('[Subcategory] Clear success', () => {
    it('should clear the success attribute in state', () => {
      const action = new SubcategoryActions.ClearSuccess();
      const result = subcategoryReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        success: null
      });
    });
  });
  describe('[Subcategory] Clear error', () => {
    it('should clear the error attribute in state', () => {
      const action = new SubcategoryActions.ClearError();
      const result = subcategoryReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        error: null
      });
    });
  });
  describe('[Subcategory] Delete subcategory', () => {
    it('should return the default state', () => {
      const action = new SubcategoryActions.DeleteSubcategory(1);
      const result = subcategoryReducer(initialState, action);
      expect(result).toEqual(initialState);
    });
  });
  describe('[Subcategory] Get subcategory', () => {
    it('should return the default state', () => {
      const action = new SubcategoryActions.GetSubcategory(1);
      const result = subcategoryReducer(initialState, action);
      expect(result).toEqual(initialState);
    });
  });
  describe('[Subcategory] Get subcategory success', () => {
    it('should add the fetched subcategory to the state', () => {
      const foundSubcategory = new SubcategoryModel(1, 'subcategory', 1, 'category');
      const action = new SubcategoryActions.GetSubcategorySuccess(foundSubcategory);
      const result = subcategoryReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        subcategory: foundSubcategory
      });
    });
  });
  describe('[Subcategory] Edit subcategory', () => {
    it('should return the default state', () => {
      const action = new SubcategoryActions.EditSubcategory(new SubcategoryModel(1, 'new name', 1, 'category'));
      const result = subcategoryReducer(initialState, action);
      expect(result).toEqual(initialState);
    });
  });
  describe('[Subcategory] Success', () => {
    it('should add the success message to state', () => {
      const action = new SubcategoryActions.SubcategorySuccess('success message');
      const result = subcategoryReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        success: action.payload
      });
    });
  });
  describe('[Subcategory] Edit subcategory success', () => {
    it('should add the success message to state, as well as reset the subcategory', () => {
      const action = new SubcategoryActions.EditSubcategorySuccess('success message');
      const result = subcategoryReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        success: action.payload,
        subcategory: new SubcategoryModel(-1, '', -1, '')
      });
    });
  });
  describe('[Subcategory] Get categories page', () => {
    it('should return the default state', () => {
      const action = new SubcategoryActions.GetCategoriesPage({page: 0, size: 10});
      const result = subcategoryReducer(initialState, action);
      expect(result).toEqual(initialState);
    });
  });
  describe('[Subcategory] Get categories page success', () => {
    it('should add the fetched categories to state', () => {
      const action = new SubcategoryActions.GetCategoriesByPageSuccess({content: [{id: 1, name: 'category'}]});
      const result = subcategoryReducer(initialState, action);
      expect(result).toEqual({
        ...initialState,
        categoriesSelect: {content: initialState.categoriesSelect.content.concat(action.payload.content)}
      });
    });
  });
});
