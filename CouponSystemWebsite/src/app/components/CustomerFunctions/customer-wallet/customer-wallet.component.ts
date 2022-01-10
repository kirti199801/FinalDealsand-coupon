import { Component, OnInit } from '@angular/core';
import { Wallet } from 'src/app/models/wallet.model';
import { CustomerService } from 'src/app/services/customer.service';
@Component({
  selector: 'app-customer-wallet',
  templateUrl: './customer-wallet.component.html',
  styleUrls: ['./customer-wallet.component.css']
})
export class CustomerWalletComponent implements OnInit {

  wallet: Wallet;
  err: string;
  constructor(private customerService:CustomerService) { }

  ngOnInit(): void {
    this.customerService.getCustomerWallet().subscribe(
      val => {
        this.wallet = val;
        this.err = "";
      }, (err => {
        this.err = err.error.message;
      })
    );
  }

  createWallet() {
    let userId = localStorage.getItem('userId');

    let customerWallet = {
      id: null,
      customerId: userId,
      walletId:0,
      currentBalance: 1000.0
    }
    this.customerService.createWallet(customerWallet).subscribe(wallet => this.wallet = wallet, _=> {
      console.log("wallet creation failed");
    })
  }

}
