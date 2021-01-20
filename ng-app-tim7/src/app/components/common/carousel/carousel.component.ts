import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.css']
})
export class CarouselComponent {

  @Input() inputPictures: string[] = [];
  @Input() inputProportion: number;

  // Returning index of currently selected picture in inputPictures array on every change.
  @Output() currentPicture: EventEmitter<number> = new EventEmitter<number>();

  constructor() { }

  pictureChanged(event: any){
    this.currentPicture.emit(event);
  }

}
