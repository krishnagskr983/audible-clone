import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Book } from '../models/book';
import { UserFile } from '../models/userFile';
import { Notify } from '../models/notify';

@Component({
  selector: 'app-custom-book',
  templateUrl: './custom-book.component.html',
  styleUrls: ['./custom-book.component.css'],
})
export class CustomBookComponent implements OnInit {
  @Input() books: Book[] = [];
  bookList!: Book[];
  userId!: string | null;
  query!: string | null;
  queryKey!: string;
  queryValue!: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.query = this.route.snapshot.paramMap.get('query');
    if (this.query != null) {
      this.getBooks();
    }
  }

  onBookClick(book: Book) {
    this.router.navigate(['/display-book', book.bookId]);
    console.log('Book, on book clicked');
    console.log(book);
  }

  browseBooks(query: string) {
    if (query != null) {
      this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
        // this.router.navigate(['book', query]);
        this.router.navigate(['custom-book', query]);
      });
    }
  }

  getBooks() {
    this.query = this.route.snapshot.paramMap.get('query');
    if (this.query != null) {
      console.log('Book, query');
      console.log(this.query);
      this.queryKey = this.query.split('-')[0];
      this.queryValue = this.query?.split('-')[1];
      console.log('Book, query key');
      console.log(this.queryKey);
      console.log('Book, queryValue');
      console.log(this.queryValue);
    }
    this.http
      .get<Book[]>(
        'http://localhost:6200/audible/book-api/books/pageNumber/' +
          0 +
          '/pageSize/100'
      )
      .subscribe(
        (books) => {
          this.bookList = books;
          console.log('Book, category');

          if (this.query == null) {
            this.bookList = this.bookList;
          } else if (this.queryKey == 'category') {
            if (this.queryValue == 'all' || this.queryValue == null) {
              console.log('Book, displaying book list');
              this.bookList = this.bookList;
            } else {
              this.bookList = this.bookList.filter(
                (book) => book.genre.toLowerCase() === this.queryValue
              );
            }
          } else {
            console.log('Book, searching query');
            console.log(this.queryValue);
            this.bookList = this.bookList.filter(
              (book) =>
                book.bookTitle.toLowerCase().includes(this.queryValue) ||
                book.authorName.toLowerCase().includes(this.queryValue) ||
                book.narratorName.toLowerCase().includes(this.queryValue) ||
                book.genre.toLowerCase().includes(this.queryValue)
            );
          }
          this.books = this.bookList;
        },
        (errorResponse) => {
          console.log('Custom-Book, Error response');
          console.log(errorResponse.error.message);
        }
      );
  }
}
