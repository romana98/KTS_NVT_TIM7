import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-navigation-administrator',
  templateUrl: './navigation-administrator.component.html',
  styleUrls: ['./navigation-administrator.component.css']
})
export class NavigationAdministratorComponent {
  @Output() signOut = new EventEmitter<void>();

  constructor() {
  }

  signOutUser() {
    this.signOut.emit();
  }

}


