import { ResourceType } from "./resource-type";

export class Resource {
  id: number;
  title: string;
  description: string;
  resourceUrl: string;
  resourceType: ResourceType | null;

  constructor(
    id: number = 0,
    title: string = "",
    description: string = "",
    resourceUrl: string = "",
    resourceType: ResourceType | null = null

  ) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.resourceUrl = resourceUrl;
    this.resourceType = resourceType
  }

}
