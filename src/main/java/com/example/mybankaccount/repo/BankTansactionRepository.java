package com.example.mybankaccount.repo;

import com.example.mybankaccount.models.BankTransaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BankTansactionRepository extends CrudRepository<BankTransaction, Integer> {
    @Query("SELECT t FROM BankTransaction t WHERE t.transactionId=:transactionId")
    Iterable<BankTransaction> findBankTransactionById(@Param("transactionId") Integer transactionId);
}