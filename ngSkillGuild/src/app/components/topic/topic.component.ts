import { TopicService } from './../../services/topic.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Topic } from 'src/app/models/topic';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.css']
})
export class TopicComponent implements OnInit {

  topics: Topic[] = [];

  newTopic: Topic = new Topic(0, "", "", true);

  selected: Topic | null = null;

  showAllTopics: boolean = true;

  createForm: boolean = false;

  createTopicForm: any;

  isEditing = false;

  editTopic: Topic | null = null;

  constructor(
    private topicSvc: TopicService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.createFormInit(fb);
  }

  ngOnInit(): void {
    this.getAllTopics();
  }
  getAllTopics() {
    this.topicSvc.index().subscribe(topics => {
      this.topics = topics;
    });
  }

  createTopic(topic: Topic): void {
    this.topicSvc.create(topic).subscribe({
      next: topic => {
        console.log("Created successfully: " + topic.id);
        this.topics.push(topic);
        this.newTopic = new Topic(0, "", "", true);
        this.toggleAllTopics();
        this.getAllTopics();
      },
      error: (err) => {
        console.error('Error creating topic: ', err);
      }
    })
  }

  setEditTopic = () => {
    this.editTopic = Object.assign({}, this.selected);
  }

  cancelEdit = () => {
    this.editTopic = null;
  }

  updateTopic = (topic: Topic) => {
    this.topicSvc.update(topic).subscribe(
      {
        next: () => {
          console.log("Updated topic successfully: " + topic.id);
          this.selected = null;
          this.editTopic = null;
          this.displayAll();
          this.getAllTopics();
        },
        error: (err: any) => console.error('Error updating topic: ', err)
      }
    );
  }

  displayAll(): void {
    this.selected = null;
  }

  getTopicById(id: number) {
    this.topicSvc.show(id).subscribe(
      topic => {
        return topic;
      },
      err => {
        console.log(err);
      }
    )
  }

  deleteTopic(id: number) {
    this.topicSvc.delete(id).subscribe({
      next: (data) => {
        console.log("Deleted successfully: " + id);
        this.displayAll()
        this.getAllTopics()
      },
      error: (err) => console.error(err)
    });
  }

  toggleAllTopics = () => {
    this.showAllTopics = !this.showAllTopics;
    this.createForm = !this.createForm;
  }

  showCreateForm = () => {
    this.createForm = !this.createForm;
    this.showAllTopics = !this.showAllTopics;
  }

  createFormInit(fb: FormBuilder) {
    this.createTopicForm = this.fb.group({
      id: [0],
      name: [''],
      description: [''],
      isTech: [true]
    });
    this.createTopicForm.updateValueAndValidity();
    this.isEditing = true;
  }

  sendNewTopic() {
    let topic: Topic = {
      id: this.createTopicForm.get('id').value,
      name: this.createTopicForm.get('name').value,
      description: this.createTopicForm.get('description').value,
      isTech: this.createTopicForm.get('isTech').value
    }
    this.isEditing = false;
    console.log(topic);
    this.createTopic(topic);
  }
}
