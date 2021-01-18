import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import * as CulturalOfferActions from '../store/cultural-offer.actions';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import {Subscription} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FormBuilder, FormGroup} from '@angular/forms';
import {CulturalofferModel} from '../../../models/culturaloffer.model';



@Component({
  selector: 'app-cultural-offer-addpage',
  templateUrl: './cultural-offer-addpage.component.html',
  styleUrls: ['./cultural-offer-addpage.component.css']
})
export class CulturalOfferAddpageComponent implements OnInit, OnDestroy {

  success: string = null;
  error: string = null;
  private storeSub: Subscription;
  culturalOfferForm: FormGroup;
  editOffer: boolean;
  categoryPage = 1;
  subcategoryPage = 1;

  categories = [];
  subcategories = [];

  culturalOffer: CulturalofferModel = {
    id: -1,
    name: '',
    description: '',
    startDate: new Date(),
    endDate: new Date(),
    subcategory: -1,
    location: -1,
    subcategoryName: '',
    categoryName: '',
    category: -1,
    latitude: -1,
    longitude: -1,
    pictures: [''],
  };

  constructor(private route: ActivatedRoute, private store: Store<fromApp.AppState>, private snackBar: MatSnackBar,
              private fb: FormBuilder) {
    this.culturalOfferForm = this.fb.group({
        name: [''],
        startDate: [''],
        endDate: [''],
      });
  }

  @ViewChild('selectCategory') selectCategory: ElementRef;

  ngOnInit(): void {
    const offerId: number = +this.route.snapshot.paramMap.get('id');
    this.editOffer = (offerId !== 0);

    if (this.editOffer){
      this.store.dispatch(new CulturalOfferActions.GetOneOfferAction(offerId));
    }
    else{
      this.store.dispatch(new CulturalOfferActions.GetCategories({page: 0, page_size: 10}));
    }

    this.storeSub = this.store.select('culturaloffer').subscribe(state => {

          this.error = state.errorActionMessage;
          this.success = state.successActionMessage;

          if (state.selectedOffer === null){
            if (!this.editOffer){
              this.bakeCategoriesAndSubcategories(state.categoriesSelect?.content, state.subcategoriesSelect?.content);
            }
            return;
          }


          if (this.editOffer && state.selectedOffer){
            this.culturalOffer = JSON.parse(JSON.stringify(state.selectedOffer));
            console.log(this.culturalOffer);
            const category = {
              id: this.culturalOffer.category,
              name: this.culturalOffer.categoryName
            };

            const subcategory = {
              id: this.culturalOffer.subcategory,
              name: this.culturalOffer.subcategoryName,
              categoryId: this.culturalOffer.category,
              categoryName: this.culturalOffer.categoryName
            };

            this.categories = JSON.parse(JSON.stringify(state.categoriesSelect.content));
            this.subcategories = JSON.parse(JSON.stringify(state.subcategoriesSelect.content));

            this.categories = this.categories.filter( inputCategory => inputCategory.id !== this.culturalOffer.category);
            this.categories.push(category);

            this.subcategories = this.subcategories.filter( inputSubcategory => inputSubcategory.id !== this.culturalOffer.subcategory);
            this.subcategories.push(subcategory);

            this.culturalOfferForm.controls.name.setValue(state.selectedOffer?.name);
            this.culturalOfferForm.controls.startDate.setValue(state.selectedOffer?.startDate);
            this.culturalOfferForm.controls.endDate.setValue(state.selectedOffer?.endDate);

            this.editOffer = false;

          }
          else{
            this.bakeCategoriesAndSubcategories(
              JSON.parse(JSON.stringify(state.categoriesSelect.content)),
              JSON.parse(JSON.stringify(state.subcategoriesSelect.content)));
          }

          if (this.error !== null){
            this.showMessage(this.error);
          }

          if (this.success !== null){
            this.showMessage(this.success);
          }

        });
  }

  private bakeCategoriesAndSubcategories(categories: any, subcategories: any){
      if (categories && subcategories){
          this.categories = categories;
          this.subcategories = subcategories;

          if (this.culturalOffer.category === -1){
            this.culturalOffer.category = categories[0].id;
            this.culturalOffer.categoryName = this.categories[0].name;
          }

          if (this.subcategories.length !== 0){
            this.culturalOffer.subcategory = this.subcategories[0].id;
            this.culturalOffer.subcategoryName = this.subcategories[0].name;
          }
      }
  }

  private showMessage(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new CulturalOfferActions.ClearAction());
  }

  onValueChanged(key: string, value: any) {
    if (key.includes('Date')){
      this.culturalOffer[key] = new Date(value.target.value).getTime();
      this.culturalOfferForm.controls[key].patchValue(new Date(value.target.value).getTime());
      return;
    }
    else if (key.includes('name')){
      this.culturalOfferForm.controls[key].patchValue(value.target.value);

    }
    else if (key.includes('category')) {
      this.culturalOffer[key] = value.value;
      if (key === 'category'){
        this.store.dispatch(new CulturalOfferActions.CategoryChangedAction(
          {categoryId: this.culturalOffer.category, page: 0 , page_size: 10}));
      }
    }
    else{
      this.culturalOffer[key] = value.target.value;
    }

  }

  getNextBatchCategory(){
    this.categoryPage++;
    this.store.dispatch(new CulturalOfferActions.GetCategories({page: 0, page_size: 10 * this.categoryPage}));
  }

  getNextBatchSubcategory(){
    this.subcategoryPage++;
    this.store.dispatch(new CulturalOfferActions.CategoryChangedAction(
      {categoryId: this.culturalOffer.category, page: this.subcategoryPage , page_size: 10 * this.subcategoryPage}));
  }

  onSubmitClicked(){
    this.culturalOffer.name = this.culturalOfferForm.value.name;
    this.store.dispatch(new CulturalOfferActions.ClearSelectedOfferAction());
    this.editOffer = true;
    this.store.dispatch(new CulturalOfferActions.UpdateOfferAction(this.culturalOffer));
  }

  ngOnDestroy(): void {
    if (this.storeSub) {
      this.storeSub.unsubscribe();
      this.store.dispatch(new CulturalOfferActions.ClearSelectedOfferAction());
    }
  }

}
