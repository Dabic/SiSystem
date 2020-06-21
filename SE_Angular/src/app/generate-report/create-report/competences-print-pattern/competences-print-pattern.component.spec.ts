import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompetencesPrintPatternComponent } from './competences-print-pattern.component';

describe('CompetencesPrintPatternComponent', () => {
  let component: CompetencesPrintPatternComponent;
  let fixture: ComponentFixture<CompetencesPrintPatternComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompetencesPrintPatternComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompetencesPrintPatternComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
