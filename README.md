# SkillGuild v2

[![Generic badge](https://img.shields.io/badge/Status-MVP-Green.svg)](https://shields.io/)

### Live Demo

[View Demo](http://107.20.60.229:8080/SkillGuild/)

### Description

SkillGuild is a platform that encourages continuous learning, knowledge-sharing and career growth for professionals and teams through relevant, specific and actionable presentations to interested peers.

The application consists of an Angular (13.3) frontend with Spring Boot API and Java Persistence Application (JPA) with a MySQL database.

This version (v2) is in a separate repository to continue independently developing features, add unit testing, etc.

### Screenshots

**Home**

<img alt="skillguild-home" src="https://user-images.githubusercontent.com/1522180/171880172-442376b1-6c1a-458b-86c1-247f8f3c1dad.png">

**Browse Guilds**

<img alt="skillguild-home" src="https://user-images.githubusercontent.com/1522180/171880605-3ffe3a94-bde1-42fa-846f-da511eafea00.png">

**Guild**

<img alt="skillguild-home" src="https://user-images.githubusercontent.com/1522180/171880735-3d273c52-87ab-4716-b759-fbd0f43dad2e.png">

**Content**

<img alt="skillguild-home" src="https://user-images.githubusercontent.com/1522180/171880831-e33b3ddb-f359-4685-a8ff-3d7c514fd774.png">

### User Functionality:
- User register and login
- Create, configure and join Guilds
- Create Categories and Topics
- Create Content with multimedia links (slides, video, blog)
- Comment and interact with Content (i.e. "agree", "confused")

### API Routes

Prefix: `/v1/`

| Method   |      Route      |  Description |
|----------|:-------------:|:------:|
| POST  |    register   | Register user |
| GET  |    authenticate   | Authenticate user |
| GET  |    categories   | Gets categories |
| GET  |    categories/{cid}   | Gets category by ID |
| POST  |    categories   | Create new category |
| DELETE  |    categories/{cid}   | Delete category |
| PUT  |    categories/{cid}   | Update category |
| GET  | comments | Gets all comments |
| GET  |    comments/{id}  | Gets a specific comment |
| GET  | contents/{cid}/comments | Gets content comment |
| POST  | contents/{cid}/comments | Creates new content comment |
| POST  | comments | Creates new comment |
| PUT  | comments/{id} | Update comment |
| DELETE  | comments/{id} | Delete comment |
| GET  | contents | Gets all content |
| GET  | contents/{id} | Gets a specific content directly by id |
| POST  |    guilds/{id}/contents   | Creates new content associated with a guild |
| GET  | users/contents | Gets a specific user contents |
| GET  |    guilds/{id}/contents/{cid}   | Gets guild content by id |
| GET  |    users/{uid}/contents/{cid}   | Gets users content by id |
| POST  |    users/{uid}/guilds/{gid}/statuses/{sid}/contents   | Creates content with user, guild, status mapping |
| POST  |    guilds/{gid}/statuses/{sid}/contents   | Creates content with guild, status mapping |
| PUT |    contents/{id}   | Edits content |
| DELETE  | contents/{id} | Deletes content |
| GET  | guilds | Gets all guilds |
| GET  |    guilds/{id}   | Gets a specific guild |
| GET  |    guilds/myguilds   | Gets a list of user's guilds |
| GET  | guilds/{id}/members | Gets a guild's members |
| POST  | guilds | Creates a new guild |
| PUT  | guilds/{id} | Updates a guild |
| DELETE  | guilds/{id} | Deletes a guild |
| POST |    guilds/{id}/{uid}   | Joins a user to a guild |
| GET  |    questions   | Gets all questions |
| GET  |    questions/{id}   | Gets question by ID |
| POST  |    questions/{cid}   | Adds question to content by ID |
| DELETE  |    questions/{qid}   | Deletes question by ID |
| PUT  |    questions/{qid}   | Updates question by ID |
| GET  |    questions/{cid}/questions   | Show questions by content |
| GET  |    resources   | Gets all resources |
| GET  |    resources/{id}   | Gets resource by ID |
| POST  |    resources/{rtid}   | Creates resource with resource type ID |
| DELETE  |    resources/{rid}   | Deletes resource by ID |
| PUT  |    resources/{rid}   | Updates resource by ID |
| GET  | topics | Gets all topics |
| GET  |    topics/{id}  | Gets a specific topic |
| GET |    topics/{id}/contents  | Gets all contents associated with a specific topic |
| POST  | topics | Creates a new topic |
| GET  |    users   | Gets all users |
| POST |    users   | Creates a new user account |
| PUT  | users/{id} | Edits a users information |
| DELETE  | users/{id} | Deletes a users account |
| GET  |    users/{id}   | Gets a specific users account info |
| GET |    users/{id}/groups   | Gets all groups associated with a user |

##### Running the program:
```
- * BACKEND:
- Import project into Spring Tool Suite (STS)
- Compile and run main (as Spring Boot App)
- Note: Must run MySQL with relevant database (skillguilddb) locally
- Load DB: cd DB && mysql -u root -p < skillguilddb
- Runs on port 8090

- * FRONTEND:
- Install: cd ngSkillGuild && npm install
- Run with: ng serve -o
```

### Technologies Used

![Angular](https://img.shields.io/badge/angular-%23DD0031.svg?style=for-the-badge&logo=angular&logoColor=white)
![Bootstrap](https://img.shields.io/badge/bootstrap-%23563D7C.svg?style=for-the-badge&logo=bootstrap&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![TypeScript](https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

- Angular 13.3
- VS Code
- Spring Boot
- Java Persistence Application (JPA)
- Spring Tool Suite (STS)
- MySQL
- MySQL Workbench
- JavaSE-1.8
- Gradle

### Authors

v2:
- Andy Cary (@acary)

v1 (May to June, 2022):
- Andy Cary (@acary)
- Austin Lambert (@aslusaf)
- Alex Trill (@AlexTrill)
- Ving P (@Eagle-Fang)
