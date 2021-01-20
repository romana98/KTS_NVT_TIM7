import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Store, StoreModule} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDividerModule} from '@angular/material/divider';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BrowserModule, By} from '@angular/platform-browser';
import {of} from 'rxjs';
import * as NewsletterActions from '../store/newsletter.actions';
import { ActivatedRoute, convertToParamMap, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { MatSelectModule, SELECT_PANEL_PADDING_X } from '@angular/material/select';
import { MatProgressBar, MatProgressBarModule } from '@angular/material/progress-bar';
import { NewsletterModel } from 'src/app/models/newsletter.model';
import { CardNewsletterComponent } from './card-newsletter.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { DialogNewsletterComponent } from '../dialog-newsletter/dialog-newsletter.component';

describe('CardNewsletterComponent', () => {
  let component: CardNewsletterComponent;
  let fixture: ComponentFixture<CardNewsletterComponent>;
  let dialog: MatDialog;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CardNewsletterComponent ],
      imports: [FormsModule, ReactiveFormsModule,  StoreModule.forRoot(fromApp.appReducer), MatSnackBarModule, MatDividerModule,
        BrowserModule, BrowserAnimationsModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatProgressBarModule,
        MatDialogModule ],
      providers: [MatDialog]
    })
    .compileComponents();
    dialog = TestBed.inject(MatDialog);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CardNewsletterComponent);
    component = fixture.componentInstance;
    const newsletter: NewsletterModel = {id: 1, name: 'Title', description: 'Description', publishedDate: new Date('2020-12-12'),
    culturalOfferId: 1, picture: 'img.jpg', culturaloffer: 'CulturalOffer1'};
    component.newsletter = newsletter;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('openDialog()', () => {
    it('should open dialog', () => {

      fixture.detectChanges();
      spyOn(component.dialog, 'open');
      component.openDialog();
      expect(component.dialog.open).toHaveBeenCalledWith(DialogNewsletterComponent, {
        width: '800px',
        data: {id: 1, name: 'Title', description: 'Description', publishedDate: new Date('2020-12-12'),
        culturalOfferId: 1, picture: 'img.jpg', culturaloffer: 'CulturalOffer1'},
      });

    });
  });

  describe('emitUnsubscribe()', () => {
    it('should emit unsubscribe event', () => {

      fixture.detectChanges();
      spyOn(component.newItemEvent, 'emit');
      component.emitUnsubscribe();
      expect(component.newItemEvent.emit).toHaveBeenCalledWith(1);

    });
  });

});
