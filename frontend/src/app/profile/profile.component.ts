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
import { User } from '../models/user';
import { UserFile } from '../models/userFile';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  postUser: any = {};
  profileForm!: FormGroup;
  photoCard = false;
  profileData!: User;
  userImage!: string;
  userId!: string;
  email!: string;
  fName!: string;
  contact!: string;
  gen!: string;
  dob!: Date;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    // this.getProfilePhoto();
    this.getProfile();

    console.log('starting delay');

    setTimeout(() => {
      console.log('This message will be logged after 2000 milliseconds');
      console.log('this email is ==> ', this.email);
      console.log('this fName is ==> ', this.fName);

      this.profileForm = this.fb.group({
        userId: this.userId,
        myName: [
          this.fName,
          [Validators.required, Validators.pattern('^[a-zA-Z_ ]*$')],
        ],
        fullName: [
          this.fName,
          [Validators.required, Validators.pattern('^[a-zA-Z_ ]*$')],
        ],
        emailId: [
          this.email,
          [
            Validators.required,
            Validators.pattern('^[a-zA-Z0-9]*@[a-zA-Z]*.(com|in)$'),
          ],
        ],
        contactNumber: [
          this.contact,
          [Validators.required, Validators.pattern('^[6-9]{1}[0-9]{9}$')],
        ],
        gender: [this.gen, Validators.required],
        dateOfBirth: [
          this.dob,
          [Validators.required, CustomValidators.validateDateOfBirth],
        ],
      });

      console.log('Method end');
    }, 50);
  }

  getProfilePhoto() {
    const userId = sessionStorage.getItem('userId');
    this.http
      .get<UserFile>(
        'http://localhost:6100/audible/user-api/user/get-profile-photo/' +
          userId
      )
      .subscribe(
        (profilePhotoData) => {
          console.log('Get profile data is ==>');
          this.userImage = profilePhotoData.filePath;
          console.log(this.userImage);
        },
        (error) => {
          console.log('display get error ==> ');
          console.log(error);
        }
      );
  }

  getProfile() {
    const userId = sessionStorage.getItem('userId');
    this.http
      .get<User>(
        'http://localhost:6100/audible/user-api/user/get-profile/' + userId
      )
      .subscribe(
        (profileData) => {
          console.log('Profile, Get profile data is ==>');
          console.log(profileData);
          this.profileData = profileData;
          this.userId = this.profileData.userId;
          this.fName = this.profileData.fullName;
          this.email = this.profileData.emailId;
          this.contact = this.profileData.contactNumber;
          this.gen = this.profileData.gender;
          this.dob = this.profileData.dateOfBirth;
          this.userImage = this.profileData.userPhotoDTO.filePath;
          console.log(this.userImage);
        },
        (error) => {
          console.log('display get error ==> ');
          console.log(error);
        }
      );
  }

  updateProfile() {
    console.log('Update form');
    console.log(this.profileForm.value);
    this.http
      .put(
        'http://localhost:6100/audible/user-api/user/update-profile',
        this.profileForm.value,
        { responseType: 'text' }
      )
      .subscribe(
        (resultData: any) => {
          console.log(resultData);
        },
        (error) => {
          console.log('displaying error in profile');
          console.log(error);
        }
      );
    this.cancelProfile();
  }

  cancelProfile() {
    this.router.navigate(['/home']);
  }

  updateProfilePhoto() {
    const userFormData = new FormData();
    userFormData.append('userPhoto', this.postUser.userPhoto);
    userFormData.append('userId', this.userId);
    // console.log('Profile, Userphoto');
    // console.log(this.postUser.userPhoto);
    // console.log('submit user photo form', userFormData);
    // console.log('profile post user ==>');
    // console.log(this.postUser);
    this.photoCard = false;
    this.http
      .put(
        'http://localhost:6100/audible/user-api/user/update-profile-photo',
        userFormData,
        {
          responseType: 'text',
        }
      )
      .subscribe(
        (resultData: any) => {
          console.log(resultData);
        },
        (error) => {
          console.log('displaying error --> ');
          console.log(error);
        }
      );
    this.userImage = '../../assets/User_Photos/' + this.postUser.userPhoto.name;
    console.log('modified image path is ==>');
    console.log(this.userImage);
  }

  removeProfilePhoto() {
    console.log('Profile, Removing profile');
    this.photoCard = false;
    this.http
      .delete(
        'http://localhost:6100/audible/user-api/user/delete-profile-photo/' +
          this.userId,
        {
          responseType: 'text',
        }
      )
      .subscribe(
        (resultData: any) => {
          console.log('Profile, Remove Response');
          console.log(resultData);
          this.getProfilePhoto();
        },
        (error) => {
          console.log('Profile, RemoveProfilePhoto displaying error --> ');
          console.log(error);
        }
      );
  }

  onUserPhotoSelected(event: any) {
    this.postUser.userPhoto = event.target.files[0];
  }

  openPhotoCard() {
    this.photoCard = true;
  }

  closePhotoCard() {
    this.photoCard = false;
  }
}
