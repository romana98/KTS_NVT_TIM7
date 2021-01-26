import {Observable, of} from 'rxjs';
import {Action} from '@ngrx/store';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {provideMockActions} from '@ngrx/effects/testing';
import {Router} from '@angular/router';
import {RegisteredEffects} from './registered.effects';
import * as RegisteredActions from './registered.actions';
import {UserModel} from '../../../../models/user.model';


describe('RegisteredEffects', () => {
  let actions$: Observable<Action>;
  let effects: RegisteredEffects;
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
        RegisteredEffects,
        provideMockActions(() => actions$),
        { provide: Router, useValue: routerMock }
      ]
    });

    effects = TestBed.inject(RegisteredEffects);
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
    it('should return an GetRegisteredSuccess action, with the registered data, on success', () => {
      actions$ = of(new RegisteredActions.GetUser());
      const user = new UserModel('mico', 'mico@admin.com', null );
      effects.registered.subscribe(action => {
        expect(action).toEqual(new RegisteredActions.GetRegisteredSuccess(user));
        expect(localStorage.getItem('signed-in-user')).toEqual(JSON.stringify(user));
      });

      const req = http.expectOne('http://localhost:8080/registered-users/' + JSON.parse(localStorage.getItem('user')).id);
      expect(req.request.method).toEqual('GET');
      req.flush(user);
    });

    it('should return an RegisteredFail action, with the registered data, on error', () => {
      actions$ = of(new RegisteredActions.GetUser());
      const user = new UserModel('mico', 'mico@admin.com', null );
      effects.registered.subscribe(action => {
        expect(action).toEqual(new RegisteredActions.RegisteredFail('Bad request'));
        expect(localStorage.getItem('signed-in-user')).toEqual(null);
      });

      const req = http.expectOne('http://localhost:8080/registered-users/' + JSON.parse(localStorage.getItem('user')).id);
      expect(req.request.method).toEqual('GET');
      req.flush( 'Bad request', { status: 400, statusText: 'Bad request' });
    });
  });

  describe('Edit user', () => {
    it('should return an RegisteredSuccess action, with the registered data, on success (changed password)', () => {

      const user = {id: JSON.parse(localStorage.getItem('user')).id, username: 'mico', email: 'mico1@admin.com', password: 'password' };
      actions$ = of(new RegisteredActions.EditProfile(user));

      effects.edit.subscribe(action => {
        expect(action).toEqual(new RegisteredActions.RegisteredSuccess('Profile updated.'));
      });

      const req = http.expectOne('http://localhost:8080/registered-users');
      expect(req.request.method).toEqual('PUT');
      expect(req.request.body).toEqual(user);
      req.flush('');
    });

    it('should return an RegisteredSuccess action, with the registered data, on success (not changed password)', () => {

      const user = {id: JSON.parse(localStorage.getItem('user')).id, username: 'mico', email: 'mico1@admin.com', password: '' };
      const userBody = {id: JSON.parse(localStorage.getItem('user')).id, username: 'mico', email: 'mico1@admin.com', password: '________' };
      actions$ = of(new RegisteredActions.EditProfile(user));

      effects.edit.subscribe(action => {
        expect(action).toEqual(new RegisteredActions.RegisteredSuccess('Profile updated.'));
      });

      const req = http.expectOne('http://localhost:8080/registered-users');
      expect(req.request.method).toEqual('PUT');
      expect(req.request.body).toEqual(userBody);
      req.flush('');
    });

    it('should return an RegisteredFail action, with the registered data, on error', () => {

      const user = {id: JSON.parse(localStorage.getItem('user')).id, username: 'mico', email: 'mico1@admin.com', password: 'password' };
      actions$ = of(new RegisteredActions.EditProfile(user));

      effects.edit.subscribe(action => {
        expect(action).toEqual(new RegisteredActions.RegisteredFail('Bad request'));
      });

      const req = http.expectOne('http://localhost:8080/registered-users');
      expect(req.request.method).toEqual('PUT');
      expect(req.request.body).toEqual(user);
      req.flush( 'Bad request', { status: 400, statusText: 'Bad request' });
    });
  });

  describe('Register success', () => {
    it('should navigate to view profile page', () => {
      actions$ = of(new RegisteredActions.RegisteredSuccess('Profile updated.'));

      effects.registeredRedirect.subscribe(() => {
        expect(router.navigate).toHaveBeenCalledWith(['/registered/view-profile']);
      });
    });
  });
});
