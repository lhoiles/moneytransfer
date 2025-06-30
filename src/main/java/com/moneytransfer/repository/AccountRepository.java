package com.moneytransfer.repository;  // Define the package for this repository

import com.moneytransfer.model.Account;  // Import the Account model class
import org.springframework.data.jpa.repository.JpaRepository;  // Import JPA repository interface

// The AccountRepository interface extends JpaRepository to provide CRUD operations for Account entities
public interface AccountRepository extends JpaRepository<Account, Long> {
    // You don’t need to write any methods here because JpaRepository provides all basic CRUD operations (save, find, delete)
}
