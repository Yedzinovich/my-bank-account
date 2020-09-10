package com.example.mybankaccount.service.implementation;


import com.example.mybankaccount.exception.AccountNotAssociatedWithWalletException;
import com.example.mybankaccount.exception.CustomerAlreadyHasWalletException;
import com.example.mybankaccount.exception.CustomerDoesNotExistException;
import com.example.mybankaccount.exception.InsufficientBalanceInWalletException;
import com.example.mybankaccount.models.Account;
import com.example.mybankaccount.models.Customer;
import com.example.mybankaccount.models.Transaction;
import com.example.mybankaccount.models.Wallet;
import com.example.mybankaccount.repo.AccountRepository;
import com.example.mybankaccount.repo.CustomerRepository;
import com.example.mybankaccount.repo.TansactionRepository;
import com.example.mybankaccount.repo.WalletRepository;
import com.example.mybankaccount.service.WalletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
class WalletServiceImplTest {

    WalletRepository walletRepoMock = mock(WalletRepository.class);

    AccountRepository accountRepoMock = mock(AccountRepository.class);

    CustomerRepository customerRepoMock = mock(CustomerRepository.class);

    TansactionRepository transactionRepoMock = mock(TansactionRepository.class);

    private WalletServiceImpl walletService = new WalletServiceImpl(walletRepoMock,
            accountRepoMock,
            customerRepoMock,
            transactionRepoMock);


    /**
     * Method to test a creation of wallet
     * @throws Exception : Exception thrown if wallet does not exist or customer already has a wallet.
     */
    @Test
    void createWallet() throws Exception{

        Wallet wallet = new Wallet();
        wallet.setWalletId(1);

        Customer customer = new Customer("Freddie", "Mercury","mercury@queen.io");
        customer.setUserId(1);

        when(customerRepoMock.findById(anyInt())).thenReturn(java.util.Optional.of(customer));
        //when(walletRepoMock.save(any(Wallet.class)).thenReturn(wallet);
        doReturn(wallet).when(walletRepoMock).save(any(Wallet.class));

        assertEquals(1, walletService.createWallet(123).getWalletId());
    }

    /**
     * Method tests behaviour of CustomerDoesNotExistException.
     * @throws Exception CustomerDoesNotExistException
     */
    @Test
    public void createWallet_CustomerDoesNotExistException()throws Exception{

        Wallet wallet = new Wallet();
        wallet.setWalletId(2);

        Customer customer = new Customer("Freddie", "Mercury","mercury@queen.io");
        customer.setUserId(2);

        when(customerRepoMock.findById(anyInt())).thenReturn(java.util.Optional.empty());
        doReturn(wallet).when(walletRepoMock).save(any(Wallet.class));

        Assertions.assertThrows(CustomerDoesNotExistException.class, () -> {
            walletService.createWallet(2);
        });
    }

    /**
     * Method test Customer already has a wallet exception
     * @throws Exception CustomerAlreadyHasWalletException
     */
    @Test
    public void createWallet_CustomerAlreadyHasWalletException()throws Exception{

        Wallet wallet = new Wallet();
        wallet.setWalletId(3);

        Customer customer = new Customer("Freddie", "Mercury","mercury@queen.io");
        customer.setUserId(3);
        customer.setWallet(wallet);

        when(customerRepoMock.findById(anyInt())).thenReturn(java.util.Optional.of(customer));
        doReturn(wallet).when(walletRepoMock).save(any(Wallet.class));

        Assertions.assertThrows(CustomerAlreadyHasWalletException.class, () -> {
            walletService.createWallet(3);
        });
    }

    /**
     * Method to test account balance.
     * @throws Exception
     */
    @Test
    public void getAccountBalanceForCurrentWallet() throws Exception {
        Wallet wallet = new Wallet();
        wallet.setWalletId(4);

        Account account = new Account();
        account.setAccountNumber(100);
        account.setBalance(1000);

        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        wallet.setAccountsInWallet(accounts);

        when(walletRepoMock.findById(anyInt())).thenReturn(java.util.Optional.of(wallet));
        doReturn(account).when(accountRepoMock).save(any(Account.class));

        assertTrue(1000 -
                (float)walletService.getAccountBalanceForCurrentWallet(10, 100) < 0.01);
    }

    /**
     * Method to test for AccountNotAssociated Exception in wallet
     * @throws Exception AccountNotAssociatedWithWalletException
     */
    @Test
    public void getAccountBalanceForCurrentWallet_AccountNotAssociated() throws Exception {
        Wallet wallet = new Wallet();
        wallet.setWalletId(5);

        Account account = new Account();
        account.setAccountNumber(100);
        account.setBalance(1000);
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        wallet.setAccountsInWallet(accounts);

        when(walletRepoMock.findById(anyInt())).thenReturn(java.util.Optional.of(wallet));
        Assertions.assertThrows(AccountNotAssociatedWithWalletException.class, () -> {
            if(1000 - (float)walletService.getAccountBalanceForCurrentWallet(10, 101) < 0.01){
                double balance = 1000 - (float)walletService.getAccountBalanceForCurrentWallet(10, 101);
            }
        });
    }

