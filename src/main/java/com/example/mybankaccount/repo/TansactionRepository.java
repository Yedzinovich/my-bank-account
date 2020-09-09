package com.example.mybankaccount.repo;

import com.example.mybankaccount.models.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TansactionRepository extends CrudRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE t.transactionId=:transactionId")
    Iterable<Transaction> findBankTransactionById(@Param("transactionId") Integer transactionId);
}