import { Component } from '@angular/core';
import Product from '../../models/product';
import { UserService } from '../../users/user.service';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrl: './wishlist.component.css'
})
export class WishlistComponent {
  wishListedProducts: Product[];

  constructor (private authService: AuthService, private userService: UserService) {
    this.loadWishListedProducts();
  }

  onDeletedEvent() {
    this.loadWishListedProducts();
  }

  loadWishListedProducts() {
    this.userService.getWishList(this.authService.getLoggedInUser()?.id!)
      .subscribe((products) => {
        this.wishListedProducts = products;
      });
  }
}
