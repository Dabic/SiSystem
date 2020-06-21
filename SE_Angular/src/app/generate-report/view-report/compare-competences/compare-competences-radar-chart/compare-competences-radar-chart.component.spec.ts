import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompareCompetencesRadarChartComponent } from './compare-competences-radar-chart.component';

describe('CompareCompetencesRadarChartComponent', () => {
  let component: CompareCompetencesRadarChartComponent;
  let fixture: ComponentFixture<CompareCompetencesRadarChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompareCompetencesRadarChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompareCompetencesRadarChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
