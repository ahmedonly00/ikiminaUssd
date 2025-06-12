package com.ikimina.ikiminaUssd.dto;

import java.time.LocalDate;

public class FineDTO {
    private String reason;
    private Double amount;
    private LocalDate date;
    private String phoneNumber;

    public FineDTO() {
    }

    public FineDTO(String reason, Double amount, LocalDate date, String phoneNumber) {
        this.reason = reason;
        this.amount = amount;
        this.date = date;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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