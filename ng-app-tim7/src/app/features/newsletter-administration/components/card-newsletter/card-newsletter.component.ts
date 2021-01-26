import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import { Input } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { NewsletterModel } from 'src/app/models/newsletter.model';
import { DialogNewsletterComponent } from '../dialog-newsletter/dialog-newsletter.component';


@Component({
  selector: 'app-card-newsletter',
  templateUrl: './card-newsletter.component.html',
  styleUrls: ['./card-newsletter.component.css']
})
export class CardNewsletterComponent implements OnInit {

  @Input() newsletter: NewsletterModel;
  @Output() newEvent = new EventEmitter<number>();
  success: string = null;
  error: string = null;

  constructor(
    public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(DialogNewsletterComponent, {
      width: '800px',
      data: this.newsletter,
    });
  }

  emitUnsubscribe(): void {
    this.newEvent.emit(this.newsletter.culturalOfferId);
  }

}
