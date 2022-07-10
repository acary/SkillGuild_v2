import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { InteractionType } from '../models/interaction-type';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class InteractionTypeService {

  private url = environment.baseUrl + 'v1/interactiontypes/';

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

  index(): Observable<InteractionType[]> {
    return this.http.get<InteractionType[]>(this.url);
  }

  show(id: number): Observable<InteractionType> {
    return this.http.get<InteractionType>(this.url + id);
  }
  create(interactionType: InteractionType) {
    return this.http.post<InteractionType>(this.url, interactionType).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'InteractionType service create() error: ' + err
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

  update(interactionType: InteractionType) {
    return this.http.put(this.url + `${interactionType.id}`, interactionType).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'InteractionType service update() error: ' + err
          )
        );
      })
    )
  };
}
