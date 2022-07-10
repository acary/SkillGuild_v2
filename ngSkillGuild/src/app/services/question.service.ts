import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Question } from '../models/question';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  private url = environment.baseUrl + 'v1/questions/';

  constructor(
    private http: HttpClient,
    private auth: AuthService
  ) { }

  getHttpOptions() {
    let options = {
      headers: {
        Authorization: 'Basic ' + this.auth.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      },
    };
    return options;
  }

  index(): Observable<Question[]> {
    return this.http.get<Question[]>(this.url);
  }

  show(id: number): Observable<Question> {
    return this.http.get<Question>(this.url + id);
  }

  create(cid: number, newQuestion: Question) {
    return this.http.post<Question>(this.url + cid, newQuestion).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Question service create() error: ' + err
          )
        );
      })
    );
  }

  delete(id: number) {
    return this.http.delete<boolean>(this.url + id).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(err);
      })
    );
  }

  update(question: Question) {
    return this.http.put(this.url + `${question.id}`, question).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Question service update() error: ' + err
          )
        );
      })
    )
  }

  showByContentId(cid: number): Observable<Question[]> {
    return this.http.get<Question[]>(environment.baseUrl + 'v1/questions/' + cid + '/questions');
  }

}