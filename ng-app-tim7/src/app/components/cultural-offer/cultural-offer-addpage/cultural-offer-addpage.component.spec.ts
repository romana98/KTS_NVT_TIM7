import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CulturalOfferAddpageComponent } from './cultural-offer-addpage.component';

describe('CulturalOfferAddpageComponent', () => {
  let component: CulturalOfferAddpageComponent;
  let fixture: ComponentFixture<CulturalOfferAddpageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CulturalOfferAddpageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferAddpageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
