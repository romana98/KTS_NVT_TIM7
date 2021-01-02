import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-navigation-administrator',
  templateUrl: './navigation-administrator.component.html',
  styleUrls: ['./navigation-administrator.component.css']
})
export class NavigationAdministratorComponent implements OnInit {
  @Input() signOut: () => void;
  constructor() { }

  ngOnInit(): void {
  }

}
