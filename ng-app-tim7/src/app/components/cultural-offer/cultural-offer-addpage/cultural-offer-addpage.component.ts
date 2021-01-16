import {AfterContentInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import * as CulturalOfferActions from '../store/cultural-offer.actions';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import {Subscription} from 'rxjs';
import {Mappable} from '../../../models/mappable.interface';
import {MatSnackBar} from '@angular/material/snack-bar';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {validateMatchPassword} from '../../../validator/custom-validator-match-password';
import {validateLength} from '../../../validator/custom-validator-zero-min-eight-length';
import {CulturalofferModel} from '../../../models/culturaloffer.model';



@Component({
  selector: 'app-cultural-offer-addpage',
  templateUrl: './cultural-offer-addpage.component.html',
  styleUrls: ['./cultural-offer-addpage.component.css']
})
export class CulturalOfferAddpageComponent implements OnInit {

  success: string = null;
  error: string = null;
  private storeSub: Subscription;
  culturalOfferForm: FormGroup;
  culturalOffer: CulturalofferModel;

  constructor(private route: ActivatedRoute, private store: Store<fromApp.AppState>, private snackBar: MatSnackBar,
              private fb: FormBuilder) {
    this.culturalOfferForm = this.fb.group({
        name: [''],
        category: [''],
        subcategory: [''],
        startDate: [''],
        endDate: [''],
        description: [''],
        image: [''],
        location: [{
          latitude: 0,
          longitude: 0,
          locationId : 0
        }]
      });
  }

  ngOnInit(): void {
    const offerId: number = +this.route.snapshot.paramMap.get('id');
    if (offerId !== 0){
      this.store.dispatch(new CulturalOfferActions.GetOneOfferAction(offerId));
      this.storeSub = this.store.select('culturaloffer').subscribe(state => {
        this.culturalOffer = state.selectedOffer;
        this.error = state.errorActionMessage;
        this.success = state.successActionMessage;

        this.culturalOfferForm.controls.name.setValue(state.selectedOffer?.name);
        this.culturalOfferForm.controls.category.setValue(state.selectedOffer?.categoryName);
        this.culturalOfferForm.controls.subcategory.setValue(state.selectedOffer?.subcategoryName);
        this.culturalOfferForm.controls.startDate.setValue(state.selectedOffer?.startDate);
        this.culturalOfferForm.controls.endDate.setValue(state.selectedOffer?.endDate);
        this.culturalOfferForm.controls.description.setValue(state.selectedOffer?.description);
        this.culturalOfferForm.controls.image.setValue(state.selectedOffer?.pictures[0]);

        this.culturalOfferForm.controls.location.setValue({
          longitude: state.selectedOffer?.longitude,
          latitude:  state.selectedOffer?.latitude,
          locationId: state.selectedOffer?.location
        });

        if (this.error !== null){
          this.showErrorAlert(this.error);
        }

      });
    }

  }

  private showErrorAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new CulturalOfferActions.ClearAction());
  }

  compareCategory(obj1: any, obj2: any): boolean{
    return obj1.categoryName === obj2.categoryName;
  }

  compareSubcategory(obj1: any, obj2: any): boolean{
    return obj1.subcategoryName === obj2.subcategoryName;
  }

  onSubmitClicked() {
    console.log(this.culturalOfferForm.value);
  }
}
