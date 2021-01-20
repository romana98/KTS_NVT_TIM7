import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationNonSignedInComponent } from './navigation-non-signed-in.component';
import {MatToolbarModule} from '@angular/material/toolbar';

describe('NavigationNonSignedInComponent', () => {
  let component: NavigationNonSignedInComponent;
  let fixture: ComponentFixture<NavigationNonSignedInComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavigationNonSignedInComponent ],
      imports: [MatToolbarModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavigationNonSignedInComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
