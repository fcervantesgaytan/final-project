import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import Product from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private BASE_URL = 'products';

  constructor(private httpClient: HttpClient) { }

  getAllProducts(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(`${this.BASE_URL}`);
  }

  getProduct(id: number): Observable<Product> {
    return this.httpClient.get<Product>(`${this.BASE_URL}/${id}`);
  }

  getProductByName(name: string): Observable<Product> {
    return this.httpClient.get<Product>(`${this.BASE_URL}/searchname?name=${name}`);
  }

  getProductsPaginated(pageNumber: number, pageSize: number): Observable<Product[]> {
    return this.httpClient.get<Product[]>(`${this.BASE_URL}/paginate?pageNumber=${pageNumber}&pageSize=${pageSize}`);
  }

  getProductsCount(): Observable<number> {
    return this.httpClient.get<number>(`${this.BASE_URL}/count`);
  }

  getProductsByPriceRange(minPrice: number, maxPrice: number): Observable<Product[]> {
    return this.httpClient.get<Product[]>(`${this.BASE_URL}/searchprice?minPrice=${minPrice}&maxPrice=${maxPrice}`);
  }

  saveProduct(product: Product): Observable<Product> {
    return this.httpClient.post<Product>(`${this.BASE_URL}`, product);
  }

  updateProduct(id: number, product: Product): Observable<Product> {
    return this.httpClient.put<Product>(`${this.BASE_URL}/${id}`, product);
  }

  deleteProduct(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.BASE_URL}/${id}`);
  }

  buyProduct(productId: number, userId: number) {
    return this.httpClient.post(`${this.BASE_URL}/${productId}/buy/${userId}`, null);
  }

  buyProducts(userId: number, products: Product[]): Observable<Product[]> {
    return this.httpClient.post<Product[]>(`${this.BASE_URL}/buy/${userId}`, products);
  }

}
