import { ProfileComponent } from './components/profile/profile.component';
import { ResourceTypeComponent } from './components/resource-type/resource-type.component';
import { ResourceComponent } from './components/resource/resource.component';
import { TopicComponent } from './components/topic/topic.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommentComponent } from './components/comment/comment.component';
import { ContentComponent } from './components/content/content.component';
import { FooterComponent } from './components/footer/footer.component';
import { GuildComponent } from './components/guild/guild.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { RegisterComponent } from './components/register/register.component';
import { UserComponent } from './components/user/user.component';
import { StatusesComponent } from './components/statuses/statuses.component';
import { CategoryComponent } from './components/category/category.component';
import { QuestionComponent } from './components/question/question.component';
import { HubComponent } from './hub/hub.component';
import { InteractionComponent } from './components/interaction/interaction.component';
import { InteractionTypeComponent } from './components/interaction-type/interaction-type.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  { path: 'home', component: HomeComponent },
  { path: 'hub', component: HubComponent },
  { path: 'user', component: UserComponent },
  { path: 'guild', component: GuildComponent },
  { path: 'guild/:gid/contents/:cid', component: ContentComponent },
  { path: 'guild/:id', component: HubComponent },
  { path: 'interaction', component: InteractionComponent },
  { path: 'interaction-type', component: InteractionTypeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'comment', component: CommentComponent },
  { path: 'footer', component: FooterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'navigation', component: NavigationComponent },
  { path: 'content', component: ContentComponent },
  { path: 'topic', component: TopicComponent },
  { path: 'statuses', component: StatusesComponent },
  { path: 'category', component: CategoryComponent },
  { path: 'question', component: QuestionComponent },
  { path: 'resource', component: ResourceComponent },
  { path: 'resource-type', component: ResourceTypeComponent },
  { path: 'profile', component: ProfileComponent },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
