package com.example.mybankaccount.exception;

import com.example.mybankaccount.models.Customer;

public class CustomerAlreadyHasWalletException extends Exception {
    public CustomerAlreadyHasWalletException(Customer customer) {
        super("Customer "+customer.getFirstName()+" "+customer.getLastname()+" already owns a wallet : "+customer.getWallet().getWalletId());
    }
}
