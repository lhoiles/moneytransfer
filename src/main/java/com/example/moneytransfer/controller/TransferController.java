package com.example.moneytransfer.controller;

import com.example.moneytransfer.model.Transfer;
import com.example.moneytransfer.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/transfers")
public class TransferController {
    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    // This endpoint creates a new money transfer
    @PostMapping
    public ResponseEntity<?> createTransfer(@RequestBody TransferRequest request) {
        try {
            Transfer transfer = transferService.transfer(
                    request.getFromAccountId(),
                    request.getToAccountId(),
                    request.getAmount()
            );
            return ResponseEntity.ok(transfer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // This endpoint gets a transfer by its ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTransfer(@PathVariable Long id) {
        Optional<Transfer> transfer = transferService.getTransfer(id);
        return transfer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // This class is used to receive transfer requests from the client
    public static class TransferRequest {
        private Long fromAccountId;
        private Long toAccountId;
        private BigDecimal amount;

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
        public BigDecimal getAmount() {
            return amount;
        }
        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }
} 