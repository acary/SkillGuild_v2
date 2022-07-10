import { Router } from '@angular/router';
import { Guild } from './../../models/guild';
import { GuildService } from 'src/app/services/guild.service';
import { Component, OnInit } from '@angular/core';
import { NgbCollapse } from '@ng-bootstrap/ng-bootstrap';
import { Content } from 'src/app/models/content';
import { ContentService } from 'src/app/services/content.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-profile-accordian',
  templateUrl: './profile-accordian.component.html',
  styleUrls: ['./profile-accordian.component.css'],
})

export class ProfileAccordianComponent implements OnInit {
  myGuilds: Guild[] = [];
  paginationGuilds: Guild[] = [];
  myContents: Content[] = [];
  paginationContents: Content[] = [];

  key: number = 0;
  page: number = 1;
  pageSize: number = 5;

  keyC: number = 0;
  pageC: number = 1;
  pageSizeC: number = 5;

  memberOfGuild = false;
  contents: Content[] = [];
  selectedGuild: Guild | null = null;
  user: User = new User();

  constructor(
    private guildSvc: GuildService,
    private contentSvc: ContentService,
    private userSvc: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.memberOfGuilds();
    this.getUserContent();
  }

  memberOfGuilds() {
    this.guildSvc.memberOfGuilds().subscribe((guilds) => {
      this.myGuilds = guilds;
      this.refreshGuilds();
    });
  }

  getUserContent() {
    this.contentSvc.showContentByUser().subscribe((contents) => {
      this.myContents = contents;
      this.refreshContents();
    });
  }

  refreshGuilds() {
    this.paginationGuilds = this.myGuilds
      .map((guild, i) => ({ key: i + 1, ...guild }))
      .slice(
        (this.page - 1) * this.pageSize,
        (this.page - 1) * this.pageSize + this.pageSize
      );
  }

  refreshContents() {
    this.paginationContents = this.myContents
      .map((content, i) => ({ keyC: i + 1, ...content }))
      .slice(
        (this.pageC - 1) * this.pageSizeC,
        (this.pageC - 1) * this.pageSizeC + this.pageSizeC
      );
  }

  goToContent(gid: number, cid: number) {
    this.router.navigateByUrl('/guild/' + gid + '/contents/' + cid);
  }

  goToGuild(gid: number) {
    this.router.navigateByUrl('/guild/' + gid);
  }

  selectGuild(guild: Guild) {
    this.memberOfGuild = false;
    this.contents = [];
    this.selectedGuild = guild;
    this.getGuildContents(guild.id);
    this.getUserProfile();
    this.checkMembership(guild);
  }

  getGuildContents(gid: number) {
    this.contentSvc.showContentByGuild(gid).subscribe(contents => {
      this.contents = contents;
    });
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
}
