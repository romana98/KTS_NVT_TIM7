import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Store, StoreModule} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDividerModule} from '@angular/material/divider';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BrowserModule} from '@angular/platform-browser';
import {of} from 'rxjs';
import * as NewsletterActions from '../store/newsletter.actions';

import { AddNewsletterComponent } from './add-newsletter.component';
import { ActivatedRoute, convertToParamMap, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

describe('AddNewsletterComponent', () => {
  let component: AddNewsletterComponent;
  let fixture: ComponentFixture<AddNewsletterComponent>;
  let store: Store;
  let route: ActivatedRoute;
  let router: Router;

  let mockRouter = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddNewsletterComponent ],
      imports: [FormsModule, ReactiveFormsModule,  StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule, MatDividerModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatInputModule ],
      providers: [Store, {provide: ActivatedRoute, useValue: { snapshot: { paramMap: convertToParamMap( { 'culturalOfferId': '',  'culturalOfferName': '' } ) } }}, 
      {provide: Router, useValue: mockRouter}]
    })
    .compileComponents();
    store = TestBed.inject(Store);
    router = TestBed.inject(Router);
    route = TestBed.inject(ActivatedRoute);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddNewsletterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA', () => {
    expect(component).toBeTruthy();
  });
});
