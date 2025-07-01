package com.example.moneytransfer.service;  // Corrected package name

import com.moneytransfer.service.AccountService;
import com.moneytransfer.model.Account;
import com.moneytransfer.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;  // This will be tested

    @Mock
    private AccountRepository accountRepository;  // Mock the repository

    private Account senderAccount;
    private Account receiverAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        senderAccount = new Account(1L, "Sender", 500.0);  // Create sample sender account
        receiverAccount = new Account(2L, "Receiver", 300.0);  // Create sample receiver account
    }

    @Test
    public void testSuccessfulTransfer() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(receiverAccount));

        String result = accountService.transferMoney(1L, 2L, 200.0);

        assertEquals("Transfer successful.", result);
        assertEquals(300.0, senderAccount.getBalance());  // Sender balance should decrease
        assertEquals(500.0, receiverAccount.getBalance());  // Receiver balance should increase
    }

    @Test
    public void testNegativeAmountTransfer() {
        String result = accountService.transferMoney(1L, 2L, -100.0);

        assertEquals("Amount must be greater than 0.", result);
    }

    @Test
    public void testAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
        when(accountRepository.findById(2L)).thenReturn(Optional.of(receiverAccount));

        String result = accountService.transferMoney(1L, 2L, 100.0);

        assertEquals("One or both accounts not found.", result);
    }

    @Test
    public void testInsufficientFunds() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(receiverAccount));

        String result = accountService.transferMoney(1L, 2L, 600.0);  // More than sender's balance

        assertEquals("Insufficient funds in sender's account.", result);
    }
}
