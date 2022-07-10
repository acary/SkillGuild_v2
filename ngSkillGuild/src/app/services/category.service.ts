import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Category } from '../models/category';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private url = environment.baseUrl + 'v1/categories/';

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

  index(): Observable<Category[]> {
    return this.http.get<Category[]>(this.url);
  }

  show(id: number): Observable<Category> {
    return this.http.get<Category>(this.url + id);
  }

  create(category: Category) {
    return this.http.post<Category>(this.url, category).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Category service create() error: ' + err
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

  update(category: Category) {
    return this.http.put(this.url + `${category.id}`, category).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'Category service update() error: ' + err
          )
        );
      })
    )
  };
}