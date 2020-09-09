package com.example.mybankaccount.service;

import com.example.mybankaccount.exception.*;
import com.example.mybankaccount.models.Account;
import com.example.mybankaccount.models.Transaction;
import com.example.mybankaccount.models.Wallet;

import java.util.List;

public interface WalletService {

    public Wallet createWallet(Integer customerId) throws CustomerDoesNotExistException, CustomerAlreadyHasWalletException;

    public Float getAccountBalanceForCurrentWallet(Integer walledId, Integer accountId) throws WalletIdDoesNotExistException, AccountNotAssociatedWithWalletException;

    public Account withdrawFromAccount(Integer walletId, Integer accountId, Float amount, String type) throws WalletIdDoesNotExistException,
            AccountNotAssociatedWithWalletException, InsufficientBalanceInWalletException;

    public Account depositToAccount(Integer walletId, Integer accountId, Float amount, String type) throws WalletIdDoesNotExistException,
            AccountNotAssociatedWithWalletException;

    public void transferToAccount(Integer fromWalletId, Integer fromAccountId, Integer toWalletId, Integer toAccountId, Float amount) throws WalletIdDoesNotExistException,
            AccountNotAssociatedWithWalletException, InsufficientBalanceInWalletException;

    public List<Transaction> getStatement(Integer walletId, Integer accountId, Integer n) throws WalletIdDoesNotExistException,
            AccountNotAssociatedWithWalletException;

}