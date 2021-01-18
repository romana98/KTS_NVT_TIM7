import {Observable, of} from 'rxjs';
import {Action} from '@ngrx/store';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {provideMockActions} from '@ngrx/effects/testing';
import {Router} from '@angular/router';
import * as AdminActions from './administrator.actions';
import {UserModel} from '../../../models/user.model';
import {AdministratorEffects} from './administrator.effects';

describe('AdministratorEffects', () => {
  let actions$: Observable<Action>;
  let effects: AdministratorEffects;
  let http: HttpTestingController;
  let router: any;
  const accessToken = 'eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6Im1pY28iLCJhdWQiOiJ3ZWIiLCJpYXQiOjE2MTA4OTIzNDIsImV4cCI6MTYxMDg5NDE0MiwidXNlcm5hbWUiOiJtaWNvIiwiaWQiOjUwMDUsInJvbGUiOiJST0xFX0FETUlOSVNUUkFUT1IifQ.we4NyEH-EJMIRgFCsJAmSOOKJDleKY2h-KB6lB7PXm-0RUtG7mkD_SF-jsGQ-yCARzx-imPDMiQ2G18ZJwKKrg';

  beforeEach(() => {
    const routerMock = {
      navigate: jasmine.createSpy('navigate')
    };
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        AdministratorEffects,
        provideMockActions(() => actions$),
        { provide: Router, useValue: routerMock }
      ]
    });

    effects = TestBed.inject(AdministratorEffects);
    http = TestBed.inject(HttpTestingController);
    router = TestBed.inject(Router);

    let store = {};
    const mockLocalStorage = {
      getItem: (key: string): string => {
        return key in store ? store[key] : null;
      },
      setItem: (key: string, value: string) => {
        store[key] = `${value}`;
      },
      clear: () => {
        store = {};
      }
    };

    spyOn(localStorage, 'getItem')
      .and.callFake(mockLocalStorage.getItem);
    spyOn(localStorage, 'setItem')
      .and.callFake(mockLocalStorage.setItem);
    spyOn(localStorage, 'clear')
      .and.callFake(mockLocalStorage.clear);

    const userLocalStorage = { username: 'mico', id: 5005, accessToken, role: 'ROLE_ADMINISTRATOR' };
    localStorage.setItem('user', JSON.stringify(userLocalStorage));
  });

  afterEach(() => {
    http.verify();
  });

  it('should be created', () => {
    expect(effects).toBeTruthy();
  });

  describe('Get user', () => {
    it('should return an GetAdminSuccess action, with the admin data, on success', () => {
      actions$ = of(new AdminActions.GetUser());
      const user = new UserModel('mico', 'mico@admin.com', null );
      effects.admin.subscribe(action => {
        expect(action).toEqual(new AdminActions.GetAdminSuccess(user));
        expect(localStorage.getItem('signed-in-user')).toEqual(JSON.stringify(user));
      });

      const req = http.expectOne('http://localhost:8080/administrators/' + JSON.parse(localStorage.getItem('user')).id);
      expect(req.request.method).toEqual('GET');
      req.flush(user);
    });

    it('should return an AdminFail action, with the admin data, on error', () => {
      actions$ = of(new AdminActions.GetUser());
      const user = new UserModel('mico', 'mico@admin.com', null );
      effects.admin.subscribe(action => {
        expect(action).toEqual(new AdminActions.AdminFail('Bad request'));
        expect(localStorage.getItem('signed-in-user')).toEqual(null);
      });

      const req = http.expectOne('http://localhost:8080/administrators/' + JSON.parse(localStorage.getItem('user')).id);
      expect(req.request.method).toEqual('GET');
      req.flush( 'Bad request', { status: 400, statusText: 'Bad request' });
    });
  });

  describe('Get administrators', () => {
    it('should return an GetAdminsSuccess action, with the admin data, on success', () => {
      actions$ = of(new AdminActions.GetAdminPage({page: 0, size: 10}));
      const user = new UserModel('mico', 'mico@admin.com', null );
      effects.admins.subscribe(action => {
        expect(action).toEqual(new AdminActions.GetAdminsSuccess(user));
      });

      const req = http.expectOne('http://localhost:8080/administrators/by-page?page=' + 0 + '&size=' + 10);
      expect(req.request.method).toEqual('GET');
      req.flush(user);
    });

    it('should return an AdminFail action, with the admin data, on error', () => {
      actions$ = of(new AdminActions.GetAdminPage({page: 0, size: 10}));
      const user = new UserModel('mico', 'mico@admin.com', null );
      effects.admins.subscribe(action => {
        expect(action).toEqual(new AdminActions.AdminFail('Bad request'));
      });

      const req = http.expectOne('http://localhost:8080/administrators/by-page?page=' + 0 + '&size=' + 10);
      expect(req.request.method).toEqual('GET');
      req.flush( 'Bad request', { status: 400, statusText: 'Bad request' });
    });
  });

  describe('Edit user', () => {
    it('should return an AdminSuccessEdit action, with the registered data, on success (changed password)', () => {

      const user = {id: JSON.parse(localStorage.getItem('user')).id, username: 'mico', email: 'mico1@admin.com', password: 'password' };
      actions$ = of(new AdminActions.EditProfile(user));

      effects.edit.subscribe(action => {
        expect(action).toEqual(new AdminActions.AdminSuccessEdit('Profile updated.'));
      });

      const req = http.expectOne('http://localhost:8080/administrators');
      expect(req.request.method).toEqual('PUT');
      expect(req.request.body).toEqual(user);
      req.flush('');
    });

    it('should return an AdminSuccessEdit action, with the registered data, on success (not changed password)', () => {

      const user = {id: JSON.parse(localStorage.getItem('user')).id, username: 'mico', email: 'mico1@admin.com', password: '' };
      const userBody = {id: JSON.parse(localStorage.getItem('user')).id, username: 'mico', email: 'mico1@admin.com', password: '________' };
      actions$ = of(new AdminActions.EditProfile(user));

      effects.edit.subscribe(action => {
        expect(action).toEqual(new AdminActions.AdminSuccessEdit('Profile updated.'));
      });

      const req = http.expectOne('http://localhost:8080/administrators');
      expect(req.request.method).toEqual('PUT');
      expect(req.request.body).toEqual(userBody);
      req.flush('');
    });

    it('should return an AdminFail action, with the registered data, on error', () => {

      const user = {id: JSON.parse(localStorage.getItem('user')).id, username: 'mico', email: 'mico1@admin.com', password: 'password' };
      actions$ = of(new AdminActions.EditProfile(user));

      effects.edit.subscribe(action => {
        expect(action).toEqual(new AdminActions.AdminFail('Bad request'));
      });

      const req = http.expectOne('http://localhost:8080/administrators');
      expect(req.request.method).toEqual('PUT');
      expect(req.request.body).toEqual(user);
      req.flush( 'Bad request', { status: 400, statusText: 'Bad request' });
    });
  });

  describe('Add user', () => {
    it('should return an AdminSuccess action, with the registered data, on success (changed password)', () => {

      const user = {username: 'mico', email: 'mico1@admin.com', password: 'password' };
      actions$ = of(new AdminActions.AddAdmin(user));

      effects.add.subscribe(action => {
        expect(action).toEqual(new AdminActions.AdminSuccess('Administrator added.'));
      });

      const req = http.expectOne('http://localhost:8080/administrators');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual(user);
      req.flush('');
    });

    it('should return an AdminSuccess action, with the registered data, on error', () => {

      const user = { username: 'mico', email: 'mico1@admin.com', password: 'password' };
      actions$ = of(new AdminActions.AddAdmin(user));

      effects.add.subscribe(action => {
        expect(action).toEqual(new AdminActions.AdminFail('Bad request'));
      });

      const req = http.expectOne('http://localhost:8080/administrators');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual(user);
      req.flush( 'Bad request', { status: 400, statusText: 'Bad request' });
    });
  });

  describe('Delete user', () => {
    it('should return an AdminSuccess action, with the registered data, on success', () => {

      actions$ = of(new AdminActions.DeleteAdmin(10));

      effects.delete.subscribe(action => {
        expect(action).toEqual(new AdminActions.AdminSuccess('Administrator deleted.'));
      });

      const req = http.expectOne('http://localhost:8080/administrators/' + 10);
      expect(req.request.method).toEqual('DELETE');
      req.flush('');
    });

    it('should return an AdminFail action, with the registered data, on error', () => {

      actions$ = of(new AdminActions.DeleteAdmin(10));

      effects.delete.subscribe(action => {
        expect(action).toEqual(new AdminActions.AdminFail('Bad request'));
      });

      const req = http.expectOne('http://localhost:8080/administrators/' + 10);
      expect(req.request.method).toEqual('DELETE');
      req.flush( 'Bad request', { status: 400, statusText: 'Bad request' });
    });
  });

  describe('Register success', () => {
    it('should navigate to view profile page', () => {
      actions$ = of(new AdminActions.AdminSuccess('Profile updated.'));

      effects.adminSuccessEdit.subscribe(() => {
        expect(router.navigate).toHaveBeenCalledWith(['/registered/view-profile']);
      });
    });
  });
});
