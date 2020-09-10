package com.example.mybankaccount.service;

import com.example.mybankaccount.exception.*;
import com.example.mybankaccount.models.Account;
import com.example.mybankaccount.models.Transaction;
import com.example.mybankaccount.models.Wallet;

import java.util.List;

public interface WalletService {

    Wallet createWallet(Integer customerId) throws CustomerDoesNotExistException, CustomerAlreadyHasWalletException;

    Float getAccountBalanceForCurrentWallet(Integer walledId, Integer accountId) throws WalletIdDoesNotExistException, AccountNotAssociatedWithWalletException;

    Account withdrawFromAccount(Integer walletId, Integer accountId, Float amount, String type) throws WalletIdDoesNotExistException,
            AccountNotAssociatedWithWalletException, InsufficientBalanceInWalletException;

    Account depositToAccount(Integer walletId, Integer accountId, Float amount, String type) throws WalletIdDoesNotExistException,
            AccountNotAssociatedWithWalletException;

    void transferToAccount(Integer fromWalletId, Integer fromAccountId, Integer toWalletId, Integer toAccountId, Float amount) throws WalletIdDoesNotExistException,
            AccountNotAssociatedWithWalletException, InsufficientBalanceInWalletException;

    List<Transaction> getStatement(Integer walletId, Integer accountId, Integer n) throws WalletIdDoesNotExistException,
            AccountNotAssociatedWithWalletException;

}