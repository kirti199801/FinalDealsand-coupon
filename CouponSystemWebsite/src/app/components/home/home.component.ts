import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Coupon } from 'src/app/models/coupon.model';
import { CustomerService } from 'src/app/services/customer.service';
import { DataService } from 'src/app/services/data.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  searchKeyword: string;
  coupons: Coupon[] = null;
  private pointer: number = 0;
  err: string;
  private interval;
  coupon: Coupon;
  couponsByCategory: Coupon[];
  constructor(private dataService: DataService, private customerService: CustomerService, private router: Router,) { }

  ngOnInit(): void {
    this.dataService.setErr('');
    this.customerService.getAllCoupons().subscribe(val => {
      this.coupons = val;
      this.err = "";
    }, err => {
      this.err = err.error.message;
    });
    if (!this.coupons) {
      this.interval = setInterval(() => {
        this.coupon = this.coupons[this.pointer];
        this.pointer++;
        if (this.pointer === this.coupons.length) { this.pointer = 0 };
      }, 3000)
    }
  }

  search() {
    console.log("Searching for Item " + this.searchKeyword);
    this.customerService.search(this.searchKeyword).subscribe(val => {
      this.coupons = val;
      this.err = "";
    }, err => {
      this.err = err.error.message;
    });
    if (!this.coupons) {
      this.interval = setInterval(() => {
        this.coupon = this.coupons[this.pointer];
        this.pointer++;
        if (this.pointer === this.coupons.length) { this.pointer = 0 };
      }, 3000)
    }
  }
  purchaseCoupon(coupon) {
    this.err = "";
    let success = "";
    this.coupons = null;
    this.couponsByCategory = null;
    let userId = localStorage.getItem('userId');
    if (userId && userId !== '') {
      this.customerService.purchaseCoupon(coupon).subscribe(val => {
        success = "The purchase has been completed successfully"
        this.ngOnInit();
        this.err = "";
      }, err => {
        success = "";
        this.err = err.error.message;
      });
    } else {
      this.router.navigateByUrl('login');
    }
    
  }
  clickMe(coupon: Coupon) {
  }
}
