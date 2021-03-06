package com.n26.service.impl;

import com.n26.dal.TransactionRepository;
import com.n26.dto.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TransactionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TransactionRepository transactionRepository;

    @Value("${seconds.to.expire}")
    private int secondsToExpire;

    public int getSecondsToExpire() {
        return secondsToExpire;
    }

    public void setSecondsToExpire(int secondsToExpire) {
        this.secondsToExpire = secondsToExpire;
    }

    public boolean validateForOlderTransactionTimestamp(Transaction transaction, Instant now) {
        return transaction.getTimestamp().isAfter(now.minus( secondsToExpire, ChronoUnit.SECONDS));
    }

    public boolean validateForFutureTransactionTimestamp(Transaction transaction, Instant now) {
        return !transaction.getTimestamp().isAfter(now);
    }

    public List<Transaction> findTransactionsByInstant(Instant instant) {
        return transactionRepository.findTransactionsByInstant(instant);
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void removeTransactions() {
        transactionRepository.deleteAll();
    }

    public boolean validateTransactionAmount(Transaction transaction) {
        try {
            transaction.setAmountDecimal(new BigDecimal(transaction.getAmount()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

}