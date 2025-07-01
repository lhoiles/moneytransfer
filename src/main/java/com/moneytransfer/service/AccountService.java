package com.moneytransfer.service;

import com.moneytransfer.model.Account;
import com.moneytransfer.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service  // Marks this class as a service component in Spring
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;  // Injects the AccountRepository to interact with DB

    // Method to transfer money between two accounts
    public String transferMoney(Long fromAccountId, Long toAccountId, double amount) {
        // Check if amount is positive
        if (amount <= 0) {
            return "Amount must be greater than 0.";
        }

        // Fetch sender account from database
        Optional<Account> fromAccountOpt = accountRepository.findById(fromAccountId);

        // Fetch receiver account from database
        Optional<Account> toAccountOpt = accountRepository.findById(toAccountId);

        // Check if both accounts exist
        if (fromAccountOpt.isEmpty() || toAccountOpt.isEmpty()) {
            return "One or both accounts not found.";
        }

        // Get actual Account objects
        Account fromAccount = fromAccountOpt.get();
        Account toAccount = toAccountOpt.get();

        // Check if sender has enough balance
        if (fromAccount.getBalance() < amount) {
            return "Insufficient funds in sender's account.";
        }

        // Perform the transfer
        fromAccount.setBalance(fromAccount.getBalance() - amount);  // Deduct from sender
        toAccount.setBalance(toAccount.getBalance() + amount);      // Add to receiver

        // Save both updated accounts to the DB
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return "Transfer successful.";
    }
}
