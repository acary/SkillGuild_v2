import { InteractionTypeService } from './services/interaction-type.service';
import { InteractionService } from './services/interaction.service';
import { ProfileComponent } from './components/profile/profile.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NavigationComponent } from './components/navigation/navigation.component';
import { HomeComponent } from './components/home/home.component';
import { GuildComponent } from './components/guild/guild.component';
import { UserComponent } from './components/user/user.component';
import { UserService } from './services/user.service';
import { GuildService } from './services/guild.service';
import { ContentComponent } from './components/content/content.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { FooterComponent } from './components/footer/footer.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { CommentComponent } from './components/comment/comment.component';
import { AuthService } from './services/auth.service';
import { TopicComponent } from './components/topic/topic.component';
import { LoginRegistrationModalComponent } from './components/login-registration-modal/login-registration-modal.component';
import { RouterModule } from '@angular/router';
import { StatusesComponent } from './components/statuses/statuses.component';
import { CategoryService } from './services/category.service';
import { CategoryComponent } from './components/category/category.component';
import { QuestionComponent } from './components/question/question.component';
import { ResourceComponent } from './components/resource/resource.component';
import { ResourceTypeComponent } from './components/resource-type/resource-type.component';
import { HubComponent } from './hub/hub.component';
import { HubService } from './services/hub.service';
import { ResourceService } from './services/resource.service';
import { ResourceTypeService } from './services/resource-type.service';
import { ProfileAccordianComponent } from './components/profile-accordian/profile-accordian.component';
import { EditProfileModalComponent } from './components/edit-profile-modal/edit-profile-modal.component';
import { CreateGuildModalComponent } from './components/create-guild-modal/create-guild-modal.component';
import { InteractionTypeComponent } from './components/interaction-type/interaction-type.component';
import { InteractionComponent } from './components/interaction/interaction.component';
import { CommentModalComponent } from './components/comment-modal/comment-modal.component';
import { CreateContentModalComponent } from './components/create-content-modal/create-content-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    HomeComponent,
    GuildComponent,
    UserComponent,
    ContentComponent,
    FooterComponent,
    LoginComponent,
    LogoutComponent,
    NotFoundComponent,
    CommentComponent,
    TopicComponent,
    LoginRegistrationModalComponent,
    StatusesComponent,
    CategoryComponent,
    QuestionComponent,
    ResourceComponent,
    ResourceTypeComponent,
    HubComponent,
    ProfileComponent,
    ProfileAccordianComponent,
    EditProfileModalComponent,
    CreateGuildModalComponent,
    InteractionTypeComponent,
    InteractionComponent,
    CommentModalComponent,
    CreateContentModalComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgbModule,
    RouterModule
  ],
  providers: [
    AuthService,
    UserService,
    GuildService,
    CategoryService,
    HubService,
    ResourceService,
    ResourceTypeService,
    InteractionService,
    InteractionTypeService,
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
