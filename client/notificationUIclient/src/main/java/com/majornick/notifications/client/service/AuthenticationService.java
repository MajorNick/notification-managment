package com.majornick.notifications.client.service;

import com.majornick.notifications.client.dto.AuthenticationResponseDTO;
import com.majornick.notifications.client.dto.LoginFormDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RestTemplate restTemplate;
    @Value("${notification.service.url}")
    private String notificationServiceUrl;

    public void authenticate(LoginFormDTO loginForm, HttpSession session) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<?> requestEntity = new HttpEntity<>(loginForm, headers);
        ParameterizedTypeReference<AuthenticationResponseDTO> responseType = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<AuthenticationResponseDTO> responseEntity = restTemplate.exchange(
                notificationServiceUrl + "/api/auth/authenticate",
                HttpMethod.POST,
                requestEntity,
                responseType
        );
        if (responseEntity.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(401))) {
            throw new RuntimeException("NOT AUTHENTICATED, WRONG USERNAME OR ADRRESS");
        } else {
            session.setAttribute("jwtToken", Objects.requireNonNull(responseEntity.getBody()).getAccessToken());
        }
    }

}
