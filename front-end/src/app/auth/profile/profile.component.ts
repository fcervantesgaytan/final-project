import { Component } from '@angular/core';
import User from '../../models/user';
import { AuthService } from '../auth.service';
import { OrderHistoryService } from '../../order-history/order-history.service';
import OrderHistory from '../../models/order-history';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  user: User;
  orderHistories: MatTableDataSource<OrderHistory>;
  displayedColumns = ['id', 'orderDate', 'product', 'price'];

  constructor (private authService: AuthService, private orderHistoryService: OrderHistoryService) {
    this.user = this.authService.getLoggedInUser()!;
    this.orderHistoryService.getOrderHistoriesByUser(this.user.id!)
      .subscribe((orderHistories) => {
        this.orderHistories = new MatTableDataSource(orderHistories.reverse());
      });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;

    this.orderHistories.filter = filterValue.trim().toLowerCase();
  }
}
