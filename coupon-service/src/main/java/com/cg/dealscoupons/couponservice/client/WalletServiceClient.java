package com.cg.dealscoupons.couponservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "CASHBACK-SERVICE", url = "http://localhost:8080/api/wallet", configuration = MyRestTemplateConfig.class)
public interface WalletServiceClient {
    @PostMapping(value = "/createWallet", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Wallet> addWallet(@RequestHeader("Authorization") String token,
                                     @RequestBody Wallet wallet);
    @GetMapping(value = "/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Wallet> getByCustomerId(@RequestHeader("Authorization") String token,
                                           @PathVariable("customerId") String customerId);
    @PostMapping(value = "/payMoney", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> payMoney(@RequestHeader("Authorization") String token,
                                  @RequestBody WalletRequest wallet);
}
