import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Company } from '../models/company.model';
import { Observable } from 'rxjs';
import { Customer } from '../models/customer.model';
import { apiUrl } from 'src/environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private httpClient: HttpClient) { }

  addCompany(company: Company): Observable<any> {
    return this.httpClient.post(`${apiUrl}/user/addCompany`, company);
  }

  updateCompany(company: Company): Observable<any> {
    return this.httpClient.put("http://localhost:8080/admin/updateCompany", company);
  }

  deleteCompany(id: number) {
    return this.httpClient.delete("http://localhost:8080/admin/deleteCompany/" + id);
  }

  getAllCompanies(): Observable<Company[]> {
    return this.httpClient.get<Company[]>(`${apiUrl}/user/getAllCompanies`);
  }

  getOneCompany(id: number): Observable<Company> {
    return this.httpClient.get("http://localhost:8080/admin/getOneCompany/" + id);
  }

  addCustomer(customer: Customer): Observable<any> {
    return this.httpClient.post(`${apiUrl}/user/addCustomer`, customer);
  }

  updateCustomer(customer: Customer): Observable<any> {
    return this.httpClient.put("http://localhost:8080/admin/updateCustomer", customer);
  }

  deleteCustomer(id: number) {
    return this.httpClient.delete("http://localhost:8080/admin/deleteCustomer/" + id);
  }

  getAllCustomers(): Observable<Customer[]> {
    return this.httpClient.get<Customer[]>(`${apiUrl}/user/getAllCustomers`);
  }

  getOneCustomer(id: number): Observable<Customer> {
    return this.httpClient.get("http://localhost:8080/admin/getOneCustomer/" + id);
  }

}
