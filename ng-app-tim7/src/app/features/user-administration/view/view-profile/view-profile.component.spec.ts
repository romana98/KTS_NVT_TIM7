import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewProfileComponent } from './view-profile.component';
import {MatCardModule} from '@angular/material/card';
import {Store, StoreModule} from '@ngrx/store';
import * as fromApp from '../../../../store/app.reducer';
import {Router} from '@angular/router';
import * as AdminActions from '../../administrator-administration/store/administrator.actions';
import {MatDividerModule} from '@angular/material/divider';

describe('ViewProfileComponent', () => {
  let component: ViewProfileComponent;
  let fixture: ComponentFixture<ViewProfileComponent>;
  let router: any;
  let store: Store;
  const accessToken = 'eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6Im1pY28iLCJhdWQiOiJ3ZWIiLCJpYXQiOjE2MTA4OTIzNDIsImV4cCI6MTYxMDg5NDE0MiwidXNlcm5hbWUiOiJtaWNvIiwiaWQiOjUwMDUsInJvbGUiOiJST0xFX0FETUlOSVNUUkFUT1IifQ.we4NyEH-EJMIRgFCsJAmSOOKJDleKY2h-KB6lB7PXm-0RUtG7mkD_SF-jsGQ-yCARzx-imPDMiQ2G18ZJwKKrg';


  beforeEach(async(() => {
    const routerMock = {
      navigate: jasmine.createSpy('navigate')
    };
    TestBed.configureTestingModule({
      declarations: [ ViewProfileComponent ],
      imports: [StoreModule.forRoot(fromApp.appReducer), MatCardModule, MatDividerModule],
      providers: [Store,
        { provide: Router, useValue: routerMock }]
    })
    .compileComponents();

    store = TestBed.inject(Store);
    router = TestBed.inject(Router);
  }));

  beforeEach(() => {
    let storeLocal = {};
    const mockLocalStorage = {
      getItem: (key: string): string => {
        return key in storeLocal ? storeLocal[key] : null;
      },
      setItem: (key: string, value: string) => {
        storeLocal[key] = `${value}`;
      },
      clear: () => {
        storeLocal = {};
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
    fixture = TestBed.createComponent(ViewProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(component.userType).toEqual('administrator');
    expect(component.Actions).toEqual(AdminActions);
  });

  describe('ngOnInit()', () => {
    it('should subscribe to store in ngOnInit lifecycle', () => {
      const action = new AdminActions.GetUser();
      const spy = spyOn(store, 'dispatch');
      component.ngOnInit();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });

  describe('goToEdit()', () => {
    it('should navigate to EditProfile page', () => {
      component.goToEdit();
      fixture.detectChanges();
      expect(router.navigate).toHaveBeenCalledWith(['/' + component.userType + '/edit-profile']);
    });
  });
});
