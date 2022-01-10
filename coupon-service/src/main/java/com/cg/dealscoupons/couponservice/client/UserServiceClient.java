package com.cg.dealscoupons.couponservice.client;

import com.cg.dealscoupons.couponservice.entity.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:8080/api/user", configuration = MyRestTemplateConfig.class)
public interface UserServiceClient {
    @GetMapping(value = "/getAllCustomers", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Customer>> getAlCustomers(@RequestHeader("Authorization") String token);
}
