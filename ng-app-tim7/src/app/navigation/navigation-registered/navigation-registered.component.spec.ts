import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationRegisteredComponent } from './navigation-registered.component';

describe('NavigationRegisteredComponent', () => {
  let component: NavigationRegisteredComponent;
  let fixture: ComponentFixture<NavigationRegisteredComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavigationRegisteredComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavigationRegisteredComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
