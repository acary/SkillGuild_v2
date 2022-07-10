import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Content } from '../models/content';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ContentService {

  private url = environment.baseUrl + 'v1/contents/';
  private guildContentUrl = environment.baseUrl + 'v1/guilds/';
  private userContentUrl = environment.baseUrl + 'v1/users/';

  constructor(
    private http: HttpClient,
    private auth: AuthService
  ) { }

  index(): Observable<Content[]> {
    return this.http.get<Content[]>(this.url);
  }

  show(id: number): Observable<Content> {
    return this.http.get<Content>(this.url + id);
  }

  getHttpOptions() {
    let options = {
      headers: {
        Authorization: 'Basic ' + this.auth.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      },
    };
    return options;
  }


  create(uid: number, gid: number, sid: number, content: Content):Observable<Content> {
    return this.http.post<Content>(environment.baseUrl + 'v1/users/' + uid + '/guilds/' + gid + '/statuses/' + sid + '/contents', content).pipe(
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

  createNewContent(gid: number, sid: number, content: Content):Observable<Content> {
    return this.http.post<Content>(this.guildContentUrl + gid + '/statuses/' + sid + '/contents', content, this.getHttpOptions()).pipe(
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

  delete(id: number) {
    return this.http.delete<boolean>(this.url + id).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Content service delete() error: ' + err
          )
        );
      })
    );
  }

  update(updateContent: Content) {
    return this.http.put<Content>(this.url + '/' + updateContent.id, updateContent).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Content service update() error: ' + err
          )
        );
      })
    );
  }

  showContentByGuild(gid: number): Observable<Content[]> {
    return this.http.get<Content[]>(this.guildContentUrl + gid + '/contents');
  }


  showContentByUser(): Observable<Content[]> {
    return this.http.get<Content[]>(this.userContentUrl + 'contents', this.getHttpOptions());
  }

}
