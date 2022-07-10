import { User } from "./user";

export class Comment {
  id: number;
  inReplyTo: number;
  textContent: string;
  hasBeenEdited: boolean;
  createdOn: Date;
  createdByUser: User;
  contentId: number;

  constructor(
    id: number = 0,
    inReplyTo: number = 0,
    textContent: string = "",
    hasBeenEdited: boolean = false,
    createdOn: Date = new Date(),
    createdByUser: User = new User(),
    contentId: number = 1
  ) {
    this.id = id;
    this.inReplyTo = inReplyTo;
    this.textContent = textContent;
    this.hasBeenEdited = hasBeenEdited;
    this.createdOn = createdOn;
    this.createdByUser = createdByUser;
    this.contentId = contentId;
  }
}
