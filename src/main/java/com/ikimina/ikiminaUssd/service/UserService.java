package com.ikimina.ikiminaUssd.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ikimina.ikiminaUssd.dto.UserDTO;
import com.ikimina.ikiminaUssd.model.Loans.Status;
import com.ikimina.ikiminaUssd.model.User;
import com.ikimina.ikiminaUssd.repository.FineRepository;
import com.ikimina.ikiminaUssd.repository.LoanRepository;
import com.ikimina.ikiminaUssd.repository.SavingsRepository;
import com.ikimina.ikiminaUssd.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SavingsRepository savingsRepository;
    
    @Autowired
    private LoanRepository loanRepository;
    
    @Autowired
    private FineRepository fineRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String phoneNumber, String fullName, String password, User.Role role) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new RuntimeException("Phone number already registered");
        }

        User user = new User();
        user.setPhoneNumber(phoneNumber);
        user.setFullName(fullName);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        return userRepository.save(user);
    }

    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public UserDTO getUserDetails(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Double totalSavings = savingsRepository.sumAmountByUser(user);
        Double totalLoans = loanRepository.sumAmountByUserAndStatus(user, Status.APPROVED);
        Double totalFines = fineRepository.sumAmountByUser(user);

        return new UserDTO(
            user.getPhoneNumber(),
            user.getFullName(),
            user.getRole(),
            totalSavings != null ? totalSavings : 0.0,
            totalLoans != null ? totalLoans : 0.0,
            totalFines != null ? totalFines : 0.0
        );
    }

    public boolean authenticate(String phoneNumber, String password) {
        Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);
        if (userOpt.isPresent()) {
            return passwordEncoder.matches(password, userOpt.get().getPassword());
        }
        return false;
    }
} 