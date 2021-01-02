import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-navigation-registered',
  templateUrl: './navigation-registered.component.html',
  styleUrls: ['./navigation-registered.component.css']
})
export class NavigationRegisteredComponent implements OnInit {
  @Input() signOut: () => void;
  constructor() { }

  ngOnInit(): void {
  }

}
