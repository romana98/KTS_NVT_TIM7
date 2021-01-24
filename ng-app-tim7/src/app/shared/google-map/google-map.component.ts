import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild} from '@angular/core';
import {AgmMap, LatLngLiteral} from '@agm/core';
import {Mappable} from '../../models/mappable.interface';
import {MapDataModel} from '../../models/map-data.model';

@Component({
  selector: 'app-google-map',
  templateUrl: './google-map.component.html',
  styleUrls: ['./google-map.component.css']
})
export class GoogleMapComponent implements OnChanges {
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
  @Output() DragEnd = new EventEmitter<MapDataModel>();
  centerLatitude;
  centerLongitude;
  zoom = 10;
  location = '';

  constructor() { }

  ngOnChanges(): void{
    if (this.focusInput !== undefined){
      this.zoom = 15;
      this.centerLatitude = this.focusInput.latitude;
      this.centerLongitude = this.focusInput.longitude;
    }
  }

  // returns MapDataModel = {latitude, longitude, locationName}
  dragEnd(coords: LatLngLiteral) {
    // @ts-ignore
    const opencage = require('opencage-api-client');
    opencage.geocode({q: '' + coords.lat + ', ' + coords.lng, key: 'df145e8c933d49e399e5d6703a1e88b1'}).then(data => {
      if (data.status.code === 200) {
        this.location = data.results[0].formatted;
        this.DragEnd.emit(new MapDataModel(coords.lat, coords.lng, data.results[0].formatted));
      }
    }).catch(error => {
    });
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
