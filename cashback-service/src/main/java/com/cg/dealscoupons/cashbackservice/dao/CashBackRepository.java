package com.cg.dealscoupons.cashbackservice.dao;

import com.cg.dealscoupons.cashbackservice.entity.CashBack;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CashBackRepository extends MongoRepository<CashBack, String> {

    List<CashBack> findAllByCustomerId(int customerId);
    List<CashBack> findAllByCouponId(int couponId);
    Optional<CashBack> findByCouponIdAndCustomerId(int couponId, String customerId);
}
