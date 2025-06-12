package com.ikimina.ikiminaUssd.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ikimina.ikiminaUssd.model.Savings;
import com.ikimina.ikiminaUssd.model.User;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {
    List<Savings> findByUser(User user);
    List<Savings> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT SUM(s.amount) FROM Savings s WHERE s.user = ?1")
    Double sumAmountByUser(User user);
    
    @Query("SELECT SUM(s.amount) FROM Savings s WHERE s.date = ?1")
    Double sumAmountByDate(LocalDate date);
} 