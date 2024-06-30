package com.majornick.notifications.controller;

import com.majornick.notifications.dto.UserDTO;
import com.majornick.notifications.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @PostMapping("/register")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody UserDTO userDTO) {
        adminService.registerAdmin(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin registered successfully!");
    }
}