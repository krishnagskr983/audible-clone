import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CustomValidators } from './custom-validators';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  registerSuccess = false;
  registerFailure = false;
  hideRegister = true;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      fullName: [
        '',
        [Validators.required, Validators.pattern('^[a-zA-Z_ ]*$')],
      ],
      emailId: [
        '',
        [
          Validators.required,
          Validators.pattern('^[a-zA-Z0-9]*@[a-zA-Z]*.(com|in)$'),
        ],
      ],
      password: [
        '',
        [
          Validators.required,
          this.lowercase,
          this.uppercase,
          this.digit,
          this.special,
          Validators.minLength(7),
          Validators.maxLength(20),
        ],
      ],
      contactNumber: [
        '',
        [Validators.required, Validators.pattern('^[6-9]{1}[0-9]{9}$')],
      ],
      gender: ['', Validators.required],
      dateOfBirth: [
        '',
        [Validators.required, CustomValidators.validateDateOfBirth],
      ],
    });
  }

  register() {
    this.http
      .post(
        'http://localhost:6100/audible/user-api/user/register',
        this.registerForm.value,
        { responseType: 'text' }
      )
      .subscribe(
        (resultData: any) => {
          console.log(resultData);
          // sessionStorage.setItem('userId', resultData);
          this.registerSuccess = true;
        },
        (error) => {
          console.log('displaying error in register');
          console.log(error);
          this.registerFailure = true;
        }
      );
    this.hideRegister = false;
  }

  loginPage() {
    this.router.navigate(['login']);
  }

  lowercase(c: FormControl) {
    if (!c.value.match('(?=.*[a-z])')) {
      return { lowercase: { msg: true } };
    }
    return null;
  }

  uppercase(c: FormControl) {
    if (!c.value.match('(?=.*[A-Z])')) {
      return { uppercase: { msg: true } };
    }
    return null;
  }

  digit(c: FormControl) {
    if (!c.value.match('(?=.*[0-9])')) {
      return { digit: { msg: true } };
    }
    return null;
  }

  special(c: FormControl) {
    if (!c.value.match('(?=.*?[#?!@$%^&*-])')) {
      return { special: { msg: true } };
    }
    return null;
  }

  hideFailure() {
    this.registerFailure = false;
    this.hideRegister = true;
  }
}
