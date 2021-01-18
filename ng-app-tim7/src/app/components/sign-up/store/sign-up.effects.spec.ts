import { provideMockActions } from '@ngrx/effects/testing';
import { TestBed } from '@angular/core/testing';
import {Observable, of} from 'rxjs';
import * as SignUpActions from './sign-up.actions';
import { SignUpEffects } from './sign-up.effects';
import {Action} from '@ngrx/store';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {Router} from '@angular/router';

describe('SignUpEffects', () => {
  let actions$: Observable<Action>;
  let effects: SignUpEffects;
  let http: HttpTestingController;
  let router: any;
  beforeEach(() => {
    const routerMock = {
      navigate: jasmine.createSpy('navigate')
    };
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        SignUpEffects,
        provideMockActions(() => actions$),
        { provide: Router, useValue: routerMock }
      ]
    });

    effects = TestBed.inject(SignUpEffects);
    http = TestBed.inject(HttpTestingController);
    router = TestBed.inject(Router);
  });

  afterEach(() => {
    http.verify();
  });

  it('should be created', () => {
    expect(effects).toBeTruthy();
  });
  describe('Sign up', () => {
    it('should return an SignUpSuccess action, with the sign-in data, on success', () => {
      actions$ = of(new SignUpActions.SignUpStart({ username: 'mico1', email: 'mico1@admin.com', password: '123qweASD' }));
      effects.signUp.subscribe(action => {
        expect(action).toEqual(new SignUpActions.SignUpSuccess('Registration successful! Activate account by email.'));
      });



      const req = http.expectOne('http://localhost:8080/auth/sign-up');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({username: 'mico1', email: 'mico1@admin.com', password: '123qweASD'});
      req.flush('Registration successful! Activate account by email.');
    });

    it('should return an AuthenticateFail action, with the sign-in data, on error', () => {
      actions$ = of(new SignUpActions.SignUpStart({username: 'nonExist', email: 'non_exist@admin.com', password: '123qweASD'}));

      effects.signUp.subscribe(action => {
        expect(action).toEqual(new SignUpActions.SignUpFail('Username or email already exists.'));
      });

      const req = http.expectOne('http://localhost:8080/auth/sign-up');
      expect(req.request.method).toEqual('POST');
      expect(req.request.body).toEqual({username: 'nonExist', email: 'non_exist@admin.com', password: '123qweASD'});
      req.flush('Username or email already exists.', { status: 400, statusText: 'Bad Request' });
    });
  });

  describe('Sign up success', () => {
    it('should navigate to main page', () => {
      actions$ = of(new SignUpActions.SignUpSuccess('success message'));

      effects.signUpRedirect.subscribe(() => {
        expect(router.navigate).toHaveBeenCalledWith(['/']);
      });
    });
  });
});
