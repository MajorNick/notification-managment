package com.majornick.notifications.service;

import com.majornick.notifications.domain.User;
import com.majornick.notifications.dto.AuthenticationRequestDTO;
import com.majornick.notifications.dto.AuthenticationResponseDTO;
import com.majornick.notifications.dto.UserDTO;
import com.majornick.notifications.exception.EmptyLoginAttemptException;
import com.majornick.notifications.exception.InvalidLoginAttemptException;
import com.majornick.notifications.exception.UsernameAlreadyExistsException;
import com.majornick.notifications.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO registerAdmin(UserDTO userDTO) {
        if (userRepo.existsByUsername(userDTO.getUsername())) {
            throw new UsernameAlreadyExistsException(String.format("User with username %s, already exists", userDTO.getUsername()));
        }
        User user = userRepo.save(User.builder()
                .role("ADMIN")
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build()
        );
        return AuthenticationResponseDTO
                .builder()
                .accessToken(jwtService.generateToken(user))
                .build();
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );
        User user = userRepo.findByUsername(request.getUsername()).orElseThrow(
                () ->  new UsernameAlreadyExistsException(String.format("User with username %s, already exists", request.getUsername()))
        );

        return AuthenticationResponseDTO
                .builder()
                .accessToken(jwtService.generateToken(user))
                .build();
    }
}
