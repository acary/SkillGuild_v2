import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Interaction } from '../models/interaction';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class InteractionService {

  private url = environment.baseUrl + 'v1/interactions/';

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

  index(): Observable<Interaction[]> {
    return this.http.get<Interaction[]>(this.url);
  }

  show(id: number): Observable<Interaction> {
    return this.http.get<Interaction>(this.url + id);
  }
  create(interaction: Interaction) {
    return this.http.post<Interaction>(this.url, interaction).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Interaction service create() error: ' + err
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

  update(interaction: Interaction) {
    return this.http.put(this.url + `${interaction.id}`, interaction).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Interaction service update() error: ' + err
          )
        );
      })
    )
  };
}
