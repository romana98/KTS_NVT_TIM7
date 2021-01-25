import { provideMockActions } from '@ngrx/effects/testing';
import { TestBed } from '@angular/core/testing';
import {Observable, of} from 'rxjs';
import * as CategoryActions from './category.actions';
import { CategoryEffects } from './category.effects';
import {Action} from '@ngrx/store';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {CategoryModel} from '../../../models/category.model';

describe('CategoryEffects', () => {
  let actions$: Observable<Action>;
  let effects: CategoryEffects;
  let http: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        CategoryEffects,
        provideMockActions(() => actions$)
      ]
    });

    effects = TestBed.inject(CategoryEffects);
    http = TestBed.inject(HttpTestingController);
  });
  afterEach(() => {
    http.verify();
  });
  it('should be created', () => {
    expect(effects).toBeTruthy();
  });
  describe('Get categories by page', () => {
    it('SUCCESS: should return a GetCategoriesByPageSuccess action with page of categories as a payload', () => {
      actions$ = of(new CategoryActions.GetCategoriesPage({page: 0, size: 10}));
      effects.categories.subscribe(action => {
        expect(action).toEqual(new CategoryActions.GetCategoriesByPageSuccess({content: [{id:  1, name: 'category'}]}));
      });
      const req = http.expectOne('http://localhost:8080/categories/by-page?page=0&size=10');
      expect(req.request.method).toEqual('GET');
      req.flush({content: [{id: 1, name: 'category'}]});
    });
    it('ERROR: should return a CategoryFail action with the error message', () => {
      actions$ = of(new CategoryActions.GetCategoriesPage({page: 0, size: 10}));
      effects.categories.subscribe(action => {
        expect(action).toEqual(new CategoryActions.CategoryFail('An unknown error has occurred!'));
      });
      const req = http.expectOne('http://localhost:8080/categories/by-page?page=0&size=10');
      expect(req.request.method).toEqual('GET');
      req.flush('An unknown error has occurred!', { status: 401, statusText: 'Unauthorized' });
    });
  });
  describe('Add category', () => {
    it('SUCCESS: should return a CategorySuccess action with the success message', () => {
      actions$ = of(new CategoryActions.AddCategory({name: 'new category'}));
      effects.add.subscribe(action => {
        expect(action).toEqual(new CategoryActions.CategorySuccess('Category added successfully.'));
      });
      const req = http.expectOne('http://localhost:8080/categories');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({name: 'new category'});
      req.flush('Category added successfully.');
    });
    it('ERROR: should return a CategoryFail action with the error message', () => {
      actions$ = of(new CategoryActions.AddCategory({name: 'exist category name'}));
      effects.add.subscribe(action => {
        expect(action).toEqual(new CategoryActions.CategoryFail('Category with that name already exists!'));
      });
      const req = http.expectOne('http://localhost:8080/categories');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({name: 'exist category name'});
      req.flush('Category with that name already exists!', { status: 400, statusText: 'Bad Request' });
    });
  });
  describe('Delete category', () => {
    it('SUCCESS: should return a CategorySuccess action with the success message', () => {
      actions$ = of(new CategoryActions.DeleteCategory(1));
      effects.delete.subscribe(action => {
        expect(action).toEqual(new CategoryActions.CategorySuccess('Category deleted successfully.'));
      });
      const req = http.expectOne('http://localhost:8080/categories/1');
      expect(req.request.method).toEqual('DELETE');
      req.flush('Category deleted successfully.');
    });
    it('ERROR: should return a CategoryFail action with the error message', () => {
      actions$ = of(new CategoryActions.DeleteCategory(1));
      effects.delete.subscribe(action => {
        expect(action).toEqual(new CategoryActions.CategoryFail('Deleting failed! Check if selected category has subcategories!'));
      });
      const req = http.expectOne('http://localhost:8080/categories/1');
      expect(req.request.method).toEqual('DELETE');
      req.flush('Deleting failed! Check if selected category has subcategories!', { status: 400, statusText: 'Bad Request' });
    });
  });
  describe('Get category', () => {
    it('SUCCESS: should return a GetCategorySuccess action with the fetched category', () => {
      actions$ = of(new CategoryActions.GetCategory(1));
      effects.category.subscribe(action => {
        expect(action).toEqual(new CategoryActions.GetCategorySuccess(new CategoryModel(1, 'category')));
      });
      const req = http.expectOne('http://localhost:8080/categories/by-id/1');
      expect(req.request.method).toEqual('GET');
      req.flush(new CategoryModel(1, 'category'));
    });
    it('ERROR: should return a CategoryFail action with the error message', () => {
      actions$ = of(new CategoryActions.GetCategory(1));
      effects.category.subscribe(action => {
        expect(action).toEqual(new CategoryActions.CategoryFail('An unknown error has occurred!'));
      });
      const req = http.expectOne('http://localhost:8080/categories/by-id/1');
      expect(req.request.method).toEqual('GET');
      req.flush('An unknown error has occurred!', { status: 400, statusText: 'Bad Request' });
    });
  });
  describe('Edit category', () => {
    it('SUCCESS: should return a EditCategorySuccess action with the success message', () => {
      actions$ = of(new CategoryActions.EditCategory({id: 1, name: 'new category name'}));
      effects.edit.subscribe(action => {
        expect(action).toEqual(new CategoryActions.EditCategorySuccess('Category updated successfully.'));
      });
      const req = http.expectOne('http://localhost:8080/categories');
      expect(req.request.method).toEqual('PUT');
      expect(req.request.body).toEqual({id: 1, name: 'new category name'});
      req.flush('Category updated successfully.');
    });
    it('ERROR: should return a CategoryFail action with the error message', () => {
      actions$ = of(new CategoryActions.EditCategory({id: 1, name: 'exist category name'}));
      effects.edit.subscribe(action => {
        expect(action).toEqual(new CategoryActions.CategoryFail('Category with that name already exists!'));
      });
      const req = http.expectOne('http://localhost:8080/categories');
      expect(req.request.method).toEqual('PUT');
      expect(req.request.body).toEqual({id: 1, name: 'exist category name'});
      req.flush('Category with that name already exists!', { status: 400, statusText: 'Bad Request' });
    });
  });
});
