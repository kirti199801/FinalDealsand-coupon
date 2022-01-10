package com.cg.dealscoupons.couponservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class CouponJson {

    @Test
    public void generateJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

//        Coupon coupon = Coupon.builder()
//                .productType("abc")
//                .productName("lcu")
//                .category("soap")
//                .rating(Map.of(1, 5.0))
//                .review(Map.of(1, "Bad"))
//                .imageList(List.of("httt"))
//                .price(122.5)
//                .description("soao")
//                .specification(Map.of("soap", "bath"))
//                .build();
//
//        System.out.println(mapper.writeValueAsString(coupon));
    }
}
