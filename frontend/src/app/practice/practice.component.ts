import { Component } from '@angular/core';

import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user';

@Component({
  selector: 'app-practice',
  templateUrl: './practice.component.html',
  styleUrls: ['./practice.component.css'],
})
export class PracticeComponent {
  selectedImage: any;
  uImage: any;
  photoCard = true;
  userId!: string;
  postUser: any = {};
  http: any;

  handleFileInput(event: any): void {
    const file = event.target.files[0];
    this.readAndDisplayImage(file);
  }

  readAndDisplayImage(file: File): void {
    const reader = new FileReader();

    reader.onload = (e: any) => {
      this.selectedImage = e.target.result;
    };

    reader.readAsDataURL(file);
  }

  updateProfilePhoto() {
    // this.userId = '655b4c86dd85e62851a80d31';
    // this.postUser.userId = sessionStorage.getItem('userId');
    console.log('sending request update profile photo ==> ');
    // console.log('post User ==> ');
    // console.log(this.postUser);
    // this.photoCard = true;
    // const userFormData = new FormData();
    // userFormData.append('userPhoto', this.postUser.userPhoto);
    // userFormData.append('userId', this.userId);
    // console.log(' user form data', userFormData);
    // this.http
    //   .get(
    //     'http://localhost:6100/audible/user-api/user/update-profile-photo',
    //     { userId: '123' },
    //     {
    //       responseType: 'text',
    //     }
    //   )

    this.http
      .get(
        'http://localhost:6100/audible/user-api/user/get-profile/655b4c86dd85e62851a80d31'
      )
      .subscribe(
        (resultData: User) => {
          console.log('profile photo result data ==> ');
          console.log(resultData);
        },
        (error: any) => {
          console.log('displaying error --> ');
          console.log(error);
        }
      );

    console.log('after request');
  }

  onUserPhotoSelected(event: any) {
    console.log('reading photo');
    this.postUser.userPhoto = event.target.files[0];
    this.updateProfilePhoto();
    // setTimeout(() => {
    // }, 5000);
    // console.log(this.postUser);
    // this.readAndUploadPhoto(this.postUser.userPhoto);
  }

  // readAndUploadPhoto(file: File): void {
  //   const reader = new FileReader();
  //   reader.onload = (e: any) => {
  //     this.uImage = e.target.result;
  //   };
  //   reader.readAsDataURL(file);

  //   this.updateProfilePhoto();
  // }

  openPhotoCard() {
    this.photoCard = true;
  }

  closePhotoCard() {
    this.photoCard = false;
    this.photoCard = true;
  }
}
