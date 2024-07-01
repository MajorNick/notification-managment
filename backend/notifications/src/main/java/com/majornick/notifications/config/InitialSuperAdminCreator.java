package com.majornick.notifications.config;

import com.majornick.notifications.domain.User;
import com.majornick.notifications.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

//@Component
@RequiredArgsConstructor
public class InitialSuperAdminCreator implements CommandLineRunner {
    private final UserRepo userRepo;
    //   private final PasswordEncoder passwordEncoder;

    @Value("${initial.super_admin.username}")
    private String adminUsername;
    @Value("${initial.super_admin.password}")
    private String adminPassword;


    @Override
    public void run(String... args) {
        userRepo.save(User
                .builder()
           //     .password(passwordEncoder.encode(adminPassword))
                .username(adminUsername)
                .role("SUPER_ADMIN")
                .build());

    }

}
