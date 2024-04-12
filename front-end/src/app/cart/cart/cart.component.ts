import { Component } from '@angular/core';
import { CartService } from '../cart.service';
import Product from '../../models/product';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {
  backendUrl = environment.backendUrl;
  products: Product[];

  constructor (private cartService: CartService) {
    this.products = this.cartService.getProducts();
    this.cartService.updateProductsSubscriber
      .subscribe((products) => {
        this.products = products;
      });
  }

  async placeOrder() {
    this.cartService.buyProducts();
  }

  get fullPrice(): number {
    return this.products.reduce((p1, p2) => p1 + p2.price, 0);
  }
}
