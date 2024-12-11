import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  loginFailure = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
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
          Validators.minLength(7),
          Validators.maxLength(20),
        ],
      ],
    });
  }

  login() {
    this.http
      .post(
        'http://localhost:6100/audible/user-api/user/login',
        this.loginForm.value,
        { responseType: 'text' }
      )
      .subscribe(
        (response: any) => {
          let c = JSON.parse(response);
          sessionStorage.setItem('userId', '' + c.userId);
          sessionStorage.setItem('emailId', '' + c.emailId);
          sessionStorage.setItem('userName', '' + c.fullName);
          console.log(response);
          this.router.navigate(['/home']);
        },
        (error) => {
          console.log(error);
          this.loginFailure = true;
        }
      );
  }

  registerPage() {
    this.router.navigate(['/register']);
  }
}
