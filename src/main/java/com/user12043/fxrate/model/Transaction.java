package com.user12043.fxrate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Date;

@Entity
public class Transaction {
    @Id
    private byte[] id; // ULID

    @Column
    private Date date;

    @Column(name = "base_currency")
    private String baseCurrency;

    @Column(name = "target_currency")
    private String targetCurrency;

    @Column
    private double amount;

    @Column
    private double result;

    public Transaction(byte[] id, Date date, String baseCurrency, String targetCurrency, double amount, double result) {
        this.id = id;
        this.date = date;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
        this.result = result;
    }

    public Transaction() {
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
