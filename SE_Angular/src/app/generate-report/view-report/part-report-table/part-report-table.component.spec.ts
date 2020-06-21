import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PartReportTableComponent } from './part-report-table.component';

describe('PartReportTableComponent', () => {
  let component: PartReportTableComponent;
  let fixture: ComponentFixture<PartReportTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PartReportTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PartReportTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
