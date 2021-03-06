package com.example.mybankaccount.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountNumber;
    private float balance;

    @ManyToOne
    @JsonIgnore
    private Customer accountHolder;

    @ManyToOne
    @JsonIgnore
    private Wallet walletHolder;

    @OneToMany(mappedBy = "transactionFromAccount")
    private List<Transaction> transactions;

    private static final long serialVersionUID = 1L;

    public Account() {
        super();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Customer getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(Customer accountHolder) {
        this.accountHolder = accountHolder;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setBankTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Wallet getWalletHolder() {
        return walletHolder;
    }

    public void setWalletHolder(Wallet walletHolder) {
        this.walletHolder = walletHolder;
    }
}
