import { TestBed } from '@angular/core/testing';

import { GenerateReportService } from './generate-report.service';

describe('GenerateReportService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GenerateReportService = TestBed.get(GenerateReportService);
    expect(service).toBeTruthy();
  });
});
