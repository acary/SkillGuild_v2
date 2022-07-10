import { CommentService } from './../../services/comment.service';
import { Content } from './../../models/content';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from 'src/app/services/auth.service';
import { Comment } from 'src/app/models/comment';

@Component({
  selector: 'app-comment-modal',
  templateUrl: './comment-modal.component.html',
  styleUrls: ['./comment-modal.component.css'],
})
export class CommentModalComponent implements OnInit {
  @Input() selectedContentId: number = 0;
  @Input() shareUrl: string = '';

  comment: Comment = new Comment();

  closeResult = '';

  constructor(
    private modalService: NgbModal,
    private commentSvc: CommentService,
    private router: Router,
    private auth: AuthService
  ) {}

  ngOnInit(): void {}

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  openCommentModal(content: any) {
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
        windowClass: 'dark-modal',
      })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
          this.createNewComment(this.selectedContentId, this.comment);
          setTimeout(() => {
            window.location.reload();
          }, 10);
          // this.goToContent(this.shareUrl);
        },
        (reason) => {
          this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        }
      );
  }

  createNewComment = (contentId: number, comment: Comment) => {
    this.commentSvc.create(contentId, comment).subscribe({
      next: () => {
        console.log('Created Comment successfully');
      },
      error: (err: any) => console.error('Error updating user: ', err),
    });
  };

  // goToContent(shareUrl: string) {
  //   // console.log(shareUrl);
  //   if (shareUrl === '') {
  //   } else {
  //     this.router.navigateByUrl(shareUrl);
  //   }
  //   setTimeout(() => {
  //     window.location.reload();
  //   }, 10);
  // }
}
