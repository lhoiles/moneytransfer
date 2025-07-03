package com.example.moneytransfer.repository;

import com.example.moneytransfer.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
} 