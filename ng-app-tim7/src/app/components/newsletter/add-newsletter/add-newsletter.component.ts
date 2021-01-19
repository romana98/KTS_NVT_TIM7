import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, FormGroupDirective, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import {MatSnackBar} from '@angular/material/snack-bar';
import {validateMatchPassword} from '../../../validator/custom-validator-match-password';
import * as NewsletterActions from '../store/newsletter.actions';
import { ActivatedRoute, Router } from '@angular/router';
import { ElementRef } from '@angular/core';

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
  //picture = '';
  name = '';
  description = '';
  culturalOfferId = null;
  categoryId = null;
  subcategoryId = null;
  culturalOfferIdParam = null;
  culturalOfferNameParam = null;

  @ViewChild('fileInput') fileInput: ElementRef;

   constructor(
    private fb: FormBuilder,
    private store: Store<fromApp.AppState>,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
        name : [null, Validators.required],
        description : [null, Validators.required],
        offerSelect : [null],
        picture: [null]
      });
  }

  ngOnInit(): void {
    this.culturalOfferIdParam = this.route.snapshot.paramMap.get('culturalOfferId');
    this.culturalOfferNameParam = this.route.snapshot.paramMap.get('culturalOfferName');
    this.form.get('offerSelect').valueChanges.subscribe(val => {
      const toValidate = 'offerSelect';
      if (!this.culturalOfferIdParam) {
          this.form.controls[toValidate].setValidators([Validators.required]);
          this.form.controls[toValidate].updateValueAndValidity();
      } else {
          this.form.controls[toValidate].clearValidators();
          this.form.controls[toValidate].updateValueAndValidity();
      }
  });
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
    newsletter.picture = this.form.value.picture;
    newsletter.publishedDate = new Date();
    newsletter.culturalOfferId = this.culturalOfferIdParam ? this.culturalOfferIdParam : this.culturalOfferId;

    this.store.dispatch(new NewsletterActions.AddNewsletter({ name: newsletter.name, description: newsletter.description,
      picture: newsletter.picture, publishedDate: newsletter.publishedDate, culturalOfferId: newsletter.culturalOfferId  }));
      this.form.patchValue({
        picture: ''
      });
    this.router.navigate(['/newsletter/dashboard']);
  }

  getNextBatchCategories() {
    this.pageCategory++;
    this.store.dispatch(new NewsletterActions.GetCategoriesSelect({ page: this.pageCategory, size: this.pageSize }));
  }

  getNextBatchSubcategories() {
    this.pageSubcategory++;
    this.store.dispatch(new NewsletterActions.GetSubcategoriesSelect({ page: this.pageSubcategory, size: this.pageSize,
      category: this.categoryId }));
  }

  getNextBatchOffers() {
    this.pageOffer++;
    this.store.dispatch(new NewsletterActions.GetOffersSelect({ page: this.pageOffer, size: this.pageSize, subcategory:
      this.subcategoryId }));
  }

  onChangeCategories(event) {
    this.pageSubcategory = 0;
    this.categoryId = event.value.id;
    this.store.dispatch(new NewsletterActions.GetSubcategoriesSelect({ page: this.pageSubcategory, size: this.pageSize,
      category: event.value.id }));
  }

  onChangeSubcategories(event) {
    this.pageOffer = 0;
    this.subcategoryId = event.value.id;
    this.store.dispatch(new NewsletterActions.GetOffersSelect({ page: this.pageOffer, size: this.pageSize, subcategory:
      event.value.id }));
  }

  onChangeOffers(event) {
    this.culturalOfferId = event.value.id;
    console.log(this.culturalOfferId);
  }

  onFileChanged(e) {
    const file = e.dataTransfer ? e.dataTransfer.files[0] : e.target.files[0];
    const pattern = /image-*/;
    const reader = new FileReader();
    if (!file) {
      this.form.patchValue({
        picture: ''
      });
      return;
    }
    if (!file.type.match(pattern)) {
      alert('invalid format');
      return;
    }
    reader.onload = this._handleReaderLoaded.bind(this);
    reader.readAsDataURL(file);
    this.fileInput.nativeElement.value = '';
  }
  _handleReaderLoaded(e) {
    const reader = e.target;
    this.form.patchValue({
      picture: reader.result.replace(/(\r\n\t|\n|\r\t)/gm, '')
    });
    //this.form.controls.picture = reader.result.replace(/(\r\n\t|\n|\r\t)/gm, '');
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
