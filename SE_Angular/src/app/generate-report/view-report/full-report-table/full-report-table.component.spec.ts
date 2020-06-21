import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FullReportTableComponent } from './full-report-table.component';

describe('FullReportTableComponent', () => {
  let component: FullReportTableComponent;
  let fixture: ComponentFixture<FullReportTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FullReportTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FullReportTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
