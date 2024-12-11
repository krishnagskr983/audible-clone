import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomBookComponent } from './custom-book.component';

describe('CustomBookComponent', () => {
  let component: CustomBookComponent;
  let fixture: ComponentFixture<CustomBookComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CustomBookComponent],
    });
    fixture = TestBed.createComponent(CustomBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
