package com.ikimina.ikiminaUssd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikimina.ikiminaUssd.model.User;
import com.ikimina.ikiminaUssd.service.UserService;

@RestController
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(
            @RequestParam String adminPhoneNumber,
            @RequestParam String adminPassword,
            @RequestParam String newAdminPhoneNumber,
            @RequestParam String newAdminFullName,
            @RequestParam String newAdminPassword) {
        try {
            // First verify the existing admin
            if (!userService.authenticate(adminPhoneNumber, adminPassword)) {
                return ResponseEntity.status(401).body("Invalid admin credentials");
            }
            
            // Check if the requesting user is an admin
            User admin = userService.findByPhoneNumber(adminPhoneNumber)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
                
            if (admin.getRole() != User.Role.ADMIN) {
                return ResponseEntity.status(403).body("Only admins can register new admins");
            }
            
            // Register the new admin
            User newAdmin = userService.registerUser(
                newAdminPhoneNumber,
                newAdminFullName,
                newAdminPassword,
                User.Role.ADMIN
            );
            
            return ResponseEntity.ok("Admin registered successfully with phone number: " + newAdmin.getPhoneNumber());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 