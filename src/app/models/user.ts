import { UserFile } from './userFile';

export class User {
  userId!: string;
  fullName!: string;
  emailId!: string;
  contactNumber!: string;
  gender!: string;
  dateOfBirth!: Date;
  userPhotoDTO!: UserFile;
}
