package com.cg.dealscoupons.couponservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "coupon")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Coupon {
    @Id
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
