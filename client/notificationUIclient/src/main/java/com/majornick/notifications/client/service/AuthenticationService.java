package com.majornick.notifications.client.service;

import com.majornick.notifications.client.dto.AuthenticationResponseDTO;
import com.majornick.notifications.client.dto.LoginFormDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    public void authenticate(LoginFormDTO loginForm, HttpServletResponse response) {
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
        System.out.println(responseEntity.getStatusCode());
        if (responseEntity.getStatusCode().isError()) {
            throw new RuntimeException("NOT AUTHENTICATED, WRONG USERNAME OR ADRRESS");
        } else {
            Cookie cookie = new Cookie("jwt_token", Objects.requireNonNull(responseEntity.getBody().getAccessToken()));
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
        }
    }

}
