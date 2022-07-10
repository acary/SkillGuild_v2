export class Category {
  id: number;
  name: string;
  description: string;
  imgUrl: string;

  constructor(
    id: number = 0,
    name: string = "",
    description: string = "",
    imgUrl: string = ""
  ) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.imgUrl = imgUrl;
  }
}
