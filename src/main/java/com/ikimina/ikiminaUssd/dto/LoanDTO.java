package com.ikimina.ikiminaUssd.dto;

import java.time.LocalDate;

import com.ikimina.ikiminaUssd.model.Loans.Status;

public class LoanDTO {
    private Double amount;
    private Status status;
    private LocalDate requestDate;
    private LocalDate approvedDate;
    private String phoneNumber;

    public LoanDTO() {
    }

    public LoanDTO(Double amount, Status status, LocalDate requestDate, LocalDate approvedDate, String phoneNumber) {
        this.amount = amount;
        this.status = status;
        this.requestDate = requestDate;
        this.approvedDate = approvedDate;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDate approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
} 