import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PassedExamsPieChartComponent } from './passed-exams-pie-chart.component';

describe('PassedExamsPieChartComponent', () => {
  let component: PassedExamsPieChartComponent;
  let fixture: ComponentFixture<PassedExamsPieChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PassedExamsPieChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PassedExamsPieChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
