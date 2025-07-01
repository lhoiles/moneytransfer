package com.moneytransfer.controller;

import com.moneytransfer.model.Account;
import com.moneytransfer.repository.AccountRepository;
import com.moneytransfer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;  // Inject the service

    // Create a new account
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountRepository.save(account);
    }

    // Get all accounts
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // POST /api/accounts/transfer
    @PostMapping("/transfer")
    public String transferMoney(@RequestBody TransferRequest request) {
        return accountService.transferMoney(
            request.getFromAccountId(),
            request.getToAccountId(),
            request.getAmount()
        );
    }

    // Simple DTO to represent the request body for transfer
    static class TransferRequest {
        private Long fromAccountId;
        private Long toAccountId;
        private double amount;

        // Getters and setters
        public Long getFromAccountId() {
            return fromAccountId;
        }

        public void setFromAccountId(Long fromAccountId) {
            this.fromAccountId = fromAccountId;
        }

        public Long getToAccountId() {
            return toAccountId;
        }

        public void setToAccountId(Long toAccountId) {
            this.toAccountId = toAccountId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }
}
