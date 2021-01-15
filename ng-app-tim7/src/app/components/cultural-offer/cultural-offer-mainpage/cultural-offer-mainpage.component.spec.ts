import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CulturalOfferMainpageComponent } from './cultural-offer-mainpage.component';

describe('CulturalOfferMainpageComponent', () => {
  let component: CulturalOfferMainpageComponent;
  let fixture: ComponentFixture<CulturalOfferMainpageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CulturalOfferMainpageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferMainpageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
