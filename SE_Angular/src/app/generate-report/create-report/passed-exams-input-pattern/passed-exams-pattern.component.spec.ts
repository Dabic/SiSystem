import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PassedExamsPatternComponent } from './passed-exams-pattern.component';

describe('PassedExamsPatternComponent', () => {
  let component: PassedExamsPatternComponent;
  let fixture: ComponentFixture<PassedExamsPatternComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PassedExamsPatternComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PassedExamsPatternComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
