import { Component, OnInit } from '@angular/core';
import { Library } from '../models/library';
import { Book } from '../models/book';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css'],
})
export class LibraryComponent implements OnInit {
  libraryList!: Library[];
  libraryBookList: Book[] = [];
  userId: string | null | undefined;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.userId = sessionStorage.getItem('userId');
    this.getLibraryBooks();
  }

  getLibraryBooks() {
    // this.query = this.route.snapshot.paramMap.get('query');

    this.http
      .get<Library[]>(
        'http://localhost:6300/audible/library-api/library/get-books/user/655b3e1ed236fc2982f8a01e'
      )
      .subscribe(
        (libraryData) => {
          this.libraryList = libraryData;
          console.log('Library, libraryData get books');
          console.log(this.libraryList);
          for (const library of this.libraryList) {
            console.log('Library, push library books');
            console.log(library);
            if (library && library.bookDTO) {
              console.log('Library, before push');
              this.libraryBookList.push(library.bookDTO);
              console.log('Library, after push');
            }
          }
        },
        (errorResponse) => {
          console.log('CustomBook, error getAllLibraryBooks');
          console.log(errorResponse.error.message);
        }
      );
  }
}
