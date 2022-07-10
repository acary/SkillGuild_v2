import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Content } from 'src/app/models/content';
import { Question } from 'src/app/models/question';
import { QuestionService } from 'src/app/services/question.service';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {

  questions: Question[] = [];

  newQuestion: Question = new Question();

  selected: Question | null = null;

  showAllQuestions: boolean = true;

  createForm: boolean = false;

  createQuestionForm: any;

  isEditing = false;

  editQuestion: Question | null = null;

  constructor(
    private questionSvc: QuestionService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.createFormInit(fb);
  }

  ngOnInit(): void {
    this.getAllQuestions();
  }
  getAllQuestions() {
    this.questionSvc.index().subscribe(questions => {
      this.questions = questions;
    });
  }

  createQuestion(cid: number, question: Question): void {
    this.questionSvc.create(cid, question).subscribe({
      next: data => {
        console.log("Created new question successfully");
        this.questions.push(data);
        this.newQuestion = new Question();
        this.toggleAllQuestions();
        this.getAllQuestions();
      },
      error: (err) => {
        console.error('Error creating question: ', err);
      }
    })
  }

  setEditQuestion = () => {
    this.editQuestion = Object.assign({}, this.selected);
  }

  cancelEdit = () => {
    this.editQuestion = null;
  }

  updateQuestion = (question: Question) => {
    this.questionSvc.update(question).subscribe(
      {
        next: () => {
          console.log("Updated question successfully: " + question.id);
          this.selected = null;
          this.editQuestion = null;
          this.displayAll();
          this.getAllQuestions();
        },
        error: (err: any) => console.error('Error updating question: ', err)
      }
    );
  }

  displayAll(): void {
    this.selected = null;
  }

  getQuestionById(id: number) {
    this.questionSvc.show(id).subscribe(
      question => {
        return question;
      },
      err => {
        console.log(err);
      }
    )
  }

  deleteQuestion(id: number) {
    this.questionSvc.delete(id).subscribe({
      next: (data) => {
        console.log("Deleted successfully: " + id);
        this.displayAll()
        this.getAllQuestions()
      },
      error: (err) => console.error(err)
    });
  }

  toggleAllQuestions = () => {
    this.showAllQuestions = !this.showAllQuestions;
    this.createForm = !this.createForm;
  }

  showCreateForm = () => {
    this.createForm = !this.createForm;
    this.showAllQuestions = !this.showAllQuestions;
  }

  createFormInit(fb: FormBuilder) {
    this.createQuestionForm = this.fb.group({
      id: [100],
      question: [''],
      correctAnswer: [''],
      contentId: [''],
    });
    this.createQuestionForm.updateValueAndValidity();
    this.isEditing = true;
  }

  sendNewQuestion() {
    let question: Question = {
      id: this.createQuestionForm.get('id').value,
      question: this.createQuestionForm.get('question').value,
      correctAnswer: this.createQuestionForm.get('correctAnswer').value,
      contentId: this.createQuestionForm.get('contentId').value
    }
    this.isEditing = false;
    this.createQuestion(question.contentId, question);
    console.log(question);
  }
}
