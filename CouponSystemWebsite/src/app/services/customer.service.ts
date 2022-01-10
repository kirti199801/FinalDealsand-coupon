import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Customer } from '../models/customer.model';
import { Observable } from 'rxjs';
import { Coupon } from '../models/coupon.model';
import { apiUrl } from 'src/environments/environment.prod';
import { Wallet } from '../models/wallet.model';
@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private httpClient: HttpClient) { }

  purchaseCoupon(coupon: Coupon) {
    let userId = localStorage.getItem('userId');
    return this.httpClient.post(`${apiUrl}/coupon/purchaseCoupon/${userId}`, coupon);
  }

  createWallet(wallet:Wallet): Observable<Wallet> {
    return this.httpClient.post<Wallet>(`${apiUrl}/wallet/createWallet`, wallet);
  }

  getCustomerWallet(): Observable<Wallet> {
    let userId = localStorage.getItem('userId');

    return this.httpClient.get<Wallet>(`${apiUrl}/wallet/customer/${userId}`);
  }

  getCustomerCoupons(): Observable<Coupon[]> {
    let userId = localStorage.getItem('userId');

    return this.httpClient.get<Coupon[]>(`${apiUrl}/coupon/customer/getCustomerCoupons/${userId}`);
  }

  getCustomerCouponsByCategory(category: string): Observable<Coupon[]> {
    return this.httpClient.get<Coupon[]>("http://localhost:8080/customer/getCustomerCouponsByCategory", { params: new HttpParams().set('category', category) });
  }

  getCustomerCouponsByPrice(maxPrice: number): Observable<Coupon[]> {
    return this.httpClient.get<Coupon[]>("http://localhost:8080/customer/getCustomerCouponsByPrice", { params: new HttpParams().set('maxPrice', maxPrice.toString()) });
  }

  getCustomerDetails(): Observable<Customer> {
    let userId = localStorage.getItem('userId');
    return this.httpClient.get<Customer>(`${apiUrl}/user/customer/${userId}`);
  }

  getAllCoupons(): Observable<Coupon[]> {
    return this.httpClient.get<Coupon[]>(`${apiUrl}/coupon/allCoupons`);
  }

  search(searchKeyword:string): Observable<Coupon[]> {
    return this.httpClient.get<Coupon[]>(`${apiUrl}/coupon/search?keyword=${searchKeyword}`);
  }

}
