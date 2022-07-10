import { UserService } from './../../services/user.service';
import { AuthService } from 'src/app/services/auth.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css'],
})
export class NavigationComponent implements OnInit {
  role: string = '';

  constructor(private auth: AuthService, private userSvc: UserService) {}

  ngOnInit(): void {}

  loggedIn(): boolean {
    return this.auth.checkLogin();
  }

  isAdmin(): boolean {
    return this.auth.checkIsAdmin();
  }
}
