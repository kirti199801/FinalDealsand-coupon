package com.cg.dealscoupons.cashbackservice.dao;

import com.cg.dealscoupons.cashbackservice.entity.Statement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatementRepository extends MongoRepository<Statement, String> {

    List<Statement> findByWalletId(int walletId);
}
