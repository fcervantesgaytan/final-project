import { AbstractControl } from "@angular/forms";

export default function passwordValidator(control: AbstractControl): null {
  let errors = { mustBeEqual: true };
  let password = control.get('password')!;
  let confirmPassword = control.get('confirmPassword')!;
  if (password.hasError('required') || confirmPassword.hasError('required')) {
    return null;
  }


  if (password.value === confirmPassword.value) {
    password.setErrors(null);
    confirmPassword.setErrors(null);
  } else {
    password.setErrors(errors);
    confirmPassword.setErrors(errors);
  }

  return null;
}
