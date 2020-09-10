package com.example.mybankaccount.service.implementation;


import com.example.mybankaccount.exception.CustomerDoesNotExistException;
import com.example.mybankaccount.models.Customer;
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
        //when(walletRepoMock.save((Wallet)anyObject())).thenReturn(w);
        doReturn(wallet).when(walletRepoMock).save(any(Wallet.class));

        Assertions.assertThrows(CustomerDoesNotExistException.class, () -> {
            walletService.createWallet(2);
        });
    }

    @Test
    void getAccountBalanceForCurrentWallet() throws Exception {
    }

    @Test
    void withdrawFromAccount() {
    }

    @Test
    void depositToAccount() {
    }

    @Test
    void transferToAccount() throws Exception{
    }

    @Test
    void getStatement() {
    }
}