import {Component, Input, OnInit} from '@angular/core';
import {latLng, LayerGroup, tileLayer} from 'leaflet';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  @Input() items;
  // items: LayerGroup = new LayerGroup();
  options = {
    layers: [
      tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 10, attribution: '...' })
    ],
    zoom: 2,
    center: latLng(48, -4)
  };
  layerMainGroup: LayerGroup[] = [
    this.items
  ];
  constructor() { }

  ngOnInit(): void {
    // TODO logic for putting stuff on map
  }

}
