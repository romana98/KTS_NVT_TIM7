import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.css']
})
export class CarouselComponent implements OnInit {

  @Input() inputPictures: string[];
  @Input() inputProportion: number;

  // Returning index of currently selected picture in inputPictures array on every change.
  @Output() currentPicture: EventEmitter<number> = new EventEmitter<number>();

  constructor() { }

  ngOnInit(): void {
  }

  pictureChanged(event: any){
    this.currentPicture.emit(event);
  }

}
