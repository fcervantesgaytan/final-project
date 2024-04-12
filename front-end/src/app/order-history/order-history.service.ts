import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import OrderHistory from '../models/order-history';

@Injectable({
  providedIn: 'root'
})
export class OrderHistoryService {
  private BASE_URL = 'orderhistories';

  constructor(private httpClient: HttpClient) {}

  public getOrderHistoriesByUser(userId: number): Observable<OrderHistory[]> {
    return this.httpClient.get<OrderHistory[]>(`${this.BASE_URL}/${userId}`);
  }
}
