import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import User from '../../models/user';
import { Router } from '@angular/router';
import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
  });

  constructor (private authService: AuthService, private router: Router) {}

  protected getFormValuesAsUser(): User {
    let { value } = this.loginForm;

    return {
      id: null,
      email: value.email!,
      name: null,
      lastName: null,
      password: value.password!,
      bio: null,
      areaOfInterest: null,
      profilePictureUrl: null,
      roles: null,
    };
  }

  async onSubmit() {
    if (! this.loginForm.valid) {
      return;
    }

    try {
      let user = await this.authService.login(this.getFormValuesAsUser());

      this.router.navigate(['/products']);
    } catch (error) {
      let httpError = error as HttpErrorResponse;

      if (httpError.status === HttpStatusCode.NotFound) {
        this.loginForm.controls.email.setErrors({ notFound: true });
      } else if (httpError.status === HttpStatusCode.Unauthorized) {
        this.loginForm.controls.email.setErrors({ unauthorized: true });
        this.loginForm.controls.password.setErrors({ unauthorized: true });
      }
    }
  }
}
