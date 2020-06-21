import { TestBed } from '@angular/core/testing';

import { EntityTableService } from './entity-table.service';

describe('EntityTableService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EntityTableService = TestBed.get(EntityTableService);
    expect(service).toBeTruthy();
  });
});
