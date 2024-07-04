package com.majornick.notifications.client.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.net.URI;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate() {
            @Override
            protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException {
                ClientHttpRequest clientHttpRequest = super.createRequest(url, method);
                clientHttpRequest.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    HttpSession session = attributes.getRequest().getSession(false);
                    if (session != null) {
                        String token = (String) session.getAttribute("jwtToken");
                        if (token != null) {
                            clientHttpRequest.getHeaders().setBearerAuth(token);
                        }
                    }
                }
                return clientHttpRequest;
            }
        };
    }


}


