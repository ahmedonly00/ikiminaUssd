package com.ikimina.ikiminaUssd.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ikimina.ikiminaUssd.model.Fines;
import com.ikimina.ikiminaUssd.model.User;

@Repository
public interface FineRepository extends JpaRepository<Fines, Long> {
    List<Fines> findByUser(User user);
    List<Fines> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT SUM(f.amount) FROM Fines f WHERE f.user = ?1")
    Double sumAmountByUser(User user);
    
    @Query("SELECT SUM(f.amount) FROM Fines f WHERE f.date = ?1")
    Double sumAmountByDate(LocalDate date);
} 