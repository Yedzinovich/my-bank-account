package com.example.mybankaccount.controller;

import com.example.mybankaccount.models.Account;
import com.example.mybankaccount.repo.AccountRepository;
import com.example.mybankaccount.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/api/v1/account")
    public Account createAccount(@RequestBody Account account) {
        return accountRepository.save(account);
    }

}