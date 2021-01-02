import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardNewsletterComponent } from './dashboard-newsletter.component';

describe('DashboardNewsletterComponent', () => {
  let component: DashboardNewsletterComponent;
  let fixture: ComponentFixture<DashboardNewsletterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashboardNewsletterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardNewsletterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
