package com.example.mybankaccount.service.implementation;

import com.example.mybankaccount.comparator.TransactionSortingComparator;
import com.example.mybankaccount.constant.Constants;
import com.example.mybankaccount.exception.*;
import com.example.mybankaccount.models.Account;
import com.example.mybankaccount.models.Transaction;
import com.example.mybankaccount.models.Customer;
import com.example.mybankaccount.models.Wallet;
import com.example.mybankaccount.repo.AccountRepository;
import com.example.mybankaccount.repo.TansactionRepository;
import com.example.mybankaccount.repo.CustomerRepository;
import com.example.mybankaccount.repo.WalletRepository;
import com.example.mybankaccount.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("walletService")
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TansactionRepository tansactionRepository;

    public  WalletServiceImpl(){}

    public WalletServiceImpl(WalletRepository walletRepository,
                             AccountRepository accountRepository,
                             CustomerRepository customerRepository,
                             TansactionRepository tansactionRepository){
        this.walletRepository = walletRepository;
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.tansactionRepository = tansactionRepository;
    }

    @Override
    public Wallet createWallet(Integer customerId) throws CustomerDoesNotExistException, CustomerAlreadyHasWalletException {

        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new CustomerDoesNotExistException(customerId);
        }
        if (customer.getWallet() != null) {
            throw new CustomerAlreadyHasWalletException(customer);
        }

        Wallet wallet = new Wallet();

        wallet.setWalletOfCustomer(customer);
        if (customer.getCustomerAccounts() != null && !customer.getCustomerAccounts().isEmpty()) {
            wallet.setAccountsInWallet(new ArrayList<>(customer.getCustomerAccounts()));
        }
        return  walletRepository.save(wallet);
    }

    @Override
    public Float getAccountBalanceForCurrentWallet(Integer walletId, Integer accountId) throws WalletIdDoesNotExistException, AccountNotAssociatedWithWalletException {

        Wallet wallet = walletRepository.findById(walletId).orElse(null);

        if (wallet == null) {
            throw new WalletIdDoesNotExistException(walletId);
        }
        List<Account> accounts =  wallet.getAccountsInWallet().stream().filter(a -> a.getAccountNumber() == accountId).collect(Collectors.toList());

        if (accounts.isEmpty()) {
            throw new AccountNotAssociatedWithWalletException(walletId, accountId);
        }

        return accounts.get(0).getBalance();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    public Account withdrawFromAccount(Integer walletId, Integer accountId, Float amount, String type) throws WalletIdDoesNotExistException,
            AccountNotAssociatedWithWalletException, InsufficientBalanceInWalletException {

        Wallet wallet = walletRepository.findById(walletId).orElse(null);

        if (wallet == null) {
            throw new WalletIdDoesNotExistException(walletId);
        }
        List<Account> accounts =  wallet.getAccountsInWallet().stream().filter(a -> a.getAccountNumber() == accountId).collect(Collectors.toList());;
        if (accounts.isEmpty()) {
            throw new AccountNotAssociatedWithWalletException(walletId, accountId);
        }
        if (accounts.get(0).getBalance() < amount) {
            throw new InsufficientBalanceInWalletException(walletId);
        }

        float currentBalance = accounts.get(0).getBalance();
        accounts.get(0).setBalance(currentBalance - amount);

        Account account = accountRepository.save(accounts.get(0));
        account.setBankTransactions(null);

        if ("WITHDRAW".equals(type)) {
            makeEntryInTransaction(Constants.WITHDRAW, amount, account.getBalance(), Constants.WITHDRAW_DESCRIPTION, account);
        }
        return account;
    }

    /**
     * Method is used to make entry into BankTransaction table for the appropriate transaction - deposit, withdrawal or transfer
     * @param amount : Amount to be deposited || withdrawn || transferred
     * @param postBalance : Balance in account after transaction has occurred
     * @param description : Custom String description associated with deposit || withdrawal || transfer
     * @param associatedAccount : Account associated with the transaction
     */
    private void makeEntryInTransaction(String typeOfTransaction, float amount, float postBalance, String description, Account associatedAccount) {
        Transaction transaction = new Transaction(typeOfTransaction, new Date(), amount, postBalance, description, associatedAccount);

        tansactionRepository.save(transaction);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    public Account depositToAccount(Integer walletId, Integer accountId, Float amount, String type) throws WalletIdDoesNotExistException,
            AccountNotAssociatedWithWalletException {

        Wallet wallet = walletRepository.findById(walletId).orElse(null);

        if (wallet==null) {
            throw new WalletIdDoesNotExistException(walletId);
        }
        List<Account> accounts =  wallet.getAccountsInWallet().stream().filter(aa -> aa.getAccountNumber() == accountId).collect(Collectors.toList());;
        if (accounts.isEmpty()) {
            throw new AccountNotAssociatedWithWalletException(walletId, accountId);
        }

        float currentBalance = accounts.get(0).getBalance();
        accounts.get(0).setBalance(currentBalance + amount);

        Account account = accountRepository.save(accounts.get(0));
        account.setBankTransactions(null);


        if ("DEPOSIT".equals(type)) {
            makeEntryInTransaction(Constants.DEPOSIT, amount, account.getBalance(), Constants.DEPOSIT_DESCRIPTION, account);
        }

        return account;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {AccountNotAssociatedWithWalletException.class, WalletIdDoesNotExistException.class, Exception.class})
    public void transferToAccount(Integer fromWalletId, Integer fromAccountId, Integer toWalletId, Integer toAccountId, Float amount) throws WalletIdDoesNotExistException,
            AccountNotAssociatedWithWalletException, InsufficientBalanceInWalletException {

        Wallet fromWallet = walletRepository.findById(fromWalletId).orElse(null);
        Wallet toWallet = walletRepository.findById(toWalletId).orElse(null);

        if (fromWallet == null || toWallet == null) {
            throw new WalletIdDoesNotExistException(fromWallet==null? fromWalletId: toWalletId);
        }

        List<Account> fromAssociateAccount =  fromWallet.getAccountsInWallet().stream().filter(aa -> aa.getAccountNumber() == fromAccountId).collect(Collectors.toList());
        List<Account> toAssociateAccount =  toWallet.getAccountsInWallet().stream().filter(aa -> aa.getAccountNumber() == toAccountId).collect(Collectors.toList());

        if (fromAssociateAccount.isEmpty()) {
            throw new AccountNotAssociatedWithWalletException(fromWalletId, fromAccountId);
        }
        if (toAssociateAccount.isEmpty()) {
            throw new AccountNotAssociatedWithWalletException(toWalletId, toAccountId);
        }

        // Withdraw
        Account fromAccount = this.withdrawFromAccount(fromWalletId,fromAccountId,amount,"TRANSFER");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("$")
                .append(amount)
                .append(" transferred to accountId : ")
                .append(toAccountId);
        makeEntryInTransaction(Constants.TRANSFER, amount, fromAccount.getBalance(), stringBuilder.toString(), fromAccount);


        // Deposit
        Account toAccount = this.depositToAccount(toWalletId, toAccountId, amount,"TRANSFER");
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1
                .append("$")
                .append(amount)
                .append(" transferred from accountId : ")
                .append(fromAccountId);
        makeEntryInTransaction(Constants.TRANSFER, amount, toAccount.getBalance(), stringBuilder1.toString(), toAccount);

    }

    @Override
    public List<Transaction> getStatement(Integer walletId, Integer accountId, Integer n) throws WalletIdDoesNotExistException,
            AccountNotAssociatedWithWalletException{

        Wallet wallet = walletRepository.findById(walletId).orElse(null);

        if (wallet == null) {
            throw new WalletIdDoesNotExistException(walletId);
        }
        List<Account> associateAccount =  wallet.getAccountsInWallet().stream().filter(aa -> aa.getAccountNumber() == accountId).collect(Collectors.toList());;
        if (associateAccount.isEmpty()) {
            throw new AccountNotAssociatedWithWalletException(walletId, accountId);
        }


        List<Transaction> bankTransactions = associateAccount.get(0).getTransactions();

        bankTransactions.sort(new TransactionSortingComparator());

        n = bankTransactions.size() >= n ? n : bankTransactions.size();
        return bankTransactions.subList(0, n);

    }
}
