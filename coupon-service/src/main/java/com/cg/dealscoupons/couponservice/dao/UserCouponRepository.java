package com.cg.dealscoupons.couponservice.dao;

import com.cg.dealscoupons.couponservice.entity.UserCoupon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCouponRepository extends MongoRepository<UserCoupon, String> {
}
