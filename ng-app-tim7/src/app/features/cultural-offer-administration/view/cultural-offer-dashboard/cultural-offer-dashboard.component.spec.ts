import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CulturalOfferDashboardComponent } from './cultural-offer-dashboard.component';

describe('CulturalOfferDashboardComponent', () => {
  let component: CulturalOfferDashboardComponent;
  let fixture: ComponentFixture<CulturalOfferDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CulturalOfferDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CulturalOfferDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
