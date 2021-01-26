import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CarouselComponent } from './carousel.component';
import {MatCarouselModule} from '@ngmodule/material-carousel';
import {SimpleChange} from '@angular/core';
import {AnimationBuilder} from '@angular/animations';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

describe('CarouselComponent', () => {
  let component: CarouselComponent;
  let fixture: ComponentFixture<CarouselComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CarouselComponent ],
      imports: [MatCarouselModule, BrowserAnimationsModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CarouselComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  describe('pictureChanged()', () => {
    it('should emit PictureChanged()', () => {

      const spy = spyOn(component.currentPicture, 'emit');
      component.pictureChanged( 1);
      fixture.detectChanges();
      expect(spy).toHaveBeenCalledWith(1);
    });
  });
});
