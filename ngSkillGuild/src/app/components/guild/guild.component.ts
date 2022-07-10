import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Guild } from 'src/app/models/guild';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/models/user';
import { GuildService } from 'src/app/services/guild.service';

@Component({
  selector: 'app-guild',
  templateUrl: './guild.component.html',
  styleUrls: ['./guild.component.css']
})
export class GuildComponent implements OnInit {

  guilds: Guild[] = [];

  selected: Guild | null = null;

  showAllGuilds: boolean = true;

  newGuild: Guild = new Guild();

  createForm: boolean = false;

  createGuildForm: any;

  isEditing = false;

  editGuild: Guild | null = null;

  constructor(
    private guildSvc: GuildService,
    private fb: FormBuilder,
    private auth: AuthService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.createFormInit(fb);
  }

  ngOnInit(): void {
    this.getAllGuilds();
  }

  isAdmin(): boolean {
    return this.auth.checkIsAdmin();
  }

  getAllGuilds() {
    this.guildSvc.index().subscribe(guilds => {
      this.guilds = guilds;
    });
  }

  displayAll(): void {
    this.selected = null;
  }

  getGuildById(id: number) {
    this.guildSvc.show(id).subscribe(
      guild => {
        return guild;
      },
      err => {
        console.log(err);
      }
    )
  }

  toggleAllGuilds = () => {
    this.showAllGuilds = !this.showAllGuilds;
    this.createForm = !this.createForm;
  }

  showCreateForm = () => {
    this.createForm = !this.createForm;
    this.showAllGuilds = !this.showAllGuilds;
  }

  createGuild(createdByUserId: number, guild: Guild): void {
    this.guildSvc.create(createdByUserId, guild).subscribe({
      next: guild => {
        console.log("Created successfully: " + guild.id);
        this.guilds.push(guild);
        this.newGuild = new Guild();
        this.toggleAllGuilds();
      },
      error: (err) => {
        console.error('Error creating guild: ', err);
      }
    })
  }

  deleteGuild(id: number) {
    this.guildSvc.delete(id).subscribe({
      next: (data) => {
        console.log("Deleted successfully: " + id);
        this.displayAll()
        this.getAllGuilds()
      },
      error: (err) => console.error(err)
    });
  }

  createFormInit(fb: FormBuilder) {
    this.createGuildForm = this.fb.group({
      id: [0],
      name: ['', Validators.required],
      description: ['', Validators.required],
      isPublic: [''],
      createdByUser: [''],
      membershipCriteria: [''],
      coverImg: ['']
    });
    this.createGuildForm.updateValueAndValidity();
    this.isEditing = true;
  }

  sendNewGuild() {
    let guild: Guild = {
      id: this.createGuildForm.get('id').value,
      name: this.createGuildForm.get('name').value,
      description: this.createGuildForm.get('description').value,
      isPublic: this.createGuildForm.get('isPublic').value,
      membershipCriteria: this.createGuildForm.get('membershipCriteria').value,
      createdByUser: new User(),//this.createGuildForm.get('createdByUser').value,
      coverImg: '',
      createdOn: '',
      lastUpdated: '',
    }
    this.isEditing = false;
    this.createGuild(guild.createdByUser.id, guild);
  }

  setEditGuild = () => {
    this.editGuild = Object.assign({}, this.selected);
  }

  cancelEdit = () => {
    this.editGuild = null;
  }

  updateGuild = (guild: Guild) => {
    this.guildSvc.update(guild).subscribe(
      {


        next: () => {
          console.log("Updated guild successfully: " + guild.id);
          this.selected = null;
          this.editGuild = null;
          this.displayAll();
          this.getAllGuilds();
        },
        error: (err: any) => console.error('Error updating guild: ', err)
      }
    );
  }


}
