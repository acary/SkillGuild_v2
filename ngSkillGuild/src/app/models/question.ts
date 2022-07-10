import { Content } from "./content";

export class Question {
  id: number;
  question: string;
  correctAnswer: string;
  contentId: number;

  constructor(
    id: number = 0,
    question: string = "",
    correctAnswer: string = "",
    contentId: number = 0
  ) {
    this.id = id;
    this.question = question;
    this.correctAnswer = correctAnswer;
    this.contentId = contentId;
  }

}
