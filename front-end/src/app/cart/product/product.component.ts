import { Component, Input, OnInit } from '@angular/core';
import Product from '../../models/product';
import { ImageService } from '../../image/image.service';
import { CartService } from '../cart.service';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-product-cart',
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent implements OnInit {
  @Input()
  product: Product;
  backendUrl = environment.backendUrl;

  constructor (private imageService: ImageService, private cartService: CartService) { }

  ngOnInit(): void {

  }

  onDelete(): void {
    this.cartService.deleteItem(this.product);
  }
}
