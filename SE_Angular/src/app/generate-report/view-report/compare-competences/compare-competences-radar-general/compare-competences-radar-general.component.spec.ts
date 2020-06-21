import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompareCompetencesRadarGeneralComponent } from './compare-competences-radar-general.component';

describe('CompareCompetencesRadarGeneralComponent', () => {
  let component: CompareCompetencesRadarGeneralComponent;
  let fixture: ComponentFixture<CompareCompetencesRadarGeneralComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompareCompetencesRadarGeneralComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompareCompetencesRadarGeneralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
