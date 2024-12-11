import { HttpClient } from '@angular/common/http';
import { Component, HostListener, Injectable, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserFile } from '../models/userFile';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
@Injectable({
  providedIn: 'root',
})
export class NavbarComponent implements OnInit {
  searchText!: string;
  actualPrice!: number;
  userId!: string | null;
  userImage!: string;
  userFileDTO!: UserFile;
  showProfile: boolean = false;
  profileName!: string | null;

  constructor(private router: Router, private http: HttpClient) {}

  ngOnInit(): void {
    this.profileName = sessionStorage.getItem('userName');
    if (this.profileName != null) {
      this.profileName = this.profileName.split(' ')[0];
    }
    console.log('Home, this profile name ');
    console.log(this.profileName);
    this.getProfilePhoto();
  }

  getProfilePhoto() {
    const userId = sessionStorage.getItem('userId');
    this.http
      .get(
        'http://localhost:6100/audible/user-api/user/get-profile-photo/' +
          userId
      )
      .subscribe(
        (data: any) => {
          console.log('Home, Get profile photo data is ==>');
          console.log(data);
          this.userFileDTO = data;
          this.userImage = this.userFileDTO.filePath;
        },
        (error) => {
          console.log('Home, display get error ==> ');
          console.log(error);
        }
      );
  }

  categoryBooks(categorySelect: { value: any }) {
    console.log('Navbar, before calling Book');
    this.browseBooks('category-' + categorySelect.value.toLowerCase());
  }

  searchBooks(searchText: string) {
    this.browseBooks('search-' + searchText.toLowerCase());
  }

  browseBooks(query: string) {
    if (query != null) {
      this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
        // this.router.navigate(['book', query]);
        this.router.navigate(['custom-book', query]);
      });
    }
  }

  onClickHome() {
    this.router.navigate(['/home']);
  }

  onClickLibrary() {
    this.router.navigate(['/library']);
  }

  onClickNotify() {
    this.router.navigate(['/notify']);
  }

  onClickProfile() {
    this.router.navigate(['/profile']);
  }

  logOut() {
    sessionStorage.clear();
    this.router.navigate(['/login']);
  }

  displayProfile() {
    this.showProfile = !this.showProfile;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent): void {
    // console.log('Click event:', event);

    // Check if the click is outside the profile card
    const isClickInsideProfileCard = (event.target as HTMLElement).closest(
      '.profile-box'
    );
    // console.log('Is click inside profile card:', isClickInsideProfileCard);

    if (!isClickInsideProfileCard) {
      // Close the profile card if the click is outside
      this.showProfile = false;
    }
  }
}
