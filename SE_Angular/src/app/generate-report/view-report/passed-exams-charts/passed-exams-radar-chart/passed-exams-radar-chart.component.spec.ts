import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PassedExamsRadarChartComponent } from './passed-exams-radar-chart.component';

describe('PassedExamsRadarChartComponent', () => {
  let component: PassedExamsRadarChartComponent;
  let fixture: ComponentFixture<PassedExamsRadarChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PassedExamsRadarChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PassedExamsRadarChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
