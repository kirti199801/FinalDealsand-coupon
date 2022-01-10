package com.cg.dealscoupons.cashbackservice.service;

import com.cg.dealscoupons.cashbackservice.dao.CashBackRepository;
import com.cg.dealscoupons.cashbackservice.entity.CashBack;
import com.cg.dealscoupons.cashbackservice.entity.CashbackRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CashbackService {

    private final CashBackRepository cashBackRepository;

    public List<CashBack> getAllCashBackForCustomer(int customerId) {
        return cashBackRepository.findAllByCustomerId(customerId);
    }

    public boolean isCouponValidForUser(String customerId, int couponId) {
        Optional<CashBack> byCouponIdAndCustomerId = cashBackRepository.findByCouponIdAndCustomerId(couponId, customerId);
        return byCouponIdAndCustomerId.isEmpty();
    }

    public void addCashback(CashbackRequest cashbackRequest) {
        cashBackRepository.save(CashBack.builder().amount(cashbackRequest.getAmount()).couponId(cashbackRequest.getCouponId()).customerId(cashbackRequest.getCustomerId()).build());
    }
}