    /**
     * Method to test withdrawal from account
     * @throws Exception
     */
    @Test
    public void withdrawFromAccount() throws Exception {
        Wallet wallet = new Wallet();
        wallet.setWalletId(6);

        Account account = new Account();
        account.setAccountNumber(100);
        account.setBalance(100);
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        wallet.setAccountsInWallet(accounts);

        when(walletRepoMock.findById(anyInt())).thenReturn(java.util.Optional.of(wallet));
        doReturn(account).when(accountRepoMock).save(any(Account.class));
        assertTrue(90 -
                walletService.withdrawFromAccount(12, 100, 10f, "WITHDRAW").getBalance() < 0.01);
    }

    /**
     * Method to test InsufficientBalanceInWalletException exception when withdrawing amount > balance
     * @throws Exception InsufficientBalanceInWalletException
     */
    @Test
    public void withdrawFromAccount_InsufficientBalance() throws InsufficientBalanceInWalletException, Exception {
        Wallet wallet = new Wallet();
        wallet.setWalletId(7);

        Account account = new Account();
        account.setAccountNumber(100);
        account.setBalance(100);
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        wallet.setAccountsInWallet(accounts);

        when(walletRepoMock.findById(anyInt())).thenReturn(java.util.Optional.of(wallet));
        doReturn(account).when(accountRepoMock).save(any(Account.class));

        Assertions.assertThrows(InsufficientBalanceInWalletException.class, () -> {
            if(90 - walletService.withdrawFromAccount(12, 100, 200f, "WITHDRAW").getBalance() < 0.01){
                double balance = 90 - walletService.withdrawFromAccount(12, 100, 200f, "WITHDRAW").getBalance();
            }
        });

    }

    /**
     * Method to test deposit to account
     * @throws Exception
     */
    @Test
    public void depositToAccount() throws Exception{
        Wallet wallet = new Wallet();
        wallet.setWalletId(8);

        Account account = new Account();
        account.setAccountNumber(100);
        account.setBalance(100);
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        wallet.setAccountsInWallet(accounts);

        when(walletRepoMock.findById(anyInt())).thenReturn(java.util.Optional.of(wallet));
        doReturn(account).when(accountRepoMock).save(any(Account.class));

        assertTrue(110 -
                walletService.depositToAccount(13, 100, 10f, "DEPOSIT").getBalance()<0.01);

    }

    /**
     * Method to test transfer from one account to another
     * @throws Exception
     */
    @Test
    public void transferToAccount()  throws Exception{
        Wallet wallet = new Wallet();
        wallet.setWalletId(9);

        Account account = new Account();
        account.setAccountNumber(100);
        account.setBalance(100);
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        wallet.setAccountsInWallet(accounts);

        Wallet wallet1 = new Wallet();
        wallet1.setWalletId(10);

        Account account1 = new Account();
        account1.setAccountNumber(200);
        account1.setBalance(200);
        List<Account> accounts1 = new ArrayList<>();
        accounts1.add(account1);
        wallet1.setAccountsInWallet(accounts1);

        when(walletRepoMock.findById(wallet.getWalletId())).thenReturn(java.util.Optional.of(wallet));
        when(walletRepoMock.findById(wallet1.getWalletId())).thenReturn(java.util.Optional.of(wallet1));

        doReturn(account).when(accountRepoMock).save(account);
        doReturn(account1).when(accountRepoMock).save(account1);

        walletService.transferToAccount(wallet.getWalletId(), account.getAccountNumber(), wallet1.getWalletId(), account1.getAccountNumber(),10f);
        assertEquals(90, (int)account.getBalance());
        assertEquals(210, (int)account1.getBalance());
    }

    /**
     * Method to test statement balance
     * @throws Exception
     */
    @Test
    public void getStatement() throws Exception{
        Wallet wallet = new Wallet();
        wallet.setWalletId(11);
        Account account = new Account();
        account.setAccountNumber(100);
        account.setBalance(100);

        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        wallet.setAccountsInWallet(accounts);

        when(walletRepoMock.findById(wallet.getWalletId())).thenReturn(java.util.Optional.of(wallet));
        doReturn(account).when(accountRepoMock).save(account);

        walletService.withdrawFromAccount(wallet.getWalletId(), account.getAccountNumber(), 10f, "WITHDRAW");
        Transaction transaction =  new Transaction();
        transaction.setPostBalance(90);
        List<Transaction> bankTransactions = new ArrayList<>();
        bankTransactions.add(transaction);
        account.setBankTransactions(bankTransactions);

        assertEquals((int)transaction.getPostBalance(), (int)walletService.getStatement(wallet.getWalletId(), account.getAccountNumber(), 1).get(0).getPostBalance());

    }
}