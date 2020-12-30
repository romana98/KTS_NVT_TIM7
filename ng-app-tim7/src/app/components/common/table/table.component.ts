import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit, OnChanges {
  @Input() dataSource;
  @Input() columnsToDisplay;
  @Input() columnsToIterate;
  @Output() Delete = new EventEmitter<number>();
  @Output() Click = new EventEmitter<number>();
  @Output() DoubleClick = new EventEmitter<number>();
  constructor() {

  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    for (const propName in changes) {
      if (changes.hasOwnProperty(propName)){
        let vary = this.get(propName);
        vary = changes[propName].currentValue;
      }
    }
  }
  deleted(id: number){
    this.Delete.emit(id);
  }
  clicked(id: number){
    this.Click.emit(id);
  }
  doubleClicked(id: number){
    this.DoubleClick.emit(id);
  }
  get(element: string){
    switch (element) {
      case 'dataSource':
        return this.dataSource;
      case 'columnsToDisplay':
        return this.columnsToDisplay;
      case 'Delete':
        return this.Delete;
      default:
        return this.columnsToIterate;
    }
  }
}