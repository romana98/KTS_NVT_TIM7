import {AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {CulturalofferModel} from '../../../models/culturaloffer.model';
import {FormBuilder, FormGroup, FormGroupDirective, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import {MatSnackBar} from '@angular/material/snack-bar';
import * as SubcategoryActions from '../../subcategory/store/subcategory.actions';
import * as CulturalOfferActions from '../../cultural-offer/store/cultural-offer.actions';
import {ActivatedRoute, Router} from '@angular/router';
import * as CategoryActions from '../../category/store/category.actions';


@Component({
  selector: 'app-cultural-offer-detailed-view',
  templateUrl: './cultural-offer-detailed-view.component.html',
  styleUrls: ['./cultural-offer-detailed-view.component.css']
})
export class CulturalOfferDetailedViewComponent implements OnInit, OnDestroy {
  @ViewChild(FormGroupDirective) formGroupDirective: FormGroupDirective;
  averageRating = 0.0;
  pickedRating = 3;
  subscribeButtonValue = 'Subscribe';
  hideSubscribe = false;
  form: FormGroup;
  picture = 0;
  culturalOfferDetailed = {id: 0, name: '', description: '', pictures: ['']};
  id = 0;
  pickedPhotos = [];
  comments = {content: [{description: '', registeredUser: '', picturesId: [''], publishedDate: new Date()}], numberOfElements: 0,
    totalElements: 0, totalPages: 0, number: 0};
  publishedDateComment = '';
  pageComments = 0;
  pageSizeComments = 1;
  newsletters = {content: [{name: '', description: '', picture: '', publishedDate: new Date()}],
    numberOfElements: 0, totalElements: 0, totalPages: 0, number: 0};
  publishedDateNewsletter = '';
  pageNewsletters = 0;
  pageSizeNewsletters = 1;
  success: string = null;
  error: string = null;
  isHidden = true;
  commentsEmpty = false;
  newslettersEmpty = false;
  disabledRating = false;
  hiddenButtons = false;
  disableNavigateCommentNext = false;
  disableNavigateNewsletterNext = false;
  canNotPublish = true;
  // store
  storeSub: Subscription;
  publishedDateNewComment = null;

  @ViewChild('fileInput') fileInput: ElementRef;

  constructor(private fb: FormBuilder, private store: Store<fromApp.AppState>,
              public snackBar: MatSnackBar, private router: Router, private activatedRouter: ActivatedRoute) {
    this.form = this.fb.group({
      commentInput : [null, Validators.required]
    });
  }

  ngOnInit(): void {
    this.id = Number(this.activatedRouter.snapshot.queryParamMap.get('offer_id'));
    const user = JSON.parse(localStorage.getItem('user'));
    if (user === null){
      this.isHidden = true;
      this.hiddenButtons = true;
    }else{
      this.isHidden = user.role === 'ROLE_ADMINISTRATOR';
      this.hiddenButtons = user.role === 'ROLE_ADMINISTRATOR';
      this.canNotPublish = user.role !== 'ROLE_ADMINISTRATOR';
    }
    this.store.dispatch(new CulturalOfferActions.GetComments({page: this.pageComments, size: this.pageSizeComments,
        offerId: this.id}));
    this.store.dispatch(new CulturalOfferActions.GetNewsletters({page: this.pageNewsletters, size: this.pageSizeNewsletters,
        offerId: this.id}));
    this.store.dispatch(new CulturalOfferActions.GetAverageRating({offerId: this.id}));
    if (!this.hiddenButtons){
      this.store.dispatch(new CulturalOfferActions.AlreadyRated(this.id));
      this.store.dispatch(new CulturalOfferActions.AlreadySubscribed(this.id));
    }
    this.storeSub = this.store.select('culturaloffer').subscribe(state => {
      if (state.culturalOfferDetailed === null){
        this.router.navigate(['/']);
      }else{
        this.culturalOfferDetailed = state.culturalOfferDetailed;
        if (state.comments.content.length === 1 && state.comments.content[0].description === ''){
          if (this.pageComments === 0){
            this.commentsEmpty = true;
          }
        }else{
          this.comments = state.comments;
          this.commentsEmpty = false;
          this.publishedDateComment = (new Date(this.comments.content[0].publishedDate)).toLocaleString();
        }
        if (this.pageComments + 1 === this.comments.totalPages){
          this.disableNavigateCommentNext = true;
        }else{
          this.disableNavigateCommentNext = false;
        }
        if (state.newsletters.content.length === 1 && state.newsletters.content[0].description === ''){
          if (this.pageNewsletters === 0){
            this.newslettersEmpty = true;
          }
        }else{
          this.newsletters = state.newsletters;
          this.newslettersEmpty = false;
          this.publishedDateNewsletter = (new Date(this.newsletters.content[0].publishedDate)).toLocaleString();
        }
        if (this.pageNewsletters + 1 === this.newsletters.totalPages){
          this.disableNavigateNewsletterNext = true;
        }else{
          this.disableNavigateNewsletterNext = false;
        }
        this.averageRating = state.averageRating;
        this.success = state.successActionMessage;
        this.error = state.errorActionMessage;
        this.pickedRating = state.alreadyRated;
        if (this.pickedRating !== 0.0){
          this.disabledRating = true;
        }else{
          this.pickedRating = 3.0;
          this.disabledRating = false;
        }
        this.hideSubscribe = state.alreadySubscribed;
        if (this.success) {
          this.showSuccessAlert(this.success);
        }
        if (this.error) {
          this.showErrorAlert(this.error);
        }
      }
    });
  }

  showSuccessAlert(message: string) {
    this.pageComments = 0;
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    if (message === 'Successfully subscribed!' || message === 'Successfully unsubscribed!'){
      this.store.dispatch(new CulturalOfferActions.AlreadySubscribed(this.culturalOfferDetailed.id));
    }else if (message === 'Successfully rated!'){
      this.store.dispatch(new CulturalOfferActions.GetAverageRating({offerId: this.culturalOfferDetailed.id}));
    }else{
      this.store.dispatch(new CulturalOfferActions.GetComments({page: this.pageComments, size: this.pageSizeComments,
        offerId: this.culturalOfferDetailed.id}));
    }
    this.store.dispatch(new CulturalOfferActions.ClearAction());
    setTimeout(() => this.formGroupDirective.resetForm(), 0);
    this.picture = 0;
    this.pickedPhotos = [];
  }

  showErrorAlert(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 3000 });
    this.store.dispatch(new CulturalOfferActions.ClearAction());
  }

  rate() {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user === null){
      this.store.dispatch(new CulturalOfferActions.Rate({offerId: this.culturalOfferDetailed.id,
        rate: this.pickedRating, registeredId: -1}));
    }else{
      this.store.dispatch(new CulturalOfferActions.Rate({offerId: this.culturalOfferDetailed.id,
        rate: this.pickedRating, registeredId: user.id}));
    }
    this.disabledRating = true;
  }

  addComment() {
    const user = JSON.parse(localStorage.getItem('user'));
    if (this.publishedDateNewComment === null){
      this.publishedDateNewComment = new Date();
    }
    if (user === null){
      this.store.dispatch(new CulturalOfferActions.CreateComment(
        {description: this.form.value.commentInput, publishedDate: this.publishedDateNewComment,
        registeredId: -1, picturesId: this.pickedPhotos, culturalOfferId: this.culturalOfferDetailed.id}));
    }else{
      this.store.dispatch(new CulturalOfferActions.CreateComment(
        {description: this.form.value.commentInput, publishedDate: this.publishedDateNewComment,
        registeredId: user.id, picturesId: this.pickedPhotos, culturalOfferId: this.culturalOfferDetailed.id}));
    }
  }

  commentNavigateBefore() {
    this.pageComments = this.pageComments - 1;
    this.store.dispatch(new CulturalOfferActions.GetComments({page: this.pageComments, size: this.pageSizeComments,
      offerId: this.culturalOfferDetailed.id}));
  }

  commentNavigateNext() {
    this.pageComments = this.pageComments + 1;
    this.store.dispatch(new CulturalOfferActions.GetComments({page: this.pageComments, size: this.pageSizeComments,
      offerId: this.culturalOfferDetailed.id}));
  }

  newslettersNavigateBefore() {
    this.pageNewsletters = this.pageNewsletters - 1;
    this.store.dispatch(new CulturalOfferActions.GetNewsletters({page: this.pageNewsletters, size: this.pageSizeNewsletters,
      offerId: this.culturalOfferDetailed.id}));
  }

  newslettersNavigateAfter() {
    this.pageNewsletters = this.pageNewsletters + 1;
    this.store.dispatch(new CulturalOfferActions.GetNewsletters({page: this.pageNewsletters, size: this.pageSizeNewsletters,
      offerId: this.culturalOfferDetailed.id}));
  }

  subscribe() {
    const user = JSON.parse(localStorage.getItem('user'));
    if (this.hideSubscribe === false){
      if (user === null){
        this.store.dispatch(new CulturalOfferActions.Subscribe({offerId: this.culturalOfferDetailed.id, userId: -1}));
      }else{
        this.store.dispatch(new CulturalOfferActions.Subscribe({offerId: this.culturalOfferDetailed.id, userId: user.id}));
      }
    }else{
      if (user === null){
        this.store.dispatch(new CulturalOfferActions.Unsubscribe({offerId: this.culturalOfferDetailed.id, userId: -1}));
      }else{
        this.store.dispatch(new CulturalOfferActions.Unsubscribe({offerId: this.culturalOfferDetailed.id, userId: user.id}));
      }
    }
  }

  onFileChanged(e) {
    const file = e.dataTransfer ? e.dataTransfer.files[0] : e.target.files[0];
    const pattern = /image-*/;
    const reader = new FileReader();
    if (!file) {
      return;
    }
    console.log(file);
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
      this.pickedPhotos.push(picture);
    }
  }

  onPictureChanged(index: number){
    this.picture = index;
  }

  onPictureRemove(){
    this.pickedPhotos.splice(this.picture, 1);
  }

  goToPublish(){
    this.router.navigate(['/newsletter/add-newsletter', {culturalOfferId: this.id, culturalOfferName: this.culturalOfferDetailed.name}]);
  }

  ngOnDestroy() {
    this.storeSub.unsubscribe();
  }

}
