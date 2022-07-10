import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';
import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: User = new User();

  constructor(private userSvc: UserService) { }

  ngOnInit(): void {
    this.getUserProfile();
  }

  getUserProfile() {

    this.userSvc.getProfile().subscribe(
      user => {
        this.user = user;
      },
      err => {
        console.log(err);
      }
    )
  }

  editProfile() {
    console.log("Editing profile...");
  }


}
