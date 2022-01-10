import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';
import { apiUrl } from 'src/environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpClient: HttpClient) { }

  loginAdmin(user: User): Observable<{ "token": string }> {
    return this.httpClient.post<{ "token": string }>(`${apiUrl}/user/login`, user);
  }

  loginCompany(user: User): Observable<{ "token": string }> {
    return this.httpClient.post<{ "token": string }>(`${apiUrl}/user/login`, user);
  }

  loginCustomer(user: User): Observable<{ "token": string }> {
    return this.httpClient.post<{ "token": string }>(`${apiUrl}/user/login`, user);
  }
}
