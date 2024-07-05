package com.majornick.notifications.controller;

import com.majornick.notifications.dto.AuthenticationRequestDTO;
import com.majornick.notifications.dto.AuthenticationResponseDTO;
import com.majornick.notifications.dto.UserDTO;
import com.majornick.notifications.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for user authentication and registration")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new admin user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin user registered successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> registerAdmin(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerAdmin(userDTO));
    }

    @Operation(summary = "Authenticate a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponseDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> loginUser(@RequestBody AuthenticationRequestDTO request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}