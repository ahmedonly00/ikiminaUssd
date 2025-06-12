package com.ikimina.ikiminaUssd.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ikimina.ikiminaUssd.model.Reports;
import com.ikimina.ikiminaUssd.model.User;

@Repository
public interface ReportsRepository extends JpaRepository<Reports, Long> {
    List<Reports> findByUser(User user);
    List<Reports> findByUserAndFromDateBetween(User user, LocalDate startDate, LocalDate endDate);
} 