import { Component, Input, OnInit } from '@angular/core';
import { Book } from '../models/book';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BookFile } from '../models/bookFile';

@Component({
  selector: 'app-display-book',
  templateUrl: './display-book.component.html',
  styleUrls: ['./display-book.component.css'],
})
export class DisplayBookComponent implements OnInit {
  book!: Book;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit() {
    const bookId = this.route.snapshot.paramMap.get('bookId');
    this.http
      .get<Book>('http://localhost:6200/audible/book-api/book/' + bookId)
      .subscribe(
        (bookData: Book) => {
          this.book = bookData;
          console.log('DisplayBook, book');
          console.log(this.book);
        },
        (error: any) => {
          console.log('DisplayBook, display book error ==> ');
          console.log(error);
        }
      );
  }

  addBookToLibrary(bookId: string) {
    const userId = sessionStorage.getItem('userId');
    this.http
      .post(
        'http://localhost:6300/audible/library-api/library/add-book/' +
          bookId +
          '/user/' +
          userId,
        {
          responseType: 'text',
        }
      )
      .subscribe(
        (libraryResponse: string | any) => {
          console.log('DisplayBook, go to library');
          console.log(libraryResponse);
          this.router.navigate(['/library']);
        },
        (error: any) => {
          console.log('DisplayBook, display book error ==> ');
          console.log(error);
        }
      );
  }
}
