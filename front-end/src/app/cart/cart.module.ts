import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartComponent } from './cart/cart.component';
import { RouterModule, Routes } from '@angular/router';
import { canActivateAuthenticatedGuard } from '../auth/can-activate-authenticated.guard';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { ProductComponent } from './product/product.component';

const routes: Routes = [
  { path: '', component: CartComponent, canActivate: [canActivateAuthenticatedGuard] }
];

@NgModule({
  declarations: [
    CartComponent,
    ProductComponent,
  ],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatInputModule,
  ],
  exports: [RouterModule]
})
export class CartModule { }
