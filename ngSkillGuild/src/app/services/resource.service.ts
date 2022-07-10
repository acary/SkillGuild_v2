import { Resource } from './../models/resource';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ResourceService {

  private url = environment.baseUrl + 'v1/resources/';

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

  index(): Observable<Resource[]> {
    return this.http.get<Resource[]>(this.url);
  }

  show(id: number): Observable<Resource> {
    return this.http.get<Resource>(this.url + id);
  }

  create(newResource: Resource) {
    console.log(newResource)
    return this.http.post<Resource>(this.url, newResource).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Resource service create() error: ' + err
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

  update(resource: Resource) {
    return this.http.put(this.url + `${resource.id}`, resource).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Resource service update() error: ' + err
          )
        );
      })
    )
  };
}
