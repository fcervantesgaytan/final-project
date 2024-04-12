import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import User from '../models/user';
import Product from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private BASE_URL = 'users';

  constructor(private httpClient: HttpClient) { }

  getAllUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(this.BASE_URL);
  }

  getUser(id: number): Observable<User> {
    return this.httpClient.get<User>(`${this.BASE_URL}/${id}`);
  }

  getUsersByName(firstName: string, lastName: string): Observable<User[]> {
    return this.httpClient.get<User[]>(`${this.BASE_URL}/searchname?firstName=${firstName}&lastName=${lastName}`);
  }

  getUserByEmail(email: string): Observable<User> {
    return this.httpClient.get<User>(`${this.BASE_URL}/searchemail?email=${email}`);
  }

  saveUser(user: User): Observable<User> {
    return this.httpClient.post<User>(`${this.BASE_URL}`, user);
  }

  updateUser(id: number, user: User): Observable<User> {
    return this.httpClient.put<User>(`${this.BASE_URL}/${id}`, user);
  }

  deleteUser(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.BASE_URL}/${id}`);
  }

  getWishList(id: number): Observable<Product[]> {
    return this.httpClient.get<Product[]>(`${this.BASE_URL}/${id}/wishlist`);
  }

  addProductToWishList(userId: number, productId: number): Observable<Product[]> {
    return this.httpClient.post<Product[]>(`${this.BASE_URL}/${userId}/wishlist/${productId}`, null);
  }

  deleteProductFromWishList(userId: number, productId: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.BASE_URL}/${userId}/wishlist/${productId}`);
  }

  clearWishList(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.BASE_URL}/${id}/wishlist`);
  }

}
