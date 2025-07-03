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
    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    @Autowired
    public TransferService(AccountRepository accountRepository, TransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    @Transactional
    public Transfer transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        Optional<Account> fromOpt = accountRepository.findById(fromAccountId);
        Optional<Account> toOpt = accountRepository.findById(toAccountId);
        if (!fromOpt.isPresent() || !toOpt.isPresent()) {
            throw new IllegalArgumentException("Account not found");
        }
        Account from = fromOpt.get();
        Account to = toOpt.get();
        BigDecimal fromBalance = BigDecimal.valueOf(from.getBalance());
        BigDecimal toBalance = BigDecimal.valueOf(to.getBalance());
        if (fromBalance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        from.setBalance(fromBalance.subtract(amount).doubleValue());
        to.setBalance(toBalance.add(amount).doubleValue());
        accountRepository.save(from);
        accountRepository.save(to);
        Transfer transfer = new Transfer(fromAccountId, toAccountId, amount, LocalDateTime.now(), "COMPLETED");
        return transferRepository.save(transfer);
    }

    public Optional<Transfer> getTransfer(Long id) {
        return transferRepository.findById(id);
    }
} 