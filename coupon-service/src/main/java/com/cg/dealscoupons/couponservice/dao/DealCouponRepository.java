package com.cg.dealscoupons.couponservice.dao;

import com.cg.dealscoupons.couponservice.entity.Coupon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DealCouponRepository extends MongoRepository<Coupon, String> {

    Optional<Coupon> findByCouponName(String couponName);
    Optional<Coupon> findByCouponId(int couponId);
    List<Coupon> findAllByCategory(String category);
    List<Coupon> findAllByCouponType(String couponType);
    Coupon findTopByOrderByCouponIdDesc();

    List<Coupon> findAllByCompanyId(String companyId);
}
