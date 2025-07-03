package com.moneytransfer.service;

import com.moneytransfer.model.Account;
import com.moneytransfer.model.Transfer;
import com.moneytransfer.repository.AccountRepository;
import com.moneytransfer.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransferService {
    // This service handles money transfers between accounts
    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    @Autowired
    public TransferService(AccountRepository accountRepository, TransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    @Transactional
    public Transfer transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        // Don't allow transferring to the same account
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        // Find both accounts
        Optional<Account> fromOpt = accountRepository.findById(fromAccountId);
        Optional<Account> toOpt = accountRepository.findById(toAccountId);
        // Make sure both accounts exist
        if (!fromOpt.isPresent() || !toOpt.isPresent()) {
            throw new IllegalArgumentException("Account not found");
        }
        Account from = fromOpt.get();
        Account to = toOpt.get();
        // Check if there is enough money
        BigDecimal fromBalance = BigDecimal.valueOf(from.getBalance());
        BigDecimal toBalance = BigDecimal.valueOf(to.getBalance());
        if (fromBalance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        // Subtract from sender, add to receiver
        from.setBalance(fromBalance.subtract(amount).doubleValue());
        to.setBalance(toBalance.add(amount).doubleValue());
        // Save updated accounts
        accountRepository.save(from);
        accountRepository.save(to);
        // Save the transfer record
        Transfer transfer = new Transfer(fromAccountId, toAccountId, amount, LocalDateTime.now(), "COMPLETED");
        return transferRepository.save(transfer);
    }

    // Get a transfer by its ID
    public Optional<Transfer> getTransfer(Long id) {
        return transferRepository.findById(id);
    }
} 