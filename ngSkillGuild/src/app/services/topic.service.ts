import { Topic } from './../models/topic';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { catchError, Observable, throwError } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private url = environment.baseUrl + 'v1/topics/';

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

  index(): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.url);
  }

  show(id: number): Observable<Topic> {
    return this.http.get<Topic>(this.url + id);
  }

  create(topic: Topic) {
    return this.http.post<Topic>(this.url, topic).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Topic service create() error: ' + err
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

  update(topic: Topic) {
    return this.http.put(this.url + `${topic.id}`, topic).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Topic service update() error: ' + err
          )
        );
      })
    )
  };
}
