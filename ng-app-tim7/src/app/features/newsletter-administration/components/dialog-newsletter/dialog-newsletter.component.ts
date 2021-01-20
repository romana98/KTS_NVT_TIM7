import { Component, Inject, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { NewsletterModel } from 'src/app/models/newsletter.model';

@Component({
  selector: 'app-dialog-newsletter',
  templateUrl: './dialog-newsletter.component.html',
  styleUrls: ['./dialog-newsletter.component.css']
})
export class DialogNewsletterComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<DialogNewsletterComponent>,
              @Inject(MAT_DIALOG_DATA) public data: NewsletterModel) { }

  ngOnInit(): void {
  }

}
