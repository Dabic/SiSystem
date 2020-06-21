import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ObligationsPrintPatternComponent } from './obligations-print-pattern.component';

describe('ObligationsPrintPatternComponent', () => {
  let component: ObligationsPrintPatternComponent;
  let fixture: ComponentFixture<ObligationsPrintPatternComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ObligationsPrintPatternComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ObligationsPrintPatternComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
