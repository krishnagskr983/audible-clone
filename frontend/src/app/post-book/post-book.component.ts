import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-book',
  templateUrl: './post-book.component.html',
  styleUrls: ['./post-book.component.css'],
})
export class PostBookComponent implements OnInit {
  postBook: any = { genre: '' };
  postSuccess = false;
  postFailure = false;
  hidePost = true;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {}

  submitBookForm() {
    const bookFormData = new FormData();
    bookFormData.append('bookTitle', this.postBook.bookTitle);
    bookFormData.append('bookOverview', this.postBook.bookOverview);
    bookFormData.append('genre', this.postBook.genre);
    bookFormData.append('bookPrice', this.postBook.bookPrice);
    bookFormData.append('authorName', this.postBook.authorName);
    bookFormData.append('narratorName', this.postBook.narratorName);
    bookFormData.append('length', this.postBook.length);
    bookFormData.append('description', this.postBook.description);
    bookFormData.append('ratings', this.postBook.ratings);
    bookFormData.append('bookPhoto', this.postBook.bookPhoto);
    bookFormData.append('bookAudio', this.postBook.bookAudio);

    this.http
      .post('http://localhost:6200/audible/book-api/book/add', bookFormData, {
        responseType: 'text',
      })
      .subscribe(
        (resultData: any) => {
          this.postSuccess = true;
          console.log(resultData);
        },
        (error) => {
          console.log('displaying error --> ');
          console.log(error);
          this.postFailure = true;
        }
      );
    this.hidePost = false;
  }

  onBookPhotoSelected(event: any) {
    this.postBook.bookPhoto = event.target.files[0];
  }

  onBookAudioSelected(event: any) {
    this.postBook.bookAudio = event.target.files[0];
  }

  closePopup() {
    this.postFailure = false;
    this.postSuccess = false;
    this.hidePost = true;
  }
}
