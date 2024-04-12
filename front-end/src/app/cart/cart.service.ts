import { Injectable } from '@angular/core';
import { ProductService } from '../products/product.service';
import Product from '../models/product';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../auth/auth.service';
import { Subject, forkJoin } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private CART_KEY = 'CART';
  private products: Product[];
  public updateProductsSubscriber = new Subject<Product[]>;

  public getProducts(): Product[] {
    return this.products;
  }

  constructor(private productService: ProductService, private authService: AuthService, private matSnackBar: MatSnackBar, private router: Router) {
    const products = localStorage.getItem(this.CART_KEY);

    if (products) {
      this.products = JSON.parse(products);
    } else {
      this.products = [];
    }
  }

  public buyProducts() {
    this.productService.buyProducts(this.authService.getLoggedInUser()?.id!, this.products)
      .subscribe((products) => {
        this.products = [];
        this.updateProductsSubscriber.next(this.products.slice());
        localStorage.setItem(this.CART_KEY, '[]');

        this.router.navigate(['/profile']);
      });
  }

  addItem(product: Product): void {
    if (product.totalProductsInventory === this.products.filter(p => p.id === product.id).length) {
      this.matSnackBar.open(`There isn't enough stock of this item to add more`, 'Close', { duration: 3000 });
      return;
    }

    this.products.push(product);
    localStorage.setItem(this.CART_KEY, JSON.stringify(this.products));

    this.updateProductsSubscriber.next(this.products.slice());
    this.matSnackBar.open(`${product.name} added to Cart`, 'Close', { duration: 3000 });
  }

  deleteItem(product: Product): void {
    const productIndex = this.products.findIndex((p) => p.id === product.id);
    if (productIndex >= 0) {
      this.products.splice(productIndex, 1);
    }

    localStorage.setItem(this.CART_KEY, JSON.stringify(this.products));
    this.updateProductsSubscriber.next(this.products.slice());
    this.matSnackBar.open(`${product.name} deleted from Cart`, 'Close', { duration: 3000 });
  }
}
