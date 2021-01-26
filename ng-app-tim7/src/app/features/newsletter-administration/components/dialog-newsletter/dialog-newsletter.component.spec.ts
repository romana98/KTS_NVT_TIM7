import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {MatDialogRef, MAT_DIALOG_DATA, MatDialogModule} from '@angular/material/dialog';
import { NewsletterModel } from 'src/app/models/newsletter.model';

import { DialogNewsletterComponent } from './dialog-newsletter.component';

describe('DialogNewsletterComponent', () => {
  let component: DialogNewsletterComponent;
  let fixture: ComponentFixture<DialogNewsletterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogNewsletterComponent ],
      imports: [MatDialogModule],
      providers: [{ provide: MatDialogRef, useValue: {} }, { provide: MAT_DIALOG_DATA, useValue:
        new NewsletterModel(1, 'Title', 'Description', new Date(), 1, 'img.jpg', 'CulturalOffer1') }]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogNewsletterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
