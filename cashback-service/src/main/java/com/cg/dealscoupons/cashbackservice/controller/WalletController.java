package com.cg.dealscoupons.cashbackservice.controller;

import com.cg.dealscoupons.cashbackservice.entity.Statement;
import com.cg.dealscoupons.cashbackservice.entity.Wallet;
import com.cg.dealscoupons.cashbackservice.entity.WalletRequest;
import com.cg.dealscoupons.cashbackservice.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
@AllArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping(value = "/createWallet", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Wallet> addWallet(@RequestBody Wallet wallet) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(walletService.createWallet(wallet));
    }


    @PostMapping(value = "/addMoney", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Wallet> addMoney(@RequestBody WalletRequest wallet) {
        walletService.addMoney(wallet);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(walletService.findByWalletId(wallet.getWalletId()));
    }

    @PostMapping(value = "/payMoney", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> payMoney(@RequestBody WalletRequest wallet) {
        walletService.addMoney(wallet);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping(value = "/allWallet", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Wallet>> getAllWallet() {
        return ResponseEntity.ok(walletService.getAllWallet());
    }


    @GetMapping(value = "/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Wallet> getByCustomerId(@RequestHeader("Authorization") String token,
                                                  @PathVariable("customerId") String customerId ) {
        return ResponseEntity.ok(walletService.findByCustomerId(customerId));
    }

    @GetMapping(value = "/wallet/{walletId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Wallet> getById(@PathVariable ("walletId") int walletId ) {
        return ResponseEntity.ok(walletService.findByWalletId(walletId));
    }

    @GetMapping(value = "/statement/{walletId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Statement>> getSatementById(@RequestParam (value = "walletId") int walletId ) {
        return ResponseEntity.ok(walletService.getStatementForWallet(walletId));
    }

    @GetMapping(value = "/getAllStatement", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Statement>> getSatementById( ) {
        return ResponseEntity.ok(walletService.getAllStatemet());
    }

    @DeleteMapping(value = "/{walletId}")
    public ResponseEntity<Void> deleteWallet(@RequestParam (value = "walletId") int walletId) {
        walletService.deleteWallet(walletId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
