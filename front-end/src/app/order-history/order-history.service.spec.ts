import { TestBed } from '@angular/core/testing';

import { OrderHistoryService } from './order-history.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('OrderHistoryService', () => {
  let service: OrderHistoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(OrderHistoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
