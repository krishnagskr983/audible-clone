import { BookFile } from './bookFile';

export class Book {
  bookId!: string;
  bookTitle!: string;
  bookOverview!: string;
  bookPrice!: string;
  authorName!: string;
  narratorName!: string;
  genre!: string;
  ratings!: string;
  description!: string;
  length!: string;
  bookPhotoDTO!: BookFile;
  bookAudioDTO!: BookFile;
}
