package com.majornick.notifications.client.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(HttpServletRequest request) {
        return new RestTemplate() {
            @Override
            protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException {
                ClientHttpRequest clientHttpRequest = super.createRequest(url, method);
                String token = extractTokenFromCookie(request);
                clientHttpRequest.getHeaders().set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
                if (token != null) {
                    clientHttpRequest.getHeaders().setBearerAuth(token);

                }
                return clientHttpRequest;
            }
        };
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}


