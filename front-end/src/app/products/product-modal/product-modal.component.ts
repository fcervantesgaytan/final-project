import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import Product from '../../models/product';
import { environment } from '../../../environments/environment';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-modal',
  templateUrl: './product-modal.component.html',
  styleUrl: './product-modal.component.css'
})
export class ProductModalComponent {
  backendUrl = environment.backendUrl;

  constructor (
    private router: Router,
    public dialogRef: MatDialogRef<ProductModalComponent>,
    @Inject(MAT_DIALOG_DATA) public product: Product
  ){ }

  onDetails() {
    this.dialogRef.close();
    this.router.navigate(['/products', this.product.id]);
  }

  onCancel() {
    this.dialogRef.close();
  }
}
