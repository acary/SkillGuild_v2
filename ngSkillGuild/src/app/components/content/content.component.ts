import { AuthService } from 'src/app/services/auth.service';
import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Content } from 'src/app/models/content';
import { Question } from 'src/app/models/question';
import { Status } from 'src/app/models/status';
import { Comment } from 'src/app/models/comment';
import { CommentService } from 'src/app/services/comment.service';
import { ContentService } from 'src/app/services/content.service';
import { QuestionService } from 'src/app/services/question.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';
import { GuildService } from 'src/app/services/guild.service';
import { Guild } from 'src/app/models/guild';
import { environment } from './../../../environments/environment';

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent implements OnInit {

  private url = ""; // environment.baseUrl;
  shareUrl = "";

  contents: Content[] = [];

  editContent: Content | null = null;

  selected: Content | null = null;

  showAllContents: boolean = true;

  newContent: Content = new Content();

  createForm: boolean = false;

  createContentForm: any;

  isEditing = false;

  gid: number = 0;
  cid: number = 0;
  selectedContent: Content | null = null;
  questions: Question[] = [];
  comments: Comment[] = [];
  user: User = new User();
  selectedGuild: Guild | null = null;

  constructor(
    private contentSvc: ContentService,
    private fb: FormBuilder,
    public datepipe: DatePipe,
    private route: ActivatedRoute,
    private router: Router,
    private commentSvc: CommentService,
    private questionSvc: QuestionService,
    private userSvc: UserService,
    private guildSvc: GuildService,
    private auth: AuthService
  ) {
    this.createFormInit(fb);
  }

  isAdmin(): boolean {
    return this.auth.checkIsAdmin();
  }

  ngOnInit(): void {
    this.checkRouteParams();
    this.getAllContents();
  }

  checkRouteParams() {
    this.route.queryParams.subscribe(params => {
      if (this.route.snapshot.paramMap.get('gid')) {
        this.gid = Number(this.route.snapshot.paramMap.get('gid'));
        this.getGuildById(this.gid);
        console.log("GUILD ID: " + this.gid); //
      }
      if (this.route.snapshot.paramMap.get('cid')) {
        this.cid = Number(this.route.snapshot.paramMap.get('cid'));
        this.selectContent(this.cid);
        console.log("CONTENT ID: " + this.gid); //
      }
    });

    if (this.gid && this.cid) {
      this.shareUrl = this.url + "guild/" + this.gid + "/contents/" + this.cid;
      return true;
    }
    else {
      return false;
    }
  }

  getGuildById(gid: number) {
    this.guildSvc.show(gid).subscribe(
      guild => {
        this.selectedGuild = guild;
      }
    )
  }

  getAllContents() {
    this.contentSvc.index().subscribe(contents => {
      this.contents = contents;
    });
  }

  displayAll(): void {
    this.selected = null;
  }

  getContentById(id: number) {
    this.contentSvc.show(id).subscribe(
      content => {
        return content;
      },
      err => {
        console.log(err);
      }
    )
  }

  setEditContent = () => {
    this.editContent = Object.assign({}, this.selected);
  }

  cancelEdit = () => {
    this.editContent = null;
  }

  toggleAllContents = () => {
    this.showAllContents = !this.showAllContents;
    this.createForm = !this.createForm;
  }

  showCreateForm = () => {
    this.createForm = !this.createForm;
    this.showAllContents = !this.showAllContents;
  }

  createContent(uid: number, gid: number, sid: number, content: Content): void {
    this.contentSvc.create(uid, gid, sid, content).subscribe({
      next: content => {
        console.log("Created successfully: " + content.id);
        this.contents.push(content);
        this.newContent = new Content();
        this.toggleAllContents();
        // this.reload();
      },
      error: (err) => {
        console.error('Error creating content: ', err);
      }
    })
  }

  createFormInit(fb: FormBuilder) {
    this.createContentForm = this.fb.group({
      id: [0],
      title: ['', Validators.required],
      description: ['', Validators.required],
      publishDate: ['2022-06-022 12:30:00'],
      isPublic: [''],
      isLive: [''],
      statusId: [''],
      lastUpdated: [''],
      lengthMinutes: [''],
      presentationDate: ['2022-06-022 12:30:00'],
      guildId: [''],
      createdByUser: [''],
      resources: [''],
      questions: [''],
      topics: [''],
      comments: [''],
      interactions: [''],
    });
    this.createContentForm.updateValueAndValidity();
    this.isEditing = true;
  }

  sendNewContent() {
    let content: Content = {
      id: this.createContentForm.get('id').value,
      title: this.createContentForm.get('title').value,
      description: this.createContentForm.get('description').value,
      publishDate: new Date(this.createContentForm.get('publishDate').value),
      isPublic: this.createContentForm.get('isPublic').value,
      isLive: this.createContentForm.get('isLive').value,
      statusId: new Status(this.createContentForm.get('statusId').value, 'Draft'),
      lastUpdated: '', // this.createContentForm.get('lastUpdated').value,
      lengthMinutes: 0, // this.createContentForm.get('lengthMinutes').value,
      presentationDate: new Date(this.createContentForm.get('presentationDate').value),
      guild: this.createContentForm.get('guild').value,
      createdByUser: this.createContentForm.get('createdByUser').value,
      resources: [], // this.createContentForm.get('resources').value,
      questions: [], // this.createContentForm.get('questions').value,
      topics: [], // this.createContentForm.get('topics').value,
      comments: [], // this.createContentForm.get('comments').value
      interactions: [], // this.createContentForm.get('interactions').value
    }
    console.log(content);
    this.isEditing = false;
    this.createContent(content.createdByUser.id, content.guild.id, content.statusId.id, content);
  }

  deleteContent(id: number) {
    this.contentSvc.delete(id).subscribe(
      (data) => this.getAllContents(),
      (err) => console.error(err)
    );
  }

  updateContent(content: Content) {
    this.contentSvc.update(content).subscribe(
      (data) => {
        this.getAllContents();
        this.editContent = null;
        if (this.selected) {
          this.selected = Object.assign({}, content);
        }
      },
      (err) => console.error(err)
    );
  }

  // Direct access to content
  selectContent(cid: number) {
    this.contentSvc.show(cid).subscribe(content => {
      this.selectedContent = content;
      this.getContentComments(this.selectedContent.id);
      this.getContentQuestions(this.selectedContent.id);
    });
  }

    // Questions
    getContentQuestions(cid: number) {
      this.questionSvc.showByContentId(cid).subscribe(questions => {
        this.questions = questions;
      });
    }

    // Comments
    getContentComments(cid: number) {
      this.commentSvc.showByContentId(cid).subscribe(comments => {
        this.comments = comments;
      });
    }

}
