package com.example.moneytransfer.model;  // Define the package for this class

// The @Entity annotation marks this class as an entity that will be mapped to a database table.
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity  // Marks this class as a JPA entity (this will be stored in the database as a table)
public class Account {

    // @Id annotation marks this field as the primary key for the database table
    @Id  
    private Long id;  // Unique identifier for the account (this will be stored in the database)

    private String name;  // Account holder's name (this will be stored in the database)

    private double balance;  // Account balance (this will be stored in the database)

    // Default constructor (required by JPA)
    public Account() {
    }

    // Constructor to create an Account with specific values
    public Account(Long id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    // Getters and setters allow you to access and modify the fields of the Account class
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Override the toString() method to represent the Account object as a String
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}

