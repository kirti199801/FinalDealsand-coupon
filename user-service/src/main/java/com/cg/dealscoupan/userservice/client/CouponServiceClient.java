package com.cg.dealscoupan.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "COUPON-SERVICE", url = "http://localhost:8080/api/coupon", configuration = MyRestTemplateConfig.class)
public interface CouponServiceClient {
    @GetMapping(value = "/company/getCompanyCoupons/{companyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Coupon>> getCompanyCoupon(@RequestHeader("Authorization") String token,
                                                  @PathVariable("companyId") String companyId);
    @GetMapping(value = "/customer/getCustomerCoupons/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Coupon>> getCustomerCoupon(@RequestHeader("Authorization") String token,
                                                   @PathVariable("customerId") String customerId);
}
