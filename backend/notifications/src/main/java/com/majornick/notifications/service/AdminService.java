/*
package com.majornick.notifications.service;

import com.majornick.notifications.domain.User;
import com.majornick.notifications.dto.UserDTO;
import com.majornick.notifications.exception.EmptyLoginAttemptException;
import com.majornick.notifications.exception.InvalidLoginAttemptException;
import com.majornick.notifications.exception.UsernameAlreadyExistsException;
import com.majornick.notifications.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

//@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTGeneratorService jwtGenerator;
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

    public Map<String,String> loginUser(UserDTO userDTO) {
        if(userDTO.getUsername() == null || userDTO.getPassword() == null) {
            throw new EmptyLoginAttemptException("UserName or Password is Empty");
        }
        System.out.println(passwordEncoder.encode(userDTO.getPassword()));
        User user = userRepo.findByUsername(userDTO.getUsername())
                .orElseThrow(() ->new InvalidLoginAttemptException("username  is invalid"));
        if(!passwordEncoder.matches(userDTO.getPassword(),user.getPassword())){
            throw new InvalidLoginAttemptException("username  or password is invalid");
        }
        return jwtGenerator.generateToken(user);
    }
}
*/
