package com.example.mybankaccount.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Wallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int walletId;

    @OneToMany(mappedBy = "walletHolder")
    private List<Account> accountsInWallet;

    @OneToOne
    private Customer walletOfCustomer;

    private static final long serialVersionUID = 1L;

    public Wallet() {
        super();
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public List<Account> getAccountsInWallet() {
        return accountsInWallet;
    }

    public void setAccountsInWallet(List<Account> accountsInWallet) {
        this.accountsInWallet = accountsInWallet;
        for (Account account : accountsInWallet) {
            account.setWalletHolder(this);
        }
    }

    public Customer getWalletOfCustomer() {
        return walletOfCustomer;
    }

    public void setWalletOfCustomer(Customer walletOfCustomer) {

        this.walletOfCustomer = walletOfCustomer;
    }
}