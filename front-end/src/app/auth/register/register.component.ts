import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import User from '../../models/user';
import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';
import { Router } from '@angular/router';
import passwordValidator from '../password-validator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  registerForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    name: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    passwordGroup: new FormGroup({
      password: new FormControl('', [Validators.required]),
      confirmPassword: new FormControl('', [Validators.required]),
    },{
      validators: passwordValidator
    })
  });

  constructor (private authService: AuthService, private router: Router) {}

  getFormValuesAsUser(): User {
    let { value } = this.registerForm;

    return {
      id: null,
      email: value.email!,
      name: value.name!,
      lastName: value.lastName!,
      password: value.passwordGroup!.password!,
      bio: null,
      areaOfInterest: null,
      profilePictureUrl: null,
      roles: null,
    };
  }

  async onSubmit() {
    if (! this.registerForm.valid) {
      return;
    }

    try {
      let user = await this.authService.register(this.getFormValuesAsUser());

      this.router.navigate(['/products']);
    } catch (error) {
      const httpError = error as HttpErrorResponse;

      if (httpError.status === HttpStatusCode.Conflict) {
        this.registerForm.controls.email.setErrors({ alreadyExists: true });
      }
    }
  }
}
