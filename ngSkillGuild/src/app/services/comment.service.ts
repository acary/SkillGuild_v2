import { AuthService } from 'src/app/services/auth.service';
import { Comment } from './../models/comment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private url = environment.baseUrl + 'v1/comments/';

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

  index(): Observable<Comment[]> {
    return this.http.get<Comment[]>(this.url);
  }

  show(id: number): Observable<Comment> {
    return this.http.get<Comment>(this.url + id);
  }

  // create(uid: number, cid: number, comment: Comment) {
  //   return this.http.post<Comment>(environment.baseUrl + 'v1/users/' + uid + '/contents/' + cid + '/comments', comment).pipe(
  //     catchError((err: any) => {
  //       console.log(err);
  //       return throwError(
  //         () => new Error(
  //           'Content service create() error: ' + err
  //         )
  //       );
  //     })
  //   );
  // }

  create(cid: number, comment: Comment) {
    return this.http.post<Comment>(environment.baseUrl + 'v1/contents/' + cid + '/comments', comment, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Content service create() error: ' + err
          )
        );
      })
    );
  }

  update(updateComment: Comment) {
    return this.http.put<Comment>(this.url + updateComment.id, updateComment).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Comment service update() error: ' + err
          )
        );
      })
    );
  }

  delete(id: number) {
    return this.http.delete<boolean>(this.url + id).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Comment service delete() error: ' + err
          )
        );
      })
    );
  }

  showByContentId(cid: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(environment.baseUrl + 'v1/contents/' + cid + '/comments');
  }

}
