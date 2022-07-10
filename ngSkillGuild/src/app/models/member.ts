import { Guild } from "./guild";
import { User } from "./user";

export class Member {
  guildId: Guild;
  userId: User;
  approvedBy: number;
  moderator: boolean;
  createdOn: string;

  constructor(
    guildId: Guild,
    userId: User,
    approvedBy: number = 0,
    moderator: boolean = false,
    createdOn: string = ""
  ) {
    this.guildId = guildId;
    this.userId = userId;
    this.approvedBy = approvedBy;
    this.moderator = moderator;
    this.createdOn = createdOn;
  }
}
