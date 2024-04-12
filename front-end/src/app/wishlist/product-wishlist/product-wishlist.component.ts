import { Component, EventEmitter, Input, Output } from '@angular/core';
import Product from '../../models/product';
import { environment } from '../../../environments/environment';
import { CartService } from '../../cart/cart.service';
import { UserService } from '../../users/user.service';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-product-wishlist',
  templateUrl: './product-wishlist.component.html',
  styleUrl: './product-wishlist.component.css'
})
export class ProductWishlistComponent {
  @Input()
  product: Product;
  backendUrl = environment.backendUrl;

  @Output()
  deletedEventEmitter = new EventEmitter();

  constructor (private cartService: CartService, private authService: AuthService, private userService: UserService) { }

  deleteFromWishList() {
    this.userService.deleteProductFromWishList(this.authService.getLoggedInUser()?.id!, this.product.id)
      .subscribe(() => {
        this.deletedEventEmitter.emit();
      });
  }

  addToWishList() {
    this.cartService.addItem(this.product);
  }

}
