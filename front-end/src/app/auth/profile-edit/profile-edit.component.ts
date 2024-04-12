import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import passwordValidator from '../password-validator';
import { FileUploadValidators } from '@iplab/ngx-file-upload';
import { ImageService } from '../../image/image.service';
import { UserService } from '../../users/user.service';
import User from '../../models/user';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrl: './profile-edit.component.css'
})
export class ProfileEditComponent {
  user = this.authService.getLoggedInUser()!;

  editProfileForm = new FormGroup({
    email: new FormControl(this.user.email, [Validators.required, Validators.email]),
    name: new FormControl(this.user.name, [Validators.required]),
    lastName: new FormControl(this.user.lastName, [Validators.required]),
    bio: new FormControl(this.user.bio, []),
    areaOfInterest: new FormControl(this.user.areaOfInterest, []),
    passwordGroup: new FormGroup({
      password: new FormControl('', []),
      confirmPassword: new FormControl('', [])
    }, {
      validators: passwordValidator
    }),
    profilePictureUrl: new FormControl(null, [
      FileUploadValidators.filesLimit(1),
      FileUploadValidators.accept(['.png', '.jpg']),
    ])
  });

  constructor (
    private imageService: ImageService,
    private authService: AuthService,
    private userService: UserService,
    private router: Router,
    private activeRoute: ActivatedRoute,
  ) { }

  getFormValuesAsUser(imgSrc: string | null): User {
    const { value } = this.editProfileForm;

    return {
      id: null,
      email: value.email!,
      name: value.name!,
      lastName: value.lastName!,
      password: value.passwordGroup?.password ? value.passwordGroup.password : null,
      bio: value.bio!,
      areaOfInterest: value.areaOfInterest!,
      profilePictureUrl: imgSrc,
      roles: null,
    };
  }

  onSubmit(): void {
    if (!this.editProfileForm.valid) {
      return;
    }
    const file: File | null = this.editProfileForm.value.profilePictureUrl ? this.editProfileForm.value.profilePictureUrl[0] : null;
    const formData = new FormData();
    if (file) {
      formData.append('file', file);

      this.imageService.uploadImage(file)
        .subscribe({
          next: (image) => {
            this.userService.updateUser(this.user.id!, this.getFormValuesAsUser(image.path))
              .subscribe({
                next: async (user) => {
                  await this.authService.updateUser(user, this.editProfileForm.value.passwordGroup?.password ? this.editProfileForm.value.passwordGroup?.password : null);
                  this.router.navigate(['..'], { relativeTo: this.activeRoute });
                },
                error: (error) => {
                  const httpError = error as HttpErrorResponse;

                  if (httpError.status === HttpStatusCode.Conflict) {
                    this.editProfileForm.controls.email.setErrors({ alreadyExists: true });
                  }
                }
              });
          },
          error: (error) => {
            console.error(error);
          }
        });
    } else {

      this.userService.updateUser(this.user.id!, this.getFormValuesAsUser(null)).subscribe({
        next: async (user) => {
          this.router.navigate(['..'], { relativeTo: this.activeRoute });
          await this.authService.updateUser(user, this.editProfileForm?.value.passwordGroup?.password ? this.editProfileForm?.value.passwordGroup?.password : null);
        }
      });
    }
  }
}
