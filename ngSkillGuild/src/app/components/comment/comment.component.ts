import { Comment } from './../../models/comment';
import { CommentService } from './../../services/comment.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {

  comments: Comment[] = [];

  editComment: Comment | null = null;

  selected: Comment | null = null;

  newComment: Comment = new Comment();

  createForm: boolean = false;

  createCommentForm: any;

  isEditing = false;

  showAllComments: boolean = true;

  constructor(
    private commentSvc: CommentService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.createFormInit(fb);
  }

  ngOnInit(): void {
    this.getAllComments();
  }

  getAllComments() {
    this.commentSvc.index().subscribe(comments => {
      this.comments = comments;
      console.log(comments);
    });
  }

  getCommentById(id: number) {
    this.commentSvc.show(id).subscribe(
      comment => {
        return comment;
      },
      err => {
        console.log(err);
      }
    )
  }

  // createComment(uid: number, cid: number, comment: Comment): void {
  //   this.commentSvc.create(uid, cid, comment).subscribe({
  //     next: comment => {
  //       console.log("Created successfully: " + comment.id);
  //       this.comments.push(comment);
  //       this.newComment = new Comment();
  //       this.toggleAllComments();
  //       this.getAllComments();
  //     },
  //     error: (err) => {
  //       console.error('Error creating content: ', err);
  //     }
  //   })
  // }

  updateComment(comment: Comment) {
    this.commentSvc.update(comment).subscribe(
      (data) => {
        this.getAllComments();
        this.editComment = null;
        if (this.selected) {
          this.selected = Object.assign({}, comment);
        }
      },
      (err) => console.error(err)
    );
  }

  deleteComment(id: number) {
    this.commentSvc.delete(id).subscribe(
      (data) => this.getAllComments(),
      (err) => console.error(err)
    );
  }

  setEditComment = () => {
    this.editComment = Object.assign({}, this.selected);
  }

  cancelEdit = () => {
    this.editComment = null;
  }

  displayAll(): void {
    this.selected = null;
  }

  toggleAllComments = () => {
    this.showAllComments = !this.showAllComments;
    this.createForm = !this.createForm;
  }

  showCreateForm = () => {
    this.createForm = !this.createForm;
    this.showAllComments = !this.showAllComments;
  }

  createFormInit(fb: FormBuilder) {
    this.createCommentForm = this.fb.group({
      id: [0],
      textContent: [''],
      inReplyTo: [''],
      hasBeenEdited: [false],
      createdOn: ['2022-06-022 12:30:00'],
      createdByUser: [''],
      contentId: ['']
    });
    this.createCommentForm.updateValueAndValidity();
    this.isEditing = true;
  }

  sendNewComment() {
    let comment: Comment = {
      id: this.createCommentForm.get('id').value,
      textContent: this.createCommentForm.get('textContent').value,
      inReplyTo: this.createCommentForm.get('inReplyTo').value,
      hasBeenEdited: this.createCommentForm.get('hasBeenEdited').value,
      createdOn: new Date(this.createCommentForm.get('createdOn').value),
      createdByUser: this.createCommentForm.get('createdByUser').value,
      contentId: this.createCommentForm.get('contentId').value
    }
    this.isEditing = false;
    console.log(comment);
    // this.createComment(1, 1, comment);
  }

}
