package com.cg.dealscoupons.cashbackservice.dao;

import com.cg.dealscoupons.cashbackservice.entity.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {

    Optional<Wallet> findByCustomerId(String customerId);
    Optional<Wallet> findByWalletId(int walletId);
    Wallet findTopByOrderByWalletIdDesc();
}
