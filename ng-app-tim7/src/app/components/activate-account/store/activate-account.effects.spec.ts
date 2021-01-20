import {Observable, of} from 'rxjs';
import {Action} from '@ngrx/store';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';
import {provideMockActions} from '@ngrx/effects/testing';
import {Router} from '@angular/router';
import * as ActivateAccActions from './activate-account.actions';
import {ActivateAccountEffects} from './activate-account.effects';

describe('ActivateAccountEffects', () => {
  let actions$: Observable<Action>;
  let effects: ActivateAccountEffects;
  let http: HttpTestingController;
  let router: any;
  beforeEach(() => {
    const routerMock = {
      navigate: jasmine.createSpy('navigate')
    };
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        ActivateAccountEffects,
        provideMockActions(() => actions$),
        { provide: Router, useValue: routerMock }
      ]
    });

    effects = TestBed.inject(ActivateAccountEffects);
    http = TestBed.inject(HttpTestingController);
    router = TestBed.inject(Router);
  });

  afterEach(() => {
    http.verify();
  });

  it('should be created', () => {
    expect(effects).toBeTruthy();
  });

  describe('Activate account', () => {
    it('should return an ActivateSuccess action after activating account, on success', () => {
      actions$ = of(new ActivateAccActions.ActivateStart({id: 10}));
      effects.activate.subscribe(action => {
        expect(action).toEqual(new ActivateAccActions.ActivateSuccess('Activation succeeded.'));
      });

      const req = http.expectOne('http://localhost:8080/auth/activate/' + 10);
      expect(req.request.method).toEqual('POST');
      req.flush('');
    });

    it('should return an ActivateFail action after activating account, on error', () => {
      actions$ = of(new ActivateAccActions.ActivateStart({id: 10}));
      effects.activate.subscribe(action => {
        expect(action).toEqual(new ActivateAccActions.ActivateFail('Bad request'));
      });

      const req = http.expectOne('http://localhost:8080/auth/activate/' + 10);
      expect(req.request.method).toEqual('POST');
      req.flush( 'Bad request', { status: 400, statusText: 'Bad request' });
    });
  });

  describe('Activation success', () => {
    it('should navigate to main page', () => {
      actions$ = of(new ActivateAccActions.ActivateSuccess('Profile updated.Activation succeeded.'));

      effects.activateRedirect.subscribe(() => {
        expect(router.navigate).toHaveBeenCalledWith(['/']);
      });
    });
  });
});
