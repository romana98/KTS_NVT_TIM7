import { TestBed } from '@angular/core/testing';

import { HttpAuthInterceptor } from './http-auth.interceptor';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {UserModel} from '../models/user.model';
import {RegisteredEffects} from '../features/user-administration/registered-administration/store/registered.effects';
import {Observable, of} from 'rxjs';
import {Action} from '@ngrx/store';
import {provideMockActions} from '@ngrx/effects/testing';
import * as RegisteredActions from '../features/user-administration/registered-administration/store/registered.actions';
import {Router} from '@angular/router';

describe('HttpAuthInterceptor', () => {
  let actions$: Observable<Action>;
  let httpMock: HttpTestingController;
  const accessToken = 'eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6Im1pY28iLCJhdWQiOiJ3ZWIiLCJpYXQiOjE2MTA4OTIzNDIsImV4cCI6MTYxMDg5NDE0MiwidXNlcm5hbWUiOiJtaWNvIiwiaWQiOjUwMDUsInJvbGUiOiJST0xFX0FETUlOSVNUUkFUT1IifQ.we4NyEH-EJMIRgFCsJAmSOOKJDleKY2h-KB6lB7PXm-0RUtG7mkD_SF-jsGQ-yCARzx-imPDMiQ2G18ZJwKKrg';
  let effects: RegisteredEffects;
  let router: any;
  beforeEach(() => {
    const routerMock = {
      navigate: jasmine.createSpy('navigate')
    };
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        RegisteredEffects,
        provideMockActions(() => actions$),
        { provide: Router, useValue: routerMock },
        {
          provide: HTTP_INTERCEPTORS,
          useClass: HttpAuthInterceptor,
          multi: true,
        },
      ]
    });
    effects = TestBed.inject(RegisteredEffects);
    router = TestBed.inject(Router);
    httpMock = TestBed.inject(HttpTestingController);

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
    httpMock.verify();
  });

  it('should be created', () => {
    const interceptor: HttpAuthInterceptor = TestBed.inject(HttpAuthInterceptor);
    expect(interceptor).toBeTruthy();
  });

  it('should add an Authorization header', () => {
    actions$ = of(new RegisteredActions.GetUser());
    const user = new UserModel('mico', 'mico@admin.com', null );
    effects.registered.subscribe(action => {
      expect(action).toBeTruthy();
    });

    const req = httpMock.expectOne('http://localhost:8080/registered-users/' + JSON.parse(localStorage.getItem('user')).id);
    expect(req.request.method).toEqual('GET');
    expect(req.request.headers.has('Authorization')).toEqual(true);
    expect(req.request.headers.get('Authorization')).toBe('Bearer ' + accessToken);

    req.flush(user);
  });
});
