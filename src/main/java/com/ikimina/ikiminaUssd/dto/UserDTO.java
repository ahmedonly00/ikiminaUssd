package com.ikimina.ikiminaUssd.dto;

import com.ikimina.ikiminaUssd.model.User.Role;

public class UserDTO {
    private String phoneNumber;
    private String fullName;
    private Role role;
    private Double totalSavings;
    private Double totalLoans;
    private Double totalFines;

    public UserDTO() {
    }

    public UserDTO(String phoneNumber, String fullName, Role role, Double totalSavings, Double totalLoans, Double totalFines) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.role = role;
        this.totalSavings = totalSavings;
        this.totalLoans = totalLoans;
        this.totalFines = totalFines;
    }

    // Getters and Setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Double getTotalSavings() {
        return totalSavings;
    }

    public void setTotalSavings(Double totalSavings) {
        this.totalSavings = totalSavings;
    }

    public Double getTotalLoans() {
        return totalLoans;
    }

    public void setTotalLoans(Double totalLoans) {
        this.totalLoans = totalLoans;
    }

    public Double getTotalFines() {
        return totalFines;
    }

    public void setTotalFines(Double totalFines) {
        this.totalFines = totalFines;
    }
} 