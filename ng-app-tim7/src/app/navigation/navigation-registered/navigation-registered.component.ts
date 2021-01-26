import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-navigation-registered',
  templateUrl: './navigation-registered.component.html',
  styleUrls: ['./navigation-registered.component.css']
})
export class NavigationRegisteredComponent {
  @Output() signOut = new EventEmitter<void>();
  constructor() { }

  signOutUser(){
    this.signOut.emit();
  }

}
