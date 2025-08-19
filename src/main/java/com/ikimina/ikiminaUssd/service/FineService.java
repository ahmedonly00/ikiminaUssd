package com.ikimina.ikiminaUssd.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ikimina.ikiminaUssd.dto.FineDTO;
import com.ikimina.ikiminaUssd.model.Fines;
import com.ikimina.ikiminaUssd.model.User;
import com.ikimina.ikiminaUssd.repository.FineRepository;
import com.ikimina.ikiminaUssd.repository.UserRepository;

@Service
public class FineService {
    @Autowired
    private FineRepository fineRepository;
    
    @Autowired
    private UserRepository userRepository;

    public Fines createFine(FineDTO fineDTO, String adminPhoneNumber) {
        // Verify admin
        User admin = userRepository.findByPhoneNumber(adminPhoneNumber)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        
        if (admin.getRole() != User.Role.ADMIN) {
            throw new RuntimeException("Only admins can create fines");
        }

        // Get user to fine
        User user = userRepository.findByPhoneNumber(fineDTO.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Fines fine = new Fines();
        fine.setReason(fineDTO.getReason());
        fine.setAmount(fineDTO.getAmount());
        fine.setDate(LocalDate.now());
        fine.setUser(user);
        return fineRepository.save(fine);

    }

    public List<Fines> getUserFines(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return fineRepository.findByUser(user);
    }

    public List<Fines> getUserFinesByPeriod(String phoneNumber, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return fineRepository.findByUserAndDateBetween(user, startDate, endDate);
    }

    public Double getTotalFinesByUser(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Double total = fineRepository.sumAmountByUser(user);
        return total != null ? total : 0.0;
    }

    public Double getTotalFinesByDate(LocalDate date) {
        Double total = fineRepository.sumAmountByDate(date);
        return total != null ? total : 0.0;
    }
} 