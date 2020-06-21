import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PassedExamsBarChartComponent } from './passed-exams-bar-chart.component';

describe('PassedExamsBarChartComponent', () => {
  let component: PassedExamsBarChartComponent;
  let fixture: ComponentFixture<PassedExamsBarChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PassedExamsBarChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PassedExamsBarChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
