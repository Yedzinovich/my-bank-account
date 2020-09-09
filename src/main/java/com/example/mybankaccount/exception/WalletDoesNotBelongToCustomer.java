package com.example.mybankaccount.exception;

import com.example.mybankaccount.models.Customer;
import com.example.mybankaccount.models.Wallet;

public class WalletDoesNotBelongToCustomer extends Exception {
    public WalletDoesNotBelongToCustomer(Customer customer, Wallet wallet) {
        super("Customer with id"+customer.getUserId()+" does not have associated walletId : "+wallet.getWalletId());
    }
}
