package com.ikimina.ikiminaUssd.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikimina.ikiminaUssd.dto.SavingsDTO;
import com.ikimina.ikiminaUssd.model.Savings;
import com.ikimina.ikiminaUssd.model.User;
import com.ikimina.ikiminaUssd.repository.SavingsRepository;
import com.ikimina.ikiminaUssd.repository.UserRepository;

@Service
public class SavingsService {
    @Autowired
    private SavingsRepository savingsRepository;
    
    @Autowired
    private UserRepository userRepository;

    public Savings saveMoney(SavingsDTO savingsDTO) {
        User user = userRepository.findByPhoneNumber(savingsDTO.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Savings savings = new Savings();
        savings.setAmount(savingsDTO.getAmount());
        savings.setType(savingsDTO.getType());
        savings.setDate(LocalDate.now());
        savings.setUser(user);

        return savingsRepository.save(savings);
    }

    public List<Savings> getUserSavings(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return savingsRepository.findByUser(user);
    }

    public List<Savings> getUserSavingsByPeriod(String phoneNumber, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return savingsRepository.findByUserAndDateBetween(user, startDate, endDate);
    }

    public Double getTotalSavingsByUser(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Double total = savingsRepository.sumAmountByUser(user);
        return total != null ? total : 0.0;
    }

    public Double getTotalSavingsByDate(LocalDate date) {
        Double total = savingsRepository.sumAmountByDate(date);
        return total != null ? total : 0.0;
    }
} 