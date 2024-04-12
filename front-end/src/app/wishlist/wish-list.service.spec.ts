import { TestBed } from '@angular/core/testing';

import { WishListService } from './wish-list.service';
import { HttpClientModule } from '@angular/common/http';

describe('WishListService', () => {
  let service: WishListService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule]
    });
    service = TestBed.inject(WishListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
