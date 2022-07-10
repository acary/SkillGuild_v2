import { AuthService } from 'src/app/services/auth.service';
import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';


@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  users: User[] = [];

  selected: User | null = null;

  showAllUsers: boolean = true;

  newUser: User = new User();

  createForm: boolean = false;

  createUserForm: any;

  isEditing = false;

  editUser: User | null = null;

  constructor(
    private userSvc: UserService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private auth: AuthService
  ) {
    this.createFormInit(fb);
  }

  ngOnInit(): void {
    this.getAllUsers();

  }
  getAllUsers() {
    this.userSvc.index().subscribe(users => {
      this.users = users;
    });
  }

  setEditUser = () => {
    this.editUser = Object.assign({}, this.selected);
  }

  cancelEdit = () => {
    this.editUser = null;
  }

  updateUser = (user: User) => {
  this.userSvc.update(user).subscribe(
    {
    next: () => {
      console.log("Updated user successfully: " + user.id);
      this.selected = null;
      this.editUser = null;
      this.displayAll();
      this.getAllUsers();
    },
    error: (err: any) => console.error('Error updating user: ', err)
  }
  );
}

  displayAll(): void {
    this.selected = null;
  }

  getUserById(id: number) {
    this.userSvc.show(id).subscribe(
      guild => {
        return guild;
      },
      err => {
        console.log(err);
      }
    )
  }

  deleteUser(id: number) {
    this.userSvc.delete(id).subscribe({
      next: (data) => {
        console.log("Deleted successfully: " + id);
        this.displayAll()
        this.getAllUsers()
      },
      error: (err) => console.error(err)
    });
  }

  toggleAllUsers = () => {
    this.showAllUsers = !this.showAllUsers;
    this.createForm = !this.createForm;
  }

  showCreateForm = () => {
    this.createForm = !this.createForm;
    this.showAllUsers = !this.showAllUsers;
  }

  registerUser(user: User): void {
    this.auth.register(user).subscribe({
      next: user => {
        console.log("Created successfully: " + user.id);
        this.users.push(user);
        this.newUser = new User();
        this.toggleAllUsers();
        // this.reload();
      },
      error: (err) => {
        console.error('Error creating content: ', err);
      }
    })
  }

  createFormInit(fb: FormBuilder) {
    this.createUserForm = this.fb.group({
      id: [0],
      aboutMe: [''],
      email: ['', Validators.required],
      enabled: [true],
      firstName: [''],
      lastName: [''],
      password: [''],
      profileImgUrl: ['https://picsum.photos/200'],
      role: ['standard'],
      username: ['', Validators.required],
    });
    this.createUserForm.updateValueAndValidity();
    this.isEditing = true;
  }

  sendNewUser() {
    let user: User = {
      id: this.createUserForm.get('id').value,
      aboutMe: this.createUserForm.get('aboutMe').value,
      email: this.createUserForm.get('email').value,
      enabled: this.createUserForm.get('enabled').value,
      firstName: this.createUserForm.get('firstName').value,
      lastName: this.createUserForm.get('lastName').value,
      password: this.createUserForm.get('password').value,
      profileImgUrl: this.createUserForm.get('profileImgUrl').value,
      role: this.createUserForm.get('role').value,
      username: this.createUserForm.get('username').value,
    }
    this.isEditing = false;
    console.log(user);
    this.registerUser(user);
  }

}
