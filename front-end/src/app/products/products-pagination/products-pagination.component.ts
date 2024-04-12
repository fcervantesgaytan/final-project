import { ChangeDetectorRef, Component } from '@angular/core';
import { ProductService } from '../product.service';
import Product from '../../models/product';
import { forkJoin } from 'rxjs';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-products-pagination',
  templateUrl: './products-pagination.component.html',
  styleUrl: './products-pagination.component.css'
})
export class ProductsPaginationComponent {
  private pageNumber = 0;
  pageSize = 6;
  products: Product[] = [];
  auxProducts: Product[] | null = null;
  totalProducts: number;
  search: string = '';

  handlePageEvent(e: PageEvent) {
    this.pageSize = e.pageSize;
    this.pageNumber = e.pageIndex;
    this.fetchMoreProducts();
  }

  onChangeSearch() {
    console.log(this.search, this.products, this.auxProducts);
    if (this.search == '') {
      this.products = this.auxProducts!;
      this.auxProducts = null;
    } else {
      if (this.auxProducts === null) {
        this.auxProducts = this.products;
      }
      let search = this.search.toLowerCase();

      this.products = this.products.filter((p) => {
        return p.description.toLowerCase().includes(search) || p.name.toLowerCase().includes(search);
      });
    }
  }

  constructor (private productService: ProductService, private ref: ChangeDetectorRef) {
    forkJoin([
      this.productService.getProductsPaginated(this.pageNumber, this.pageSize), this.productService.getProductsCount()
    ])
      .subscribe(([products, totalProducts]) => {
        this.products = products;
        this.totalProducts = totalProducts;
      });
   }

  protected fetchMoreProducts(): void {
    this.productService.getProductsPaginated(this.pageNumber, this.pageSize)
      .subscribe((products) => {
        this.products = products;
      });
  }

}
