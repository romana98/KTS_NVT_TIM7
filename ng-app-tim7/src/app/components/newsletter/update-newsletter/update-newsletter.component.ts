import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, FormGroupDirective, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import {MatSnackBar} from '@angular/material/snack-bar';
import {validateMatchPassword} from '../../../validator/custom-validator-match-password';
import * as NewsletterActions from '../store/newsletter.actions';
import {ActivatedRoute} from '@angular/router';
import {NewsletterModel} from '../../../models/newsletter.model';

@Component({
  selector: 'app-update-newsletter',
  templateUrl: './update-newsletter.component.html',
  styleUrls: ['./update-newsletter.component.css']
})
export class UpdateNewsletterComponent implements OnInit, OnDestroy {

  @ViewChild(FormGroupDirective) formGroupDirective: FormGroupDirective;
  private storeSub: Subscription;
  form: FormGroup;
  error: string = null;
  success: string = null;
  bar = false;
  newsletter = null;
  id = null;
  name = null;
  description = null;
  publishedDate = null;
  culturalOfferId = null;
  picture = null;

  constructor(
    private fb: FormBuilder,
    private store: Store<fromApp.AppState>,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute
  )
  {
    this.form = this.fb.group({
      name : [null, Validators.required],
      description : [null, Validators.required],
    });
  }

  ngOnInit(): void {
    this.store.dispatch(new NewsletterActions.GetNewsletter({ id: this.route.snapshot.paramMap.get('id') }));
    this.storeSub = this.store.select('newsletter').subscribe(state => {
      this.newsletter = state.newsletter;
      this.id = state.newsletter.id;
      this.name = state.newsletter.name;
      this.description = state.newsletter.description;
      this.publishedDate = state.newsletter.publishedDate;
      this.culturalOfferId = state.newsletter.culturalOfferId;
      this.picture = state.newsletter.picture;
      this.error = state.error;
      this.success = state.success;
      this.bar = state.bar;
      if (this.error) {
        this.showErrorAlert(this.error);
      }

      if (this.success) {
        this.showSuccessAlert(this.success);
      }
      if (this.bar){
        this.form.disable();
      }else{
        this.form.enable();
      }
    });
  }

  submit() {
    const newsletter: any = {};
    newsletter.id = this.id;
    newsletter.name = this.name;
    newsletter.description = this.description;
    newsletter.picture = this.picture;
    newsletter.publishedDate = this.publishedDate;
    newsletter.culturalOfferId = this.culturalOfferId;

    this.store.dispatch(new NewsletterActions.UpdateNewsletter({ newsletter}));
  }

  onFileChanged(e) {
    const file = e.dataTransfer ? e.dataTransfer.files[0] : e.target.files[0];
    const pattern = /image-*/;
    const reader = new FileReader();
    if (!file) {
      this.picture = '';
      return;
    }
    if (!file.type.match(pattern)) {
      alert('invalid format');
      return;
    }
    reader.onload = this._handleReaderLoaded.bind(this);
    reader.readAsDataURL(file);
  }
  _handleReaderLoaded(e) {
    const reader = e.target;
    this.picture = reader.result.replace(/(\r\n\t|\n|\r\t)/gm, '');
  }

  private showErrorAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 2000 });
    this.store.dispatch(new NewsletterActions.ClearError());
  }

  private showSuccessAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new NewsletterActions.ClearSuccess());
    setTimeout(() => this.formGroupDirective.resetForm(), 0);
  }

  ngOnDestroy() {
    if (this.storeSub) {
      this.storeSub.unsubscribe();
    }
  }

}
