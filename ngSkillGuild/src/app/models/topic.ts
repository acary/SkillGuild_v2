export class Topic {
  id: number;
  name: string;
  description: string;
  isTech: boolean;

  constructor(
    id: number = 0,
    name: string = "",
    description: string = "",
    isTech: boolean
  ) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.isTech = isTech;
  }
}
