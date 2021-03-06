package com.n26.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1l;
    @JsonIgnore
    private BigDecimal amountDecimal;
    private String amount;
    private Instant timestamp;

    public Transaction(){

    }

    public Transaction(BigDecimal amountDecimal, Instant timestamp){
        this.amountDecimal = amountDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
        this.timestamp = timestamp;
    }

    public Transaction(String amount, Instant timestamp){
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public BigDecimal getAmountDecimal() {
        return amountDecimal;
    }

    public void setAmountDecimal(BigDecimal amountDecimal) {
        this.amountDecimal = amountDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(amountDecimal, that.amountDecimal) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountDecimal, amount, timestamp);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amountDecimal=" + amountDecimal +
                ", amount='" + amount + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}