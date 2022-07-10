import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { GuildService } from 'src/app/services/guild.service';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Guild } from 'src/app/models/guild';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-create-guild-modal',
  templateUrl: './create-guild-modal.component.html',
  styleUrls: ['./create-guild-modal.component.css'],
})
export class CreateGuildModalComponent implements OnInit {
  @ViewChild('openLog')
  openLog!: ElementRef;

  @ViewChild('openReg')
  openReg!: ElementRef;

  loginUser: User = new User();

  guild: Guild = new Guild();

  newUser = new User();

  closeResult = '';

  constructor(
    private modalService: NgbModal,
    private guildSvc: GuildService,
    private router: Router,
    private auth: AuthService
  ) {}

  ngOnInit(): void {}

  toggleIsPublic() {
    this.guild.isPublic = !this.guild.isPublic;
  }

  loggedIn(): boolean {
    return this.auth.checkLogin();
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

  openCreateGuild(content: any) {
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
        windowClass: 'dark-modal',
        size: 'xl',
      })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
          this.createNewGuild(this.guild);
          this.router.navigateByUrl('guild');
          setTimeout(() => {
            window.location.reload();
          }, 100);
        },
        (reason) => {
          this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        }
      );
  }

  createNewGuild = (guild: Guild) => {
    this.guildSvc.createNewGuild(guild).subscribe({
      next: () => {
        console.log('Created Guild successfully');
      },
      error: (err: any) => console.error('Error updating user: ', err),
    });
  };

  openRegistration(content: any) {
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
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

  openLogin(content: any) {
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
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

  login(user: User) {
    this.auth.login(user.username, user.password).subscribe({
      next: (loggedInUser) => {
        this.router.navigateByUrl('/profile');
      },
      error: (fail) => {
        console.error('LoginComponent.login(); login failed');
        console.error(fail);
      },
    });

  }

  openRegFunc() {
    this.openReg.nativeElement.click();
  }

  openLogFunc() {
    this.openReg.nativeElement.click();
  }
}
