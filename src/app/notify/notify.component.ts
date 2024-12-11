import { Component, OnInit } from '@angular/core';
import { Book } from '../models/book';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Notify } from '../models/notify';

@Component({
  selector: 'app-notify',
  templateUrl: './notify.component.html',
  styleUrls: ['./notify.component.css'],
})
export class NotifyComponent implements OnInit {
  notifyList!: Notify[];
  notifyBookList: Book[] = [];
  userId: string | null | undefined;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.userId = sessionStorage.getItem('userId');
    this.getNotifyBooks();
  }

  getNotifyBooks() {
    // this.query = this.route.snapshot.paramMap.get('query');

    this.http
      .get<Notify[]>(
        'http://localhost:6400/audible/notification-api/notification/books/user/655b3e1ed236fc2982f8a01e'
      )
      .subscribe(
        (notifyData) => {
          this.notifyList = notifyData;
          console.log('Notify, notifyData get books');
          console.log(this.notifyList);
          for (const notify of this.notifyList) {
            console.log('Notify, push notify books');
            console.log(notify);
            if (notify && notify.bookDTO) {
              console.log('Notify, before push');
              this.notifyBookList.push(notify.bookDTO);
              console.log('Notify, after push');
            }
          }
        },
        (errorResponse) => {
          console.log('CustomBook, error getAllNotifyBooks');
          console.log(errorResponse.error.message);
        }
      );
  }
}
