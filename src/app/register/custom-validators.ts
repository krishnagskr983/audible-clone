import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export class CustomValidators {
  static patternValidator(regex: RegExp, error: ValidationErrors): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (!control.value) {
        // if control is empty return no error
        return null;
      }

      // test the value of the control against the regexp supplied
      const valid = regex.test(control.value);

      // if true, return no error, else return error passed in the second parameter
      return valid ? null : error;
    };
  }

  static validateDateOfBirth(
    control: AbstractControl
  ): ValidationErrors | null {
    const dob = new Date(control.value);
    const today = new Date();
    const eighteenYearsAgo = new Date();
    eighteenYearsAgo.setFullYear(eighteenYearsAgo.getFullYear() - 18);

    if (dob.getTime() > today.getTime()) {
      return { futureDate: true };
    }

    if (dob.getTime() >= eighteenYearsAgo.getTime()) {
      return { underAge: true };
    }
    return null;
  }

  static passwordMatchValidator: ValidatorFn = (
    control: AbstractControl
  ): ValidationErrors | null => {
    const newPassword = control.get('newPassword')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;

    if (newPassword !== confirmPassword) {
      return { passwordMismatch: true };
    } else {
      return null;
    }
  };
}
