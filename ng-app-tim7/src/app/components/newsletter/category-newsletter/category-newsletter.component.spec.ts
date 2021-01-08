import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryNewsletterComponent } from './category-newsletter.component';

describe('CategoryNewsletterComponent', () => {
  let component: CategoryNewsletterComponent;
  let fixture: ComponentFixture<CategoryNewsletterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CategoryNewsletterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoryNewsletterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
