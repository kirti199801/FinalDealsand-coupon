package com.cg.dealscoupan.userservice.client;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Coupon {
    private String id;

    private int couponId;
    private String companyId;
    private String couponType;
    private String couponName;
    private String category;
    private String title;
    private String description;
    private int amount;
    private double price;
    private String image;
    private LocalDate startDate;
    private LocalDate endDate;

}
