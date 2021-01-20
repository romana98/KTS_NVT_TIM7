import { provideMockActions } from '@ngrx/effects/testing';
import { TestBed } from '@angular/core/testing';
import {Observable, of} from 'rxjs';
import * as SubcategoryActions from './subcategory.actions';
import { SubcategoryEffects } from './subcategory.effects';
import {Action} from '@ngrx/store';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {SubcategoryModel} from '../../../models/subcategory.model';

describe('SubcategoryEffects', () => {
  let actions$: Observable<Action>;
  let effects: SubcategoryEffects;
  let http: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        SubcategoryEffects,
        provideMockActions(() => actions$)
      ]
    });

    effects = TestBed.inject(SubcategoryEffects);
    http = TestBed.inject(HttpTestingController);
  });
  afterEach(() => {
    http.verify();
  });
  it('should be created', () => {
    expect(effects).toBeTruthy();
  });
  describe('Get subcategories by page', () => {
    it('SUCCESS: should return a GetSubcategoriesByPageSuccess action with page of subcategories as a payload', () => {
      actions$ = of(new SubcategoryActions.GetSubcategoriesPage({page: 0, size: 10}));
      effects.subcategories.subscribe(action => {
        expect(action).toEqual(new SubcategoryActions.GetSubcategoriesByPageSuccess({content: [{id: 1, name: 'subcategory', categoryName: 'category', categoryId: 2}]}));
      });
      const req = http.expectOne('http://localhost:8080/subcategories/by-page?page=0&size=10');
      expect(req.request.method).toEqual('GET');
      req.flush({content: [{id: 1, name: 'subcategory', categoryName: 'category', categoryId: 2}]});
    });
    it('ERROR: should return a SubcategoryFail action with the error message', () => {
      actions$ = of(new SubcategoryActions.GetSubcategoriesPage({page: 0, size: 10}));
      effects.subcategories.subscribe(action => {
        expect(action).toEqual(new SubcategoryActions.SubcategoryFail('An unknown error has occurred!'));
      });
      const req = http.expectOne('http://localhost:8080/subcategories/by-page?page=0&size=10');
      expect(req.request.method).toEqual('GET');
      req.flush('An unknown error has occurred!', { status: 401, statusText: 'Unauthorized' });
    });
  });
  describe('Add subcategory', () => {
    it('SUCCESS: should return a SubcategorySuccess action with the success message', () => {
      actions$ = of(new SubcategoryActions.AddSubcategory({name: 'new subcategory', categoryName: 'category'}));
      effects.add.subscribe(action => {
        expect(action).toEqual(new SubcategoryActions.SubcategorySuccess('Subcategory added successfully.'));
      });
      const req = http.expectOne('http://localhost:8080/subcategories');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({name: 'new subcategory', categoryName: 'category'});
      req.flush('Subcategory added successfully.');
    });
    it('ERROR: should return a CategoryFail action with the error message', () => {
      actions$ = of(new SubcategoryActions.AddSubcategory({name: 'exist category name', categoryName: 'category'}));
      effects.add.subscribe(action => {
        expect(action).toEqual(new SubcategoryActions.SubcategoryFail('Subcategory with that name already exists!'));
      });
      const req = http.expectOne('http://localhost:8080/subcategories');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({name: 'exist category name', categoryName: 'category'});
      req.flush('Subcategory with that name already exists!', { status: 400, statusText: 'Bad Request' });
    });
  });
  describe('Delete subcategory', () => {
    it('SUCCESS: should return a SubcategorySuccess action with the success message', () => {
      actions$ = of(new SubcategoryActions.DeleteSubcategory(1));
      effects.delete.subscribe(action => {
        expect(action).toEqual(new SubcategoryActions.SubcategorySuccess('Subcategory deleted successfully.'));
      });
      const req = http.expectOne('http://localhost:8080/subcategories/1');
      expect(req.request.method).toEqual('DELETE');
      req.flush('Subcategory deleted successfully.');
    });
    it('ERROR: should return a SubcategoryFail action with the error message', () => {
      actions$ = of(new SubcategoryActions.DeleteSubcategory(1));
      effects.delete.subscribe(action => {
        expect(action).toEqual(new SubcategoryActions.SubcategoryFail(
          'Deleting failed! Check if selected subcategory has its cultural offers!'));
      });
      const req = http.expectOne('http://localhost:8080/subcategories/1');
      expect(req.request.method).toEqual('DELETE');
      req.flush('Deleting failed! Check if selected subcategory has its cultural offers!', { status: 400, statusText: 'Bad Request' });
    });
  });
  describe('Get subcategory', () => {
    it('SUCCESS: should return a GetSubcategorySuccess action with the fetched subccategory', () => {
      actions$ = of(new SubcategoryActions.GetSubcategory(1));
      effects.subcategory.subscribe(action => {
        expect(action).toEqual(new SubcategoryActions.GetSubcategorySuccess(new SubcategoryModel(1, 'subcategory', 1, 'category')));
      });
      const req = http.expectOne('http://localhost:8080/subcategories/by-id/1');
      expect(req.request.method).toEqual('GET');
      req.flush(new SubcategoryModel(1, 'subcategory', 1, 'category'));
    });
    it('ERROR: should return a SubcategoryFail action with the error message', () => {
      actions$ = of(new SubcategoryActions.GetSubcategory(1));
      effects.subcategory.subscribe(action => {
        expect(action).toEqual(new SubcategoryActions.SubcategoryFail('An unknown error has occurred!'));
      });
      const req = http.expectOne('http://localhost:8080/subcategories/by-id/1');
      expect(req.request.method).toEqual('GET');
      req.flush('An unknown error has occurred!', { status: 400, statusText: 'Bad Request' });
    });
  });
  describe('Edit subcategory', () => {
    it('SUCCESS: should return a EditSubcategorySuccess action with the success message', () => {
      actions$ = of(new SubcategoryActions.EditSubcategory({id: 1, name: 'new subcategory name', categoryName: 'category', categoryId: 1}));
      effects.edit.subscribe(action => {
        expect(action).toEqual(new SubcategoryActions.EditSubcategorySuccess('Subcategory updated successfully.'));
      });
      const req = http.expectOne('http://localhost:8080/subcategories');
      expect(req.request.method).toEqual('PUT');
      expect(req.request.body).toEqual({id: 1, name: 'new subcategory name', categoryName: 'category', categoryId: 1});
      req.flush('Subcategory updated successfully.');
    });
    it('ERROR: should return a SubcategoryFail action with the error message', () => {
      actions$ = of(new SubcategoryActions.EditSubcategory({id: 1, name: 'new subcategory name', categoryName: 'category', categoryId: 1}));
      effects.edit.subscribe(action => {
        expect(action).toEqual(new SubcategoryActions.SubcategoryFail('Subcategory with that name already exists!'));
      });
      const req = http.expectOne('http://localhost:8080/subcategories');
      expect(req.request.method).toEqual('PUT');
      expect(req.request.body).toEqual({id: 1, name: 'new subcategory name', categoryName: 'category', categoryId: 1});
      req.flush('Subcategory with that name already exists!', { status: 400, statusText: 'Bad Request' });
    });
  });
  describe('Get categories by page', () => {
    it('SUCCESS: should return a GetCategoriesByPageSuccess action with page of categories as a payload', () => {
      actions$ = of(new SubcategoryActions.GetCategoriesPage({page: 0, size: 10}));
      effects.categoriesSelect.subscribe(action => {
        expect(action).toEqual(new SubcategoryActions.GetCategoriesByPageSuccess({content: [{id: 1, name: 'category'}]}));
      });
      const req = http.expectOne('http://localhost:8080/categories/by-page?page=0&size=10');
      expect(req.request.method).toEqual('GET');
      req.flush({content: [{id: 1, name: 'category'}]});
    });
    it('ERROR: should return a CategoryFail action with the error message', () => {
      actions$ = of(new SubcategoryActions.GetCategoriesPage({page: 0, size: 10}));
      effects.categoriesSelect.subscribe(action => {
        expect(action).toEqual(new SubcategoryActions.SubcategoryFail('An unknown error has occurred!'));
      });
      const req = http.expectOne('http://localhost:8080/categories/by-page?page=0&size=10');
      expect(req.request.method).toEqual('GET');
      req.flush('An unknown error has occurred!', { status: 401, statusText: 'Unauthorized' });
    });
  });
});
