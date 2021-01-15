import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, FormGroupDirective, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import {MatSnackBar} from '@angular/material/snack-bar';
import {validateMatchPassword} from '../../../validator/custom-validator-match-password';
import * as NewsletterActions from '../store/newsletter.actions';

@Component({
  selector: 'app-add-newsletter',
  templateUrl: './add-newsletter.component.html',
  styleUrls: ['./add-newsletter.component.css']
})
export class AddNewsletterComponent implements OnInit, OnDestroy {

  @ViewChild(FormGroupDirective) formGroupDirective: FormGroupDirective;
  private storeSub: Subscription;
  form: FormGroup;
  error: string = null;
  success: string = null;
  bar = false;

  pageCategory = 0;
  pageSubcategory = 0;
  pageOffer = 0;
  pageSize = 15;
  categoriesSelect = {content: [], numberOfElements: 0, totalElements: 0, totalPages: 0, number: 0};
  subcategoriesSelect = {content: [], numberOfElements: 0, totalElements: 0, totalPages: 0, number: 0};
  offersSelect = {content: [], numberOfElements: 0, totalElements: 0, totalPages: 0, number: 0};
  picture = "";
  name = "";
  description = "";
  culturalOfferId = null;
  categoryId = null;
  subcategoryId = null;

   constructor(
    private fb: FormBuilder,
    private store: Store<fromApp.AppState>,
    private snackBar: MatSnackBar
  ) {
    this.form = this.fb.group({
        name : [null, Validators.required],
        description : [null, Validators.required],
        offerSelect : [null, Validators.required],
      });
  }

  ngOnInit(): void {
    this.store.dispatch(new NewsletterActions.GetCategoriesSelect({ page: this.pageCategory, size: this.pageSize }));
    this.storeSub = this.store.select('newsletter').subscribe(state => {
      this.categoriesSelect = state.categoriesSelect;
      this.subcategoriesSelect = state.subcategoriesSelect;
      this.offersSelect = state.offersSelect;
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
    newsletter.name = this.form.value.name;
    newsletter.description = this.form.value.description;
    newsletter.picture = this.picture;
    newsletter.publishedDate = new Date();
    newsletter.culturalOfferId = this.culturalOfferId;

    this.store.dispatch(new NewsletterActions.AddNewsletter({ name: newsletter.name, description: newsletter.description, picture: newsletter.picture, publishedDate: newsletter.publishedDate, culturalOfferId: newsletter.culturalOfferId  }));
    this.picture = "";
  }

  getNextBatchCategories() {
    this.pageCategory++;
    this.store.dispatch(new NewsletterActions.GetCategoriesSelect({ page: this.pageCategory, size: this.pageSize }));
  }

  getNextBatchSubcategories() {
    this.pageSubcategory++;
    this.store.dispatch(new NewsletterActions.GetSubcategoriesSelect({ page: this.pageSubcategory, size: this.pageSize, category: this.categoryId }));
  }

  getNextBatchOffers() {
    this.pageOffer++;
    this.store.dispatch(new NewsletterActions.GetOffersSelect({ page: this.pageOffer, size: this.pageSize, subcategory: this.subcategoryId }));
  }

  onChangeCategories(event) {
    this.pageSubcategory = 0;
    this.categoryId = event.value.id;
    this.store.dispatch(new NewsletterActions.GetSubcategoriesSelect({ page: this.pageSubcategory, size: this.pageSize, category: event.value.id }));
  }

  onChangeSubcategories(event) {
    this.pageOffer = 0;
    this.subcategoryId = event.value.id;
    this.store.dispatch(new NewsletterActions.GetOffersSelect({ page: this.pageOffer, size: this.pageSize, subcategory: event.value.id }));
  }

  onChangeOffers(event) {
    this.culturalOfferId = event.value.id;
    console.log(this.culturalOfferId);
  }

  onFileChanged(e) {
    var file = e.dataTransfer ? e.dataTransfer.files[0] : e.target.files[0];
    var pattern = /image-*/;
    var reader = new FileReader();
    if (!file) {
      this.picture = "";
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
    let reader = e.target;
    this.picture =  reader.result.replace(/(\r\n\t|\n|\r\t)/gm,"");
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
