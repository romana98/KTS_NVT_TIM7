import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild} from '@angular/core';
import {AgmMap, LatLngLiteral} from '@agm/core';
import {CulturalofferModel} from '../../../models/culturaloffer.model';
import {Mappable} from '../../../models/mappable.interface';

@Component({
  selector: 'app-google-map',
  templateUrl: './google-map.component.html',
  styleUrls: ['./google-map.component.css']
})
export class GoogleMapComponent implements OnInit, OnChanges {
  @ViewChild(AgmMap) agmMap: AgmMap;
  /*
  Expecting Mappable interface in model:
  Mappable {
    longitude: number;
    latitude: number;
    id: number;
  }
   */
  @Input() input: Mappable[] = [];
  @Input() draggable;
  @Input() focusInput: Mappable;
  @Output() DragEnd = new EventEmitter<LatLngLiteral>();
  centerLatitude;
  centerLongitude;
  zoom = 10;

  constructor() { }

  ngOnInit(): void {

  }

  ngOnChanges(): void{
    if (this.focusInput !== undefined){
      this.zoom = 15;
      this.centerLatitude = this.focusInput.latitude;
      this.centerLongitude = this.focusInput.longitude;
    }
  }

  dragEnd(coords: LatLngLiteral) {
    this.DragEnd.emit(coords);
  }

  changedZoom($event: number) {
    this.zoom = $event;
  }
  // mouse up
  centerChange() {
    this.centerLongitude = this.agmMap.longitude;
    this.centerLatitude = this.agmMap.latitude;
  }
}
