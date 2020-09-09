package com.example.mybankaccount.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String firstName;
    private String lastName;

    @Column(unique=true)
    private String email;

    @OneToOne(mappedBy = "walletOfCustomer")
    @JsonIgnore
    private Wallet wallet;

    @OneToMany(mappedBy = "accountHolder")
    @JsonIgnore
    private List<Account> customerAccounts;

    private static final long serialVersionUID = 1L;

    public Customer() {
        super();
    }

    public Customer(String firstName, String lastName, String email) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public List<Account> getCustomerAccounts() {
        return customerAccounts;
    }

    public void setCustomerAccounts(List<Account> customerAccounts) {
        this.customerAccounts = customerAccounts;
    }
}
