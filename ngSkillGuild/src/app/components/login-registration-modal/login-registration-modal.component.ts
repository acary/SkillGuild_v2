import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login-registration-modal',
  templateUrl: './login-registration-modal.component.html',
  styleUrls: ['./login-registration-modal.component.css'],
})
export class LoginRegistrationModalComponent implements OnInit {

  @ViewChild('openLog')
  openLog!: ElementRef;

  @ViewChild('openReg')
  openReg!: ElementRef;

  loginUser: User = new User();

  newUser = new User();

  closeResult = '';

  errorMessage: string = '';

  constructor(
    private modalService: NgbModal,
    private auth: AuthService,
    private router: Router,
  ) { }

  ngOnInit(): void { }

  openLogin(content: any) {
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
        centered: true,
        windowClass: 'dark-modal',
      })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
          this.login(this.loginUser);
        },
        (reason) => {
          this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        }
      );
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

  login(user: User) {
    this.auth.login(user.username, user.password).subscribe({
      next: (loggedInUser) => {
        this.router.navigateByUrl('/profile');
      },
      error: (fail) => {
        this.errorMessage = 'Login failed. Please try again.';
        console.error(this.errorMessage);
        this.openError(this.errorMessage);
      },
    });
  }

  openError(content: any) {
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
        centered: true,
        windowClass: 'dark-modal',
      })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
        },
        (reason) => {
          this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        }
      );
  }

  registerUser(newUser: User): void {
    this.auth.register(newUser).subscribe({
      next: (registeredUser) => {
        console.log('Created successfully');
        this.auth.login(newUser.username, newUser.password).subscribe({
          next: (loggedInUser) => {
            this.router.navigateByUrl('/profile');
          },
          error: (fail) => {
            console.error('LoginComponent.login(); login failed');
            console.error(fail);
          },
        });
      },
      error: (err) => {
        console.error('Error creating content: ', err);
      },
    });
  }

  openRegistration(content: any) {
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
        centered: true,
        windowClass: 'dark-modal',
      })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
          this.registerUser(this.newUser);
        },
        (reason) => {
          this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        }
      );
  }

  openRegFunc() {
    this.openReg.nativeElement.click();
  }

  openLogFunc() {
    this.openReg.nativeElement.click();
  }
}
