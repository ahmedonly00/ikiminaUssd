package com.ikimina.ikiminaUssd.dto;

import java.time.LocalDate;

import com.ikimina.ikiminaUssd.model.Savings.SavingsType;

public class SavingsDTO {
    private Double amount;
    private SavingsType type;
    private LocalDate date;
    private String phoneNumber;

    public SavingsDTO() {
    }

    public SavingsDTO(Double amount, SavingsType type, LocalDate date, String phoneNumber) {
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public SavingsType getType() {
        return type;
    }

    public void setType(SavingsType type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
} 