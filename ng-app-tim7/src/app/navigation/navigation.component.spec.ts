import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationComponent } from './navigation.component';
import * as AuthActions from '../features/sign-in/store/sign-in.actions';
import {Store, StoreModule} from '@ngrx/store';
import * as fromApp from '../store/app.reducer';
import {NavigationAdministratorComponent} from './navigation-administrator/navigation-administrator.component';
import {NavigationNonSignedInComponent} from './navigation-non-signed-in/navigation-non-signed-in.component';
import {NavigationRegisteredComponent} from './navigation-registered/navigation-registered.component';
import {MatToolbarModule} from '@angular/material/toolbar';

describe('NavigationComponent', () => {
  let component: NavigationComponent;
  let fixture: ComponentFixture<NavigationComponent>;
  let store: Store;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavigationComponent, NavigationAdministratorComponent, NavigationNonSignedInComponent,
        NavigationRegisteredComponent ],
      imports: [StoreModule.forRoot(fromApp.appReducer), MatToolbarModule ],
      providers: [Store]
    })
    .compileComponents();

    store = TestBed.inject(Store);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavigationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should subscribe to store in ngOnInit lifecycle', () => {

      component.ngOnInit();
      fixture.detectChanges();
      expect(component.role).toBe('NO_ROLE');
    });
  });

  describe('signOut()', () => {
    it('should subscribe to store in ngOnInit lifecycle', () => {

      const action = new AuthActions.SignOut();
      const spy = spyOn(store, 'dispatch');
      component.signOut();
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(action);
    });
  });
});
