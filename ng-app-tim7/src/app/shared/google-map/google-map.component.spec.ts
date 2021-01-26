import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import { GoogleMapComponent } from './google-map.component';
import {AgmCoreModule, MapsAPILoader} from '@agm/core';
import {MapDataModel} from '../../models/map-data.model';

describe('GoogleMapComponent', () => {
  let component: GoogleMapComponent;
  let fixture: ComponentFixture<GoogleMapComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GoogleMapComponent ],
      imports: [ AgmCoreModule],
      providers: [
        {
          provide: MapsAPILoader,
          useValue: {
            load: jasmine.createSpy('load').and.returnValue(new Promise(() => true))
          }
        }]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GoogleMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  describe('ngOnChanges()', () => {
    it('should change property value in ngOnChanges lifecycle', () => {
      component.focusInput = {latitude: 10.1, longitude: 12.2, id: 2};
      component.ngOnChanges();
      fixture.detectChanges();
      expect(component.zoom).toEqual(15);
      expect(component.centerLatitude).toEqual(10.1);
      expect(component.centerLongitude).toEqual(12.2);
    });
  });
  describe('dragEnd()', () => {
    it('should emit dragEnd()', () => {

      component.dragEnd( {lat: 10.1, lng: 12.2});
      fixture.detectChanges();
      expect(component.location).toEqual('');
    });
  });
  describe('changedZoom()', () => {
    it('should change the zoom', () => {

      component.changedZoom( 20);
      fixture.detectChanges();
      expect(component.zoom).toEqual(20);
    });
  });
  describe('centerChange()', () => {
    it('should change the latitude and longitude', () => {

      component.centerChange();
      fixture.detectChanges();
      expect(component.centerLatitude).toEqual(component.agmMap.latitude);
      expect(component.centerLongitude).toEqual(component.agmMap.longitude);
    });
  });
});
