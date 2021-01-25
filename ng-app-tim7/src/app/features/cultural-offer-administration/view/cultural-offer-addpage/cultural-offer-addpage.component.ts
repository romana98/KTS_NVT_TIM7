import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import * as CulturalOfferActions from '../../store/cultural-offer.actions';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../../store/app.reducer';
import {Subscription} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CulturalofferModel} from '../../../../models/culturaloffer.model';
import {MapDataModel} from '../../../../models/map-data.model';
import { validateDate } from '../../../../validator/custom-validator-date';

@Component({
  selector: 'app-cultural-offer-addpage',
  templateUrl: './cultural-offer-addpage.component.html',
  styleUrls: ['./cultural-offer-addpage.component.css']
})
export class CulturalOfferAddpageComponent implements OnInit, OnDestroy {

  success: string = null;
  error: string = null;
  storeSub: Subscription;
  culturalOfferForm: FormGroup;
  editOffer: boolean;
  categoryPage = 1;
  subcategoryPage = 1;

  categories = [];
  subcategories = [];
  currentPicture = 0;
  offerId = 0;

  culturalOffer: CulturalofferModel = {
    id: -1,
    name: '',
    description: '',
    startDate: new Date(),
    endDate: new Date(),
    subcategory: -1,
    location: 1,
    subcategoryName: '',
    categoryName: '',
    category: -1,
    latitude: 45.25167,
    longitude: 19.83694,
    pictures: [],
    locationName: ''
  };

  @ViewChild('fileInput') fileInput: ElementRef;

  constructor(public route: ActivatedRoute, public store: Store<fromApp.AppState>, public snackBar: MatSnackBar,
              public fb: FormBuilder) {
    this.culturalOfferForm = this.fb.group({
        name: ['', Validators.required],
        startDate: ['', Validators.required],
        endDate: ['', Validators.required],
        description: ['', Validators.required]
      },
      {
        validator:  validateDate('startDate', 'endDate')
      });
  }

  ngOnInit(): void {
    const offerId: number = +this.route.snapshot.paramMap.get('id');
    this.offerId = offerId;
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

          if (this.error !== null){
            this.showMessage(this.error);
            return;
          }

          if (state.selectedOffer === null){
            if (!this.editOffer){
              this.bakeCategoriesAndSubcategories(state.categoriesSelect?.content, state.subcategoriesSelect?.content);
            }
            return;
          }


          if (this.editOffer && state.selectedOffer){
            this.culturalOffer = JSON.parse(JSON.stringify(state.selectedOffer));

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
            this.culturalOfferForm.controls.description.setValue(state.selectedOffer?.description);

            this.editOffer = false;
            this.offerId = state.selectedOffer.id;

          }
          else {
            this.bakeCategoriesAndSubcategories(
              state.categoriesSelect?.content,
              state.subcategoriesSelect?.content);
          }

          if (this.success !== null){
            this.showMessage(this.success);
          }

        });
  }

  bakeCategoriesAndSubcategories(categories: any, subcategories: any){
      if (categories && subcategories){
          categories = JSON.parse(JSON.stringify(categories));
          subcategories = JSON.parse(JSON.stringify(subcategories));
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

  showMessage(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new CulturalOfferActions.ClearAction());
  }

  onValueChanged(key: string, value: any) {
    if (key.includes('Date')){
      // this.culturalOffer[key] = new Date(value.target.value).getTime();
      this.culturalOfferForm.controls[key].patchValue(new Date(value.target.value).getTime());
      return;
    }
    else if (key.includes('name')){
      this.culturalOfferForm.controls[key].patchValue(value.target.value);
     // this.culturalOffer.name = value.target.value;
      if (this.culturalOffer.id !== -1){
        return;
      }
      this.offerId = 0;

    }
    else if (key.includes('category')) {
      this.culturalOffer[key] = value.value;
      if (key === 'category'){
        this.store.dispatch(new CulturalOfferActions.CategoryChangedAction(
          {categoryId: this.culturalOffer.category, page: 0 , page_size: 10}));
      }
    }
    else if (key.includes('subcategory')) {
      this.culturalOffer[key] = value.value;
    }
    else if (key.includes('description')) {
      this.culturalOfferForm.controls[key].patchValue(value.target.value);
    }

  }

  getNextBatchCategory(){
    this.categoryPage++;
    this.store.dispatch(new CulturalOfferActions.GetCategories({page: 0, page_size: 10 * this.categoryPage}));
  }

  getNextBatchSubcategory(){
    this.subcategoryPage++;
    this.store.dispatch(new CulturalOfferActions.CategoryChangedAction(
      {categoryId: this.culturalOffer.category, page: 0 , page_size: 10 * this.subcategoryPage}));
  }

  onSubmitClicked(){
    this.culturalOffer = JSON.parse(JSON.stringify(this.culturalOffer));
    this.culturalOffer.name = JSON.parse(JSON.stringify(this.culturalOfferForm.value.name));
    this.culturalOffer.startDate = JSON.parse(JSON.stringify(this.culturalOfferForm.value.startDate));
    this.culturalOffer.endDate = JSON.parse(JSON.stringify(this.culturalOfferForm.value.endDate));
    this.culturalOffer.description = JSON.parse(JSON.stringify(this.culturalOfferForm.value.description));
    if (this.offerId === 0){

      this.store.dispatch(new CulturalOfferActions.ClearSelectedOfferAction(''));
      this.editOffer = true;
      this.store.dispatch(new CulturalOfferActions.AddOfferAction(this.culturalOffer));
    }
    else{
      this.store.dispatch(new CulturalOfferActions.ClearSelectedOfferAction(''));
      this.editOffer = true;
      this.store.dispatch(new CulturalOfferActions.UpdateOfferAction(this.culturalOffer));
    }
  }

  onLocationChanged(newLocation: MapDataModel){
    this.culturalOffer.latitude = newLocation.latitude;
    this.culturalOffer.longitude = newLocation.longitude;
    this.culturalOffer.locationName = newLocation.locationName;
  }

  onFileChanged(e) {
    const file = e.dataTransfer ? e.dataTransfer.files[0] : e.target.files[0];
    const pattern = /image-*/;
    const reader = new FileReader();
    if (!file) {
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
    const picture =  reader.result.replace(/(\r\n\t|\n|\r\t)/gm, '');
    if (picture !== ''){
      this.culturalOffer.pictures.push(picture);
    }
  }

  onPictureChanged(index: number){
      this.currentPicture = index;
  }

  onPictureRemove(){
      this.culturalOffer.pictures.splice(this.currentPicture, 1);
  }

  ngOnDestroy(): void {
    if (this.storeSub) {
      this.storeSub.unsubscribe();
      this.store.dispatch(new CulturalOfferActions.ClearSelectedOfferAction(''));
    }
  }

}
