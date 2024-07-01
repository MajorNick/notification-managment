package com.majornick.notifications.service;

import com.majornick.notifications.domain.User;
import com.majornick.notifications.dto.UserDTO;
import com.majornick.notifications.exception.UsernameAlreadyExistsException;
import com.majornick.notifications.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public void registerAdmin(UserDTO userDTO) {
        if (userRepo.existsByUsername(userDTO.getUsername())) {
            throw new UsernameAlreadyExistsException(String.format("User with username %s, already exists", userDTO.getUsername()));
        }
        userRepo.save(User.builder()
                .role("ADMIN")
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build()
        );
    }
}
