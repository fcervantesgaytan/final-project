import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { WatchUserComponent } from './watch-user/watch-user.component';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { EditUserComponent } from './edit-user/edit-user.component';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'users/:id', component: WatchUserComponent },
  { path: 'users/:id/edit', component: EditUserComponent },
];

@NgModule({
  declarations: [
    LoginComponent,
    WatchUserComponent,
    EditUserComponent,
  ],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatIconModule,
    MatInputModule,
    MatButtonModule,
  ],
  exports: [RouterModule]
})
export class UsersModule { }
