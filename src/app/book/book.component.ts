import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Book } from '../models/book';
import { UserFile } from '../models/userFile';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css'],
})
export class BookComponent implements OnInit {
  bookListToDisplay: Book[] = [];
  bookList!: Book[];
  errorMessage!: string;
  successMessage!: string;
  searchText!: string;
  viewDetails: boolean = true;
  bookDetails: boolean = false;
  selectedBook!: Book;
  actualPrice!: number;
  loggedIn: boolean = false;
  emailId!: string | null;
  pageNumber: number = 0;
  bookTitles!: string[];
  displayLength = 0;
  startIndex = 0;
  endIndex = 29;
  userId!: string | null;
  userImage!: string;
  userFileDTO!: UserFile;
  showProfile: boolean = false;
  profileName!: string | null;
  query!: string | null;
  queryKey!: string;
  queryValue!: string;
  showBooks: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.getBooks();
    this.userId = sessionStorage.getItem('userId');
  }

  onNext(): void {
    this.startIndex = this.startIndex + 30;
    // this.endIndex = this.startIndex + 29;
    this.endIndex = this.endIndex + 30;
  }

  onPrevious(): void {
    this.startIndex = this.startIndex - 30;
    this.endIndex = this.endIndex - 30;
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
    this.startIndex = 0;
    this.endIndex = 29;
    this.http
      .get<Book[]>(
        'http://localhost:6200/audible/book-api/books/pageNumber/' +
          this.pageNumber +
          '/pageSize/100'
      )
      .subscribe(
        (books) => {
          this.bookList = books;
          console.log('Book, category');

          if (this.query == null) {
            this.bookListToDisplay = this.bookList;
          } else if (this.queryKey == 'genre') {
            if (this.queryValue == 'all' || this.queryValue == null) {
              console.log('Book, displaying book list');
              this.bookListToDisplay = this.bookList;
            } else {
              this.bookListToDisplay = this.bookList.filter(
                (book) => book.genre.toLowerCase() === this.queryValue
              );
            }
          } else {
            console.log('Book, searching query');
            console.log(this.queryValue);
            this.bookListToDisplay = this.bookList.filter(
              (book) =>
                book.bookTitle.toLowerCase().includes(this.queryValue) ||
                book.authorName.toLowerCase().includes(this.queryValue) ||
                book.narratorName.toLowerCase().includes(this.queryValue) ||
                book.genre.toLowerCase().includes(this.queryValue)
            );
          }
          this.showBooks = false;
        },
        (errorResponse) => {
          this.errorMessage = errorResponse.error.message;
          this.showBooks = true;
        }
      );
  }

  getBookListToDisplay(): Book[] {
    this.displayLength = this.bookListToDisplay.length - 1;
    if (this.bookListToDisplay.length >= this.endIndex + 1) {
      return this.bookListToDisplay.slice(this.startIndex, this.endIndex + 1);
    }
    return this.bookListToDisplay.slice(
      this.startIndex,
      this.bookListToDisplay.length
    );
  }

  onBookClick(book: Book) {
    this.router.navigate(['/display-book', book.bookId]);
    console.log('Book, on book clicked');
    console.log(book);
    this.viewDetails = false;
    this.bookDetails = true;
    this.selectedBook = book;
  }
}
