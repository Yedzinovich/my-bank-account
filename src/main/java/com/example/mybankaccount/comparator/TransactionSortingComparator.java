package com.example.mybankaccount.comparator;

import com.example.mybankaccount.models.Transaction;

import java.util.Comparator;

public class TransactionSortingComparator implements Comparator<Transaction> {

    public int compare(Transaction t1, Transaction t2) {
        return t2.getTimestamp().compareTo(t1.getTimestamp());
    }
}
