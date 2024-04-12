import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserService } from '../users/user.service';
import { AuthService } from '../auth/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class WishListService {

  constructor(private userService: UserService, private authService: AuthService, private matSnackBar: MatSnackBar) { }

  public addToWishList(userId: number, productId: number) {
    return this.userService.addProductToWishList(userId, productId)
      .subscribe((products) => {
          this.matSnackBar.open('Product added to wishlist', 'Close', { duration: 3000 });
      });
  }
}
