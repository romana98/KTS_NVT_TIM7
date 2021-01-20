import { Component, OnInit } from '@angular/core';
import * as AdminActions from '../../administrator/store/administrator.actions';
import * as RegisteredActions from '../../registered/store/registered.actions';
import {Subscription} from 'rxjs';
import {Store} from '@ngrx/store';
import * as fromApp from '../../../store/app.reducer';
import {UserModel} from '../../../models/user.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-view-profile',
  templateUrl: './view-profile.component.html',
  styleUrls: ['./view-profile.component.css']
})
export class ViewProfileComponent implements OnInit {

  user: UserModel = new UserModel('', '', '');
  private storeSub: Subscription;
  Actions: any;
  userType;
  constructor(private store: Store<fromApp.AppState>, private router: Router) {
    this.userType = JSON.parse(localStorage.getItem('user')).role.slice(5).toLowerCase();
    this.Actions = this.userType === 'administrator' ? AdminActions : RegisteredActions;
  }

  ngOnInit(): void {
    this.store.dispatch(new this.Actions.GetUser());
    this.storeSub = this.store.select(this.userType).subscribe(state => {
      this.user = state.user;
    });
  }

  goToEdit(){
    this.router.navigate(['/' + this.userType + '/edit-profile']);
  }
}
