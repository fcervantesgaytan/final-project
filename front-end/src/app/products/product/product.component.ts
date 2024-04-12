import { Component, Input } from '@angular/core';
import Product from '../../models/product';
import { environment } from '../../../environments/environment';
import { MatDialog } from '@angular/material/dialog';
import { ProductModalComponent } from '../product-modal/product-modal.component';
import { CartService } from '../../cart/cart.service';
import { WishListService } from '../../wishlist/wish-list.service';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent {
  backEndUrl = environment.backendUrl;

  @Input()
  product: Product;

  constructor (private cartService: CartService, private dialog: MatDialog, private wishListService: WishListService, private authService: AuthService) { }

  openProductModal(): void {
    this.dialog.open(ProductModalComponent, {
      data: this.product
    });
  }

  addToCart(): void {
    this.cartService.addItem(this.product);
  }

  addToWishList(): void {
    this.wishListService.addToWishList(this.authService.getLoggedInUser()?.id!, this.product.id);
  }
}
