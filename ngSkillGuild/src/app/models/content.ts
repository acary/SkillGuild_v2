import { Guild } from "./guild";
import { Interaction } from "./interaction";
import { Question } from "./question";
import { Resource } from "./resource";
import { Status } from "./status";
import { Topic } from "./topic";
import { User } from "./user";

export class Content {
    id: number;
    title: string;
    description: string;
    publishDate: Date;
    isPublic: boolean;
    isLive: boolean;
    statusId: Status;
    lastUpdated: string;
    lengthMinutes: number;
    presentationDate: Date;
    guild: Guild;
    createdByUser: User;
    resources: Resource[] = [];
    questions: Question[] = [];
    topics: Topic[] = [];
    comments: Comment[] = [];
    interactions: Interaction[] = [];

    constructor(
        id: number = 0,
        title: string = '',
        description: string = '',
        publishDate: Date = new Date(),
        isPublic: boolean = false,
        isLive: boolean = false,
        statusId: Status = new Status(),
        lastUpdated: string = '',
        lengthMinutes: number = 0,
        presentationDate: Date = new Date(),
        guild: Guild = new Guild(),
        createdByUser: User = new User(),
        resources: Resource[] = [],
        questions: Question[] = [],
        topics: Topic[] = [],
        comments: Comment[] = [],
        interactions: Interaction[] = [],
    ) {
        this.id = id;
        this.guild = guild;
        this.title = title;
        this.description = description;
        this.publishDate = publishDate;
        this.isPublic = isPublic;
        this.isLive = isLive;
        this.statusId = statusId;
        this.lastUpdated = lastUpdated;
        this.createdByUser = createdByUser;
        this.lengthMinutes = lengthMinutes;
        this.presentationDate = presentationDate;
        this.resources = resources;
        this.questions = questions;
        this.topics = topics;
        this.comments = comments;
        this.interactions = interactions;
    }
}
