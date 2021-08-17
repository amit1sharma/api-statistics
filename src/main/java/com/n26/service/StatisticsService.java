package com.n26.service;

import com.n26.dto.TransactionStatistics;

import java.time.Instant;

public interface StatisticsService {

    TransactionStatistics getStatisticsOfaTimePeriod(Instant start, Instant end);
}