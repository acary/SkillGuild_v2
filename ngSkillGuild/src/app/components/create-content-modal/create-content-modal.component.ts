import { Router } from '@angular/router';
import { ContentService } from 'src/app/services/content.service';
import { Content } from './../../models/content';
import { Component, Input, OnInit } from '@angular/core';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-create-content-modal',
  templateUrl: './create-content-modal.component.html',
  styleUrls: ['./create-content-modal.component.css'],
})
export class CreateContentModalComponent implements OnInit {

  @Input() gid: number = 0;

  content: Content = new Content();

  closeResult = '';

  constructor(
    private modalService: NgbModal,
    private contentService: ContentService,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.content.lengthMinutes = 30;
  }

  toggleIsPublic() {
    this.content.isPublic = !this.content.isPublic;
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

  openContentModal(content: any) {
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
        windowClass: 'dark-modal',
        size: 'xl',
      })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
          this.createNewContent(this.content);
        },
        (reason) => {
          this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        }
      );
  }

  createNewContent = (content: Content) => {
    content.presentationDate = new Date(this.content.presentationDate);

    this.contentService.createNewContent(this.gid, 1, content).subscribe({
      next: (newContent) => {
        this.router.navigateByUrl(
          '/guild/' + this.gid + '/contents/' + newContent.id
        );
      },
      error: (err: any) => console.error('Error updating user: ', err),
    });
  };
}
