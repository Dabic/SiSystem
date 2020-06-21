import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompareCompetencesComponent } from './compare-competences.component';

describe('CompareCompetencesComponent', () => {
  let component: CompareCompetencesComponent;
  let fixture: ComponentFixture<CompareCompetencesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompareCompetencesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompareCompetencesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
