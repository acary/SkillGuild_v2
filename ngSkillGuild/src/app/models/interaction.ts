import { User } from "./user";

export class Interaction {
  id: number;
  createdOn: string;
  contentId: number;
  userId: number;
  interactionTypeId: number;


  constructor(
    id: number = 0,
    createdOn: string = "",
    contentId: number = 0,
    userId: number = 0,
    interactionTypeId = 0
  ) {
    this.id = id;
    this.createdOn = createdOn;
    this.contentId = contentId;
    this.userId = userId;
    this.interactionTypeId = interactionTypeId;
  }

}
