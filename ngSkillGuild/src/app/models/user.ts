export class User {

  id: number;
  username: string;
  email: string;
  password: string;
  firstName: string | null;
  lastName: string  | null;
  enabled: boolean;
  role: string;
  profileImgUrl: string | null;
  aboutMe: string | null;

  constructor(
    id: number = 0,
    username: string = "",
    email: string = "",
    password: string = "",
    firstName: string = "",
    lastName: string = "",
    enabled: boolean = false,
    role: string = "",
    profileImgUrl: string = "",
    aboutMe: string = ""
  ) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.enabled = enabled;
    this.role = role;
    this.profileImgUrl = profileImgUrl;
    this.aboutMe = aboutMe;
  }

}
