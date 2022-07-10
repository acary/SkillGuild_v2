import { ResourceType } from './../models/resource-type';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ResourceTypeService {

  private url = environment.baseUrl + 'v1/resourcetypes/';

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

  index(): Observable<ResourceType[]> {
    return this.http.get<ResourceType[]>(this.url);
  }

  show(id: number): Observable<ResourceType> {
    return this.http.get<ResourceType>(this.url + id);
  }
  create(resourceType: ResourceType) {
    return this.http.post<ResourceType>(this.url, resourceType).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'ResourceType service create() error: ' + err
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

  update(resourceType: ResourceType) {
    return this.http.put(this.url + `${resourceType.id}`, resourceType).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error(
            'ResourceType service update() error: ' + err
          )
        );
      })
    )
  };
}
