import { User } from 'src/app/models/user';
import { Component, OnInit } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-edit-profile-modal',
  templateUrl: './edit-profile-modal.component.html',
  styleUrls: ['./edit-profile-modal.component.css']
})
export class EditProfileModalComponent implements OnInit {

  user: User = new User();

  closeResult = '';

  constructor(
    private modalService: NgbModal,
    private userSvc: UserService
  ) { }

  ngOnInit(): void {
    this.getUserProfile();
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  openEditProfile(content: any) {
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
        windowClass: 'dark-modal',
        size: 'xl'
      })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
          this.updateUser(this.user);
        },
        (reason) => {
          this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        }
      );
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

  updateUser = (user: User) => {
    this.userSvc.editProfile(user).subscribe(
      {
      next: () => {
        window.location.reload();
        console.log("Updated user successfully: " + user.id);
      },
      error: (err: any) => console.error('Error updating user: ', err)
    }
    );
  }

}
