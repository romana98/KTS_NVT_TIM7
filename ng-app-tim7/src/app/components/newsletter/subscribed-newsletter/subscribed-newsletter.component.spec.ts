import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscribedNewsletterComponent } from './subscribed-newsletter.component';

describe('SubscribedNewsletterComponent', () => {
  let component: SubscribedNewsletterComponent;
  let fixture: ComponentFixture<SubscribedNewsletterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubscribedNewsletterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubscribedNewsletterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
