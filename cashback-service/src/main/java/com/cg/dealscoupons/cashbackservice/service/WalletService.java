package com.cg.dealscoupons.cashbackservice.service;

import com.cg.dealscoupons.cashbackservice.dao.StatementRepository;
import com.cg.dealscoupons.cashbackservice.dao.WalletRepository;
import com.cg.dealscoupons.cashbackservice.entity.Statement;
import com.cg.dealscoupons.cashbackservice.entity.Wallet;
import com.cg.dealscoupons.cashbackservice.entity.WalletRequest;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final StatementRepository statementRepository;

    public List<Wallet> getAllWallet() {
        return walletRepository.findAll();
    }

    public Wallet createWallet(Wallet wallet) {
        if (walletRepository.findByCustomerId(wallet.getCustomerId()).isEmpty()) {
            wallet.setWalletId(getNextWalletId());
            return walletRepository.save(wallet);
        }
        else
            throw new IllegalArgumentException("Wallet already exist for customer " + wallet.getCustomerId());
    }

    @Synchronized
    public void addMoney(WalletRequest request) {
        Optional<Wallet> byWalletId = walletRepository.findByWalletId(request.getWalletId());
        if (byWalletId.isPresent()) {
            Wallet wallet1 = byWalletId.get();
            if (request.getTransactionType().equalsIgnoreCase("deposit")) {
                wallet1.setCurrentBalance(wallet1.getCurrentBalance() + request.getAmount());
            } else {
                wallet1.setCurrentBalance(wallet1.getCurrentBalance() - request.getAmount());
            }
            walletRepository.save(wallet1);
        }
    }

    public Wallet findByWalletId(int walletId) {
        return walletRepository.findByWalletId(walletId).orElse(Wallet.builder().build());
    }

    public Wallet findByCustomerId(String customerId) {
        return walletRepository.findByCustomerId(customerId).orElse(Wallet.builder().build());
    }

    public List<Statement> getStatementForWallet(int walletId) {
        return statementRepository.findByWalletId(walletId);
    }

    public List<Statement> getAllStatemet() {
        return statementRepository.findAll();
    }

    @Synchronized
    public void deleteWallet(int walletId) {
        Optional<Wallet> byWalletId = walletRepository.findByWalletId(walletId);
        if (byWalletId.isPresent()) {
            List<Statement> byWalletId1 = statementRepository.findByWalletId(walletId);
            statementRepository.deleteAll(byWalletId1);
            walletRepository.delete(byWalletId.get());
        }
    }

    @Synchronized
    public int getNextWalletId() {
        Wallet top1ByWalletIdDesc = walletRepository.findTopByOrderByWalletIdDesc();
        int walletId = (top1ByWalletIdDesc != null) ? top1ByWalletIdDesc.getWalletId() : 0;
        return ++walletId;
    }
}
