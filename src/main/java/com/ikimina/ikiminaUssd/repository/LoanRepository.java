package com.ikimina.ikiminaUssd.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ikimina.ikiminaUssd.model.Loans;
import com.ikimina.ikiminaUssd.model.Loans.Status;
import com.ikimina.ikiminaUssd.model.User;

@Repository
public interface LoanRepository extends JpaRepository<Loans, Long> {
    List<Loans> findByUser(User user);
    List<Loans> findByUserAndStatus(User user, Status status);
    List<Loans> findByUserAndRequestDateBetween(User user, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT SUM(l.amount) FROM Loans l WHERE l.user = ?1 AND l.status = ?2")
    Double sumAmountByUserAndStatus(User user, Status status);
    
    Long countByUserAndStatusAndApprovedDateAfter(User user, Status status, LocalDate date);
} 