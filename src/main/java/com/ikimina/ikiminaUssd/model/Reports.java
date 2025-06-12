package com.ikimina.ikiminaUssd.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "reports")
public class Reports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String period; // WEEKLY or MONTHLY

    @Column(nullable = false)   
    private LocalDate fromDate;

    @Column(nullable = false)
    private LocalDate toDate;

    @Column(nullable = false)
    private Double totalSavings;

    @Column(nullable = false)
    private Double totalFines;

    @Column(nullable = false)
    private Double totalLoans;

    @Column(nullable = false)
    private LocalDate generatedOn;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Reports() {
    }

    public Reports(Long id, String period, LocalDate fromDate, LocalDate toDate, Double totalSavings, Double totalFines,
            Double totalLoans, LocalDate generatedOn, User user) {
        this.id = id;
        this.period = period;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.totalSavings = totalSavings;
        this.totalFines = totalFines;
        this.totalLoans = totalLoans;
        this.generatedOn = generatedOn;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Double getTotalSavings() {
        return totalSavings;
    }

    public void setTotalSavings(Double totalSavings) {
        this.totalSavings = totalSavings;
    }

    public Double getTotalFines() {
        return totalFines;
    }

    public void setTotalFines(Double totalFines) {
        this.totalFines = totalFines;
    }

    public Double getTotalLoans() {
        return totalLoans;
    }

    public void setTotalLoans(Double totalLoans) {
        this.totalLoans = totalLoans;
    }

    public LocalDate getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(LocalDate generatedOn) {
        this.generatedOn = generatedOn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public enum Period {
        WEEKLY,
        MONTHLY
    }
}
