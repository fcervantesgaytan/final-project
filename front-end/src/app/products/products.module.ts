import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { WatchProductComponent } from './watch-product/watch-product.component';
import { ProductsPaginationComponent } from './products-pagination/products-pagination.component';
import { canActivateAuthenticatedGuard } from '../auth/can-activate-authenticated.guard';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatCardModule } from '@angular/material/card';
import { ProductComponent } from './product/product.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ProductModalComponent } from './product-modal/product-modal.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

const routes: Routes = [
  { path: '', component: ProductsPaginationComponent, canActivate: [canActivateAuthenticatedGuard] },
  { path: ':id', component: WatchProductComponent, canActivate: [canActivateAuthenticatedGuard] },
];

@NgModule({
  declarations: [
    WatchProductComponent,
    ProductsPaginationComponent,
    ProductComponent,
    ProductModalComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    MatPaginatorModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
    MatFormFieldModule,
    FormsModule,
    MatInputModule,
    MatDialogModule,
    MatProgressSpinnerModule,
  ],
  exports: [RouterModule]
})
export class ProductsModule { }
