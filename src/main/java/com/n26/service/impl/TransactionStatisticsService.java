package com.n26.service.impl;

import com.n26.dal.TransactionRepository;
import com.n26.dto.Transaction;
import com.n26.dto.TransactionStatistics;
import com.n26.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class TransactionStatisticsService implements StatisticsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public TransactionStatistics getStatisticsOfaTimePeriod(Instant start, Instant end) {

        List<Transaction> transactionList = transactionRepository.findAllBetweenTimestamps(start, end);

        TransactionStatistics transactionStatistics = new TransactionStatistics();

        if (transactionList == null || transactionList.isEmpty())
            return transactionStatistics;

        BigDecimal[] transaction_amount_list = transactionList.stream().
                map(a -> a.getAmountDecimal()).toArray(size -> new BigDecimal[size]);

        BigDecimal sum = Arrays.stream(transaction_amount_list).reduce(
                BigDecimal.ZERO, (t, u) -> t.add(u));
        transactionStatistics.setSum(sum.toString());
        transactionStatistics.setCount(transactionList.size());
        transactionStatistics.setMax(Arrays.stream(transaction_amount_list).max(Comparator.naturalOrder()).get().toString());
        transactionStatistics.setMin(Arrays.stream(transaction_amount_list).min(Comparator.naturalOrder()).get().toString());
        transactionStatistics.setAvg(sum.
                divide(new BigDecimal(transactionStatistics.getCount()), RoundingMode.HALF_UP).toString());

        logger.info("transactionStatistics :: {}", transactionStatistics);
        return transactionStatistics;

    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

}