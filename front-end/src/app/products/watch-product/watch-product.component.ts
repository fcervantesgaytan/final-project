import { Component } from '@angular/core';
import Product from '../../models/product';
import { ProductService } from '../product.service';
import { ActivatedRoute } from '@angular/router';
import { ImageService } from '../../image/image.service';
import { CartService } from '../../cart/cart.service';
import { WishListService } from '../../wishlist/wish-list.service';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-watch-product',
  templateUrl: './watch-product.component.html',
  styleUrl: './watch-product.component.css'
})
export class WatchProductComponent {
  product: Product;

  constructor (
    private productService: ProductService,
    private route: ActivatedRoute,
    private imageService: ImageService,
    private cartService: CartService,
    private wishListService: WishListService,
    private authService: AuthService
  ) {
    this.productService.getProduct(this.route.snapshot.params['id'])
      .subscribe((product) => {
        this.product = product;
        if (this.product.image.startsWith("public")) {
          this.imageService.getApiImageLink(this.product.image);
        }

      });
  }

  addToCart(): void {
    this.cartService.addItem(this.product);
  }

  addToWishList(): void {
    this.wishListService.addToWishList(this.authService.getLoggedInUser()?.id!, this.product.id);
  }
}
