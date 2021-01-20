import { provideMockActions } from '@ngrx/effects/testing';
import { TestBed } from '@angular/core/testing';
import {Observable, of} from 'rxjs';
import * as AuthActions from './sign-in.actions';
import { AuthEffects } from './sign-in.effects';
import {Action} from '@ngrx/store';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {Router} from '@angular/router';

describe('SignInEffects', () => {
  let actions$: Observable<Action>;
  let effects: AuthEffects;
  let http: HttpTestingController;
  let router: any;
  beforeEach(() => {
    const routerMock = {
      navigate: jasmine.createSpy('navigate')
    };
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        AuthEffects,
        provideMockActions(() => actions$),
        { provide: Router, useValue: routerMock }
      ]
    });

    effects = TestBed.inject(AuthEffects);
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
  });

  afterEach(() => {
    http.verify();
  });

  it('should be created', () => {
    expect(effects).toBeTruthy();
  });
  describe('Sign in', () => {
    it('should return an AuthenticateSuccess action, with the sign-in data, on success', () => {
      const accessToken = 'eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6Im1pY28iLCJhdWQiOiJ3ZWIiLCJpYXQiOjE2MTA4OTIzNDIsImV4cCI6MTYxMDg5NDE0MiwidXNlcm5hbWUiOiJtaWNvIiwiaWQiOjUwMDUsInJvbGUiOiJST0xFX0FETUlOSVNUUkFUT1IifQ.we4NyEH-EJMIRgFCsJAmSOOKJDleKY2h-KB6lB7PXm-0RUtG7mkD_SF-jsGQ-yCARzx-imPDMiQ2G18ZJwKKrg';
      actions$ = of(new AuthActions.SignInStart({ username: 'mico', password: '123qweASD' }));
      const user = { username: 'mico', id: 5005, accessToken, role: 'ROLE_ADMINISTRATOR' };
      effects.authLogin.subscribe(action => {
        expect(action).toEqual(new AuthActions.AuthenticateSuccess({
          username: 'mico',
          id: 5005,
          accessToken,
          role: 'ROLE_ADMINISTRATOR'
        }));
        expect(localStorage.getItem('user')).toEqual(JSON.stringify(user));
      });



      const req = http.expectOne('http://localhost:8080/auth/log-in');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({username: 'mico', password: '123qweASD'});
      req.flush({accessToken});
    });

    it('should return an AuthenticateFail action, with the sign-in data, on error', () => {
      actions$ = of(new AuthActions.SignInStart({username: 'nonExist', password: '123qweASD'}));

      effects.authLogin.subscribe(action => {
        expect(action).toEqual(new AuthActions.AuthenticateFail('Bad credentials!'));
      });

      const req = http.expectOne('http://localhost:8080/auth/log-in');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({username: 'nonExist', password: '123qweASD'});
      req.flush({error: 'Unauthorized'}, { status: 401, statusText: 'Unauthorized' });
    });
  });

  describe('Sign in success', () => {
    it('should navigate to main page', () => {
      const accessToken = 'eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6Im1pY28iLCJhdWQiOiJ3ZWIiLCJpYXQiOjE2MTA4OTIzNDIsImV4cCI6MTYxMDg5NDE0MiwidXNlcm5hbWUiOiJtaWNvIiwiaWQiOjUwMDUsInJvbGUiOiJST0xFX0FETUlOSVNUUkFUT1IifQ.we4NyEH-EJMIRgFCsJAmSOOKJDleKY2h-KB6lB7PXm-0RUtG7mkD_SF-jsGQ-yCARzx-imPDMiQ2G18ZJwKKrg';

      actions$ = of(new AuthActions.AuthenticateSuccess({
       username: 'mico',
       id: 5005,
       accessToken,
       role: 'ROLE_ADMINISTRATOR'
     }));

      effects.authRedirect.subscribe(() => {
       expect(router.navigate).toHaveBeenCalledWith(['/']);
      });
    });
  });

  describe('Sign out', () => {
    it('should sign out user and go to main page', () => {
     actions$ = of(new AuthActions.SignOut());

     effects.authLogout.subscribe(() => {
       expect(localStorage.getItem('user')).toEqual(null);
       expect(router.navigate).toHaveBeenCalledWith(['/']);
      });
    });
  });
});
