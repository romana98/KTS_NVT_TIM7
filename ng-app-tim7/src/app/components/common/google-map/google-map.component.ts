import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {AgmMap, LatLngLiteral} from '@agm/core';

@Component({
  selector: 'app-google-map',
  templateUrl: './google-map.component.html',
  styleUrls: ['./google-map.component.css']
})
export class GoogleMapComponent implements OnInit {
  @ViewChild(AgmMap) agmMap: AgmMap;
  @Input() offers;
  @Input() draggable;
  @Output() DragEnd = new EventEmitter<LatLngLiteral>();
  centerLatitude = 45.2671;
  centerLongitude = 19.8335;
  zoom = 10;
  /*
  offers = [
    {
      id: 1,
      lat: 45.2671,
      lng: 19.8335
    },
    {
      id: 2,
      lat: 44.44,
      lng: 10.0
    },
    {
      id: 3,
      lat: 33.33,
      lng: 1.12
    }
  ];
   */
  constructor() { }

  ngOnInit(): void {
  }
  dragEnd(coords: LatLngLiteral) {
    this.DragEnd.emit(coords);
  }

  clicked() {
    this.zoom = 15;
    this.centerLatitude = 44.44;
    this.centerLongitude = 10.0;
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
