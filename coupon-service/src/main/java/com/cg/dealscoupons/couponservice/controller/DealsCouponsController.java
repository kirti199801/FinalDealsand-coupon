package com.cg.dealscoupons.couponservice.controller;

import com.cg.dealscoupons.couponservice.client.Wallet;
import com.cg.dealscoupons.couponservice.client.WalletRequest;
import com.cg.dealscoupons.couponservice.client.WalletServiceClient;
import com.cg.dealscoupons.couponservice.entity.Coupon;
import com.cg.dealscoupons.couponservice.service.DealsCouponService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/coupon")
@AllArgsConstructor
@Slf4j
public class DealsCouponsController {

    private final DealsCouponService dealsCouponService;
    private final WalletServiceClient walletServiceClient;

    @PostMapping(value = "/createCoupon", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCoupon(@RequestBody Coupon coupon) {
            dealsCouponService.addCoupon(coupon);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/company/getCompanyCoupons/{companyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Coupon>> getCompanyCoupon(@PathVariable("companyId") String companyId) {
        return ResponseEntity.ok(dealsCouponService.getCompanyCoupon(companyId));
    }

    @GetMapping(value = "/customer/getCustomerCoupons/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Coupon>> getCustomerCoupon(@PathVariable("customerId") String customerId) {
        return ResponseEntity.ok(dealsCouponService.getCustomerCoupon(customerId));
    }

    @GetMapping(value = "/allCoupons", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        return ResponseEntity.ok(dealsCouponService.getAllCoupons());
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Coupon>> search(@RequestParam(value = "keyword") String keyword) {
        List<Coupon> collect = dealsCouponService.getAllCoupons()
                .stream()
                .filter(c -> c.getCategory().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }


    @GetMapping(value = "/id/{couponId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Coupon>> getAllById(@RequestParam (value = "couponId") int couponId ) {
        return ResponseEntity.ok(dealsCouponService.getCouponById(couponId));
    }

    @GetMapping(value = "/type/{couponType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Coupon>> getAllByCouponType(@RequestParam (value = "couponType") String couponType ) {
        return ResponseEntity.ok(dealsCouponService.getByCouponType(couponType));
    }

    @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Coupon>> getByName(@RequestParam (value = "name") String name ) {
        return ResponseEntity.ok(dealsCouponService.getCouponByName(name));
    }

    @PostMapping(value = "purchaseCoupon/{customerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> purchaseCoupon(@RequestHeader("Authorization") String token,
                                               @PathVariable("customerId") String customerId,
                                               @RequestBody Coupon coupon) {
        dealsCouponService.purchaseCoupon(coupon, customerId, token);

        ResponseEntity<Wallet> byCustomerId = walletServiceClient.getByCustomerId(token, customerId);
        Wallet body = byCustomerId.getBody();
        if (body != null) {
            walletServiceClient.payMoney(token,
                    WalletRequest.builder()
                    .walletId(body.getWalletId())
                    .amount(coupon.getPrice())
                    .transactionType("withdraw")
                    .build());
        } else {
            Wallet wallet = Wallet.builder().customerId(customerId).currentBalance(100000).build();
            walletServiceClient.payMoney(token,
                    WalletRequest.builder()
                    .walletId(wallet.getWalletId())
                    .amount(coupon.getPrice())
                            .transactionType("withdraw")
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/category/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Coupon>> getByCategory(@RequestParam (value = "category") String category ) {
        return ResponseEntity.ok(dealsCouponService.getCouponsByCategory(category));
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Coupon> update(@RequestBody Coupon coupon) {
        return ResponseEntity.ok(dealsCouponService.updateCoupon(coupon));
    }

    @DeleteMapping("/delete/{couponId}")
    public ResponseEntity<Void> delete(@RequestParam(value = "couponId") int couponId) {
        dealsCouponService.deleteCouponById(couponId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
