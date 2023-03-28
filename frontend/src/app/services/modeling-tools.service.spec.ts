import { TestBed } from '@angular/core/testing';

import { ModelingToolsService } from './modeling-tools.service';

describe('ModelingToolsService', () => {
  let service: ModelingToolsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ModelingToolsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
