import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { NavbarComponent } from './navbar/navbar.component';
import { PostBookComponent } from './post-book/post-book.component';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProfileComponent } from './profile/profile.component';
import { PracticeComponent } from './practice/practice.component';
import { BookComponent } from './book/book.component';
import { DisplayBookComponent } from './display-book/display-book.component';
import { HomeComponent } from './home/home.component';
import { NotifyComponent } from './notify/notify.component';
import { CustomBookComponent } from './custom-book/custom-book.component';
import { LibraryComponent } from './library/library.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    NavbarComponent,
    HomeComponent,
    PostBookComponent,
    ProfileComponent,
    PracticeComponent,
    BookComponent,
    DisplayBookComponent,
    NotifyComponent,
    CustomBookComponent,
    LibraryComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
