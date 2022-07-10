import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Status } from '../models/status';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class StatusService {

  private url = environment.baseUrl + 'v1/statuses/';

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

  index(): Observable<Status[]> {
    return this.http.get<Status[]>(this.url);
  }

  show(id: number): Observable<Status> {
    return this.http.get<Status>(this.url + id);
  }

  create(status: Status) {
    return this.http.post<Status>(this.url, status).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Status service create() error: ' + err
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

  update(status: Status) {
    return this.http.put(this.url + `${status.id}`, status).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Status service update() error: ' + err
          )
        );
      })
    )
  };
}