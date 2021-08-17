package com.n26.dal;

import com.n26.dto.Transaction;

import java.time.Instant;
import java.util.List;

public interface TransactionRepository {

    void save(Transaction transaction);

    void deleteAll();

    List<Transaction> findAllBetweenTimestamps(Instant from, Instant to);

    List<Transaction> findTransactionsByInstant(Instant instant);
}