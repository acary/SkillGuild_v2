import { TestBed } from '@angular/core/testing';

import { InteractionTypeService } from './interaction-type.service';

describe('InteractionTypeService', () => {
  let service: InteractionTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InteractionTypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
