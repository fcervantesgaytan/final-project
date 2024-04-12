import { NgModule, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegisterComponent } from './register/register.component';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { BaseFormComponent } from './base-form/base-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { canActivateUnauthenticatedGuard } from './can-activate-unauthenticated.guard';
import { ProfileComponent } from './profile/profile.component';
import { canActivateAuthenticatedGuard } from './can-activate-authenticated.guard';
import { ProfileEditComponent } from './profile-edit/profile-edit.component';
import { MatCardModule } from '@angular/material/card';
import { FileUploadModule } from '@iplab/ngx-file-upload';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatTableModule } from '@angular/material/table';

const routes: Routes = [
  { path: 'register', component: RegisterComponent, canActivate: [canActivateUnauthenticatedGuard] },
  { path: 'login', component: LoginComponent, canActivate: [canActivateUnauthenticatedGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [canActivateAuthenticatedGuard] },
  { path: 'profile/edit', component: ProfileEditComponent, canActivate: [canActivateAuthenticatedGuard] }
];

@NgModule({
  declarations: [
    RegisterComponent,
    LoginComponent,
    BaseFormComponent,
    ProfileComponent,
    ProfileEditComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatCardModule,
    FileUploadModule,
    MatTableModule,
  ],
  exports: [RouterModule],
})
export class AuthModule { }
