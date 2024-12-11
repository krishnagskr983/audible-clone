import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { PostBookComponent } from './post-book/post-book.component';
import { NavbarComponent } from './navbar/navbar.component';
import { ProfileComponent } from './profile/profile.component';
import { PracticeComponent } from './practice/practice.component';
import { BookComponent } from './book/book.component';
import { DisplayBookComponent } from './display-book/display-book.component';
import { HomeComponent } from './home/home.component';
import { NotifyComponent } from './notify/notify.component';
import { CustomBookComponent } from './custom-book/custom-book.component';
import { LibraryComponent } from './library/library.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'post-book', component: PostBookComponent },
  { path: 'home', component: HomeComponent },
  { path: 'navbar', component: NavbarComponent },
  { path: 'library', component: LibraryComponent },
  { path: 'notify', component: NotifyComponent },
  { path: 'custom-book/:query', component: CustomBookComponent },
  { path: 'book', component: BookComponent },
  { path: 'display-book/:bookId', component: DisplayBookComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'practice', component: PracticeComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
