package com.moneytransfer.controller;  // Define the package for this controller

import com.moneytransfer.model.Account;  // Import the Account model class
import com.moneytransfer.repository.AccountRepository;  // Import the AccountRepository
import org.springframework.beans.factory.annotation.Autowired;  // Import for dependency injection
import org.springframework.web.bind.annotation.*;  // Import Spring Web annotations

@RestController  // This annotation marks this class as a REST controller (handles HTTP requests)
@RequestMapping("/accounts")  // All URLs in this controller will start with "/accounts"
public class AccountController {

    @Autowired  // This annotation tells Spring to inject the AccountRepository into this controller
    private AccountRepository accountRepository;

    // POST endpoint to create a new account
    @PostMapping  // Maps HTTP POST requests to the createAccount() method
    public Account createAccount(@RequestBody Account account) {
        return accountRepository.save(account);  // Save the new account and return it
    }

    // GET endpoint to fetch an account by its ID
    @GetMapping("/{id}")  // Maps HTTP GET requests with an ID to the getAccount() method
    public Account getAccount(@PathVariable Long id) {
        return accountRepository.findById(id).orElse(null);  // Fetch account by ID or return null if not found
    }
}
