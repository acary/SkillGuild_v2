import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Content } from '../models/content';
import { Comment } from '../models/comment';
import { Guild } from '../models/guild';
import { User } from '../models/user';
import { CommentService } from '../services/comment.service';
import { ContentService } from '../services/content.service';
import { GuildService } from '../services/guild.service';
import { UserService } from '../services/user.service';
import { Question } from '../models/question';
import { QuestionService } from '../services/question.service';
import { environment } from './../../environments/environment';

@Component({
  selector: 'app-hub',
  templateUrl: './hub.component.html',
  styleUrls: ['./hub.component.css']
})
export class HubComponent implements OnInit {

  private url = ""; // environment.baseUrl;
  shareUrl = "";

  guilds: Guild[] = [];
  selectedGuild: Guild | null = null;
  gid: number = 0;

  contents: Content[] = [];
  selectedContent: Content | null = null;

  questions: Question[] = [];

  // defaultImage: string = 'https://images.unsplash.com/3/doctype-hi-res.jpg';
  defaultImage: string = '/assets/images/doctype-hi-res_3.jpg';

  user: User = new User();
  myGuilds: Guild[] = [];
  memberOfGuild = false;

  comments: Comment[] = [];
  guildImgArr: string[] = ['assets/images/guild_1.jpeg','assets/images/guild_2.avif', 'assets/images/guild_3.avif',
  'assets/images/guild_4.avif', 'assets/images/guild_5.jpeg', 'assets/images/guild_6.webp'];

  constructor(
    private guildSvc: GuildService,
    private contentSvc: ContentService,
    private userSvc: UserService,
    private commentSvc: CommentService,
    private questionSvc: QuestionService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.getAllGuilds();
    this.getMyGuilds();
    this.checkRouteParams();
    // console.log("ngInit");
  }

  checkRouteParams() {
    this.route.queryParams.subscribe(params => {
      if (this.route.snapshot.paramMap.get('id')) {
        this.gid = Number(this.route.snapshot.paramMap.get('id'));
        console.log("GUILD ID: " + this.gid);
      }
    });

    if (this.gid) {
      this.shareUrl = this.url + "guild/" + this.gid + "/contents/" + this.selectedContent?.id;
      this.getGuildById(this.gid);
      console.log(this.selectedGuild);
      this.getGuildContents(this.gid);
      this.getUserProfile();
      // this.checkMembership(this.selectedGuild);
      console.log(this.selectedGuild);
      console.log("END");
    }
  }

  // Guilds
  getAllGuilds() {
    this.guildSvc.index().subscribe(guilds => {
      this.guilds = guilds;
    });
  }

  getGuildById(id: number) {
    this.guildSvc.show(id).subscribe(
      guild => {
        this.selectedGuild = guild;
      }
    )
  }

  getMyGuilds() {
    this.guildSvc.memberOfGuilds().subscribe((data) => {
      this.myGuilds = data;
    });
  }

  displayAllGuilds(): void {
    this.selectedGuild = null;
  }

  selectGuild(guild: Guild) {
    this.memberOfGuild = false;
    this.contents = [];
    this.getGuildContents(guild.id);
    this.selectedGuild = guild;
    this.getUserProfile();
    this.checkMembership(guild);
  }

  displayGuild() {
    this.selectedContent = null;
  }

  joinGuild(gid: number, uid: number) {
    if (this.selectedGuild) {
      console.log("User " + uid + " wants to join: " + gid);
      this.guildSvc.join(uid, gid).subscribe({
        next: data => {
          console.log("Joined successfully: " + data);
        },
        error: (err) => {
          console.error('Error joining guild: ', err);
        }
      })
    }
  }

  checkMembership(guild: Guild) {
    this.myGuilds.map(current => {
      if (current.id === guild.id) {
        this.memberOfGuild = true;
        return true;
      }
      return false;
    });
  }

  // Content
  getGuildContents(gid: number) {
    this.contentSvc.showContentByGuild(gid).subscribe(contents => {
      this.contents = contents;
    });
  }

  getAllContents() {
    this.contentSvc.index().subscribe(contents => {
      this.contents = contents;
    });
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

  selectContent(cid: number) {
    this.contentSvc.show(cid).subscribe(content => {
      this.router.navigateByUrl("/guild/" + this.selectedGuild?.id + "/contents/" + cid);
    });
  }

  // User
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

  checkLogin(): boolean {
    if (localStorage.getItem('credentials')) {
      return true;
    }
    return false;
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
