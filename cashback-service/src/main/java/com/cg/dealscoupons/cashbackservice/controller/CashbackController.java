package com.cg.dealscoupons.cashbackservice.controller;

import com.cg.dealscoupons.cashbackservice.entity.CashBack;
import com.cg.dealscoupons.cashbackservice.entity.CashbackRequest;
import com.cg.dealscoupons.cashbackservice.entity.Wallet;
import com.cg.dealscoupons.cashbackservice.entity.WalletRequest;
import com.cg.dealscoupons.cashbackservice.service.CashbackService;
import com.cg.dealscoupons.cashbackservice.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cashback")
@AllArgsConstructor
public class CashbackController {

    private final WalletService walletService;
    private final CashbackService cashbackService;



    @PostMapping(value = "/addCashBack", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCashback(@RequestBody CashbackRequest cashbackRequest) {
        boolean couponValidForUser = cashbackService.isCouponValidForUser(cashbackRequest.getCustomerId(), cashbackRequest.getCouponId());
        if (couponValidForUser) {
            cashbackService.addCashback(cashbackRequest);
            Wallet byCustomerId = walletService.findByCustomerId(cashbackRequest.getCustomerId());
            if (byCustomerId.getWalletId() <= 0) {
                byCustomerId = walletService.createWallet(Wallet.builder()
                        .currentBalance(0)
                        .walletId(walletService.getNextWalletId())
                        .customerId(cashbackRequest.getCustomerId())
                        .build());
            }
            walletService.addMoney(WalletRequest.builder().amount(cashbackRequest.getAmount()).transactionType("deposit").walletId(byCustomerId.getWalletId()).build());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Cashback added");
        } else {
            throw new IllegalArgumentException("This coupon is already applied to customer");
        }
    }

    @GetMapping(value = "/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CashBack>> getByCustomerId(@RequestParam(value = "customerId") int customerId ) {
        return ResponseEntity.ok(cashbackService.getAllCashBackForCustomer(customerId));
    }
}
