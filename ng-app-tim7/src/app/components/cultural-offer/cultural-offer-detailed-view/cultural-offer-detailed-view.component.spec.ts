import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CulturalOfferDetailedViewComponent } from './cultural-offer-detailed-view.component';

describe('CulturalOfferDetailedViewComponent', () => {
  let component: CulturalOfferDetailedViewComponent;
  let fixture: ComponentFixture<CulturalOfferDetailedViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CulturalOfferDetailedViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferDetailedViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
