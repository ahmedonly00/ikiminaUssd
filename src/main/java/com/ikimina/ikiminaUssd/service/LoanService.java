package com.ikimina.ikiminaUssd.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikimina.ikiminaUssd.dto.LoanDTO;
import com.ikimina.ikiminaUssd.model.Loans;
import com.ikimina.ikiminaUssd.model.Loans.Status;
import com.ikimina.ikiminaUssd.model.User;
import com.ikimina.ikiminaUssd.repository.LoanRepository;
import com.ikimina.ikiminaUssd.repository.SavingsRepository;
import com.ikimina.ikiminaUssd.repository.UserRepository;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SavingsRepository savingsRepository;

    public Loans requestLoan(LoanDTO loanDTO) {
        User user = userRepository.findByPhoneNumber(loanDTO.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if user has active loans
        Long activeLoans = loanRepository.countByUserAndStatusAndApprovedDateAfter(user, Status.APPROVED, LocalDate.now().minusMonths(1));
        if (activeLoans > 0) {
            throw new RuntimeException("You have an active loan that needs to be paid first");
        }

        // Check if user has enough savings (50% of requested loan)
        Double totalSavings = savingsRepository.sumAmountByUser(user);
        if (totalSavings == null || totalSavings < loanDTO.getAmount() * 0.5) {
            throw new RuntimeException("Insufficient savings. You need at least 50% of the requested loan amount in savings");
        }

        Loans loan = new Loans();
        loan.setAmount(loanDTO.getAmount());
        loan.setStatus(Status.PENDING);
        loan.setRequestDate(LocalDate.now());
        loan.setUser(user);

        return loanRepository.save(loan);
    }

    public void approveLoan(String adminPhoneNumber, Long loanId) {
        // Verify admin
        User admin = userRepository.findByPhoneNumber(adminPhoneNumber)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        
        if (admin.getRole() != User.Role.ADMIN) {
            throw new RuntimeException("Only admins can approve loans");
        }

        Loans loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        
        if (loan.getStatus() != Status.PENDING) {
            throw new RuntimeException("Loan is not in pending status");
        }

        loan.setStatus(Status.APPROVED);
        loan.setApprovedDate(LocalDate.now());
        loanRepository.save(loan);
    }

    public List<Loans> getUserLoans(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return loanRepository.findByUser(user);
    }

    public List<Loans> getUserLoansByPeriod(String phoneNumber, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return loanRepository.findByUserAndRequestDateBetween(user, startDate, endDate);
    }

    public Double getTotalLoansByUser(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Double total = loanRepository.sumAmountByUserAndStatus(user, Status.APPROVED);
        return total != null ? total : 0.0;
    }
} 