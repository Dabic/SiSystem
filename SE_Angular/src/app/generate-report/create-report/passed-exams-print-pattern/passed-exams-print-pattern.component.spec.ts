import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PassedExamsPrintPatternComponent } from './passed-exams-print-pattern.component';

describe('PassedExamsPrintPatternComponent', () => {
  let component: PassedExamsPrintPatternComponent;
  let fixture: ComponentFixture<PassedExamsPrintPatternComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PassedExamsPrintPatternComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PassedExamsPrintPatternComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
