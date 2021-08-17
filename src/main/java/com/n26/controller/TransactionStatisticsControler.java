package com.n26.controller;

import com.n26.dto.TransactionStatistics;
import com.n26.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
public class TransactionStatisticsControler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatisticsService transactionStatisticsService;

    @Value("${seconds.to.expire}")
    private int secondsToExpire;

    @RequestMapping(value = "/statistics", method = {RequestMethod.GET}, produces = "application/json")
    private ResponseEntity<TransactionStatistics> statistics() {
        Instant instantOfRequest = Instant.now();
        TransactionStatistics transactionStatistics = transactionStatisticsService.
                getStatisticsOfaTimePeriod(instantOfRequest.minus(secondsToExpire, ChronoUnit.SECONDS),
                        instantOfRequest);

        return new ResponseEntity<>(transactionStatistics, HttpStatus.OK);
    }
}