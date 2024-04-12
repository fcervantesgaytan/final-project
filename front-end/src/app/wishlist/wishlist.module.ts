import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WishlistComponent } from './wishlist/wishlist.component';
import { RouterModule, Routes } from '@angular/router';
import { canActivateAuthenticatedGuard } from '../auth/can-activate-authenticated.guard';
import { ProductWishlistComponent } from './product-wishlist/product-wishlist.component';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';

const routes: Routes = [
  { path: '', component: WishlistComponent, canActivate: [canActivateAuthenticatedGuard] }
];


@NgModule({
  declarations: [
    WishlistComponent,
    ProductWishlistComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    MatCardModule,
    MatButtonModule,
  ],
  exports: [RouterModule]
})
export class WishlistModule { }
