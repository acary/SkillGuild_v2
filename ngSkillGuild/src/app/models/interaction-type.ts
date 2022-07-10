export class InteractionType {
  id: number;
  name: string;
  imgUrl: string;

  constructor(
    id: number = 0,
    name: string = "",
    imgUrl: string = ""
  ) {
    this.id = id;
    this.name = name;
    this.imgUrl = imgUrl;
  }
}
