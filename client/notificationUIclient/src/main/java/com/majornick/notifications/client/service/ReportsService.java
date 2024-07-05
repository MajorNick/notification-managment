package com.majornick.notifications.client.service;

import com.majornick.notifications.client.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ReportsService {
    @Value("${notification.service.url}")
    private String notificationServiceUrl;

    private final RestTemplate restTemplate;
    public Map<String,Long> getNotificationStatusStatistics(){
        ParameterizedTypeReference<Map<String,Long>> responseType = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<Map<String,Long>> responseEntity = restTemplate.exchange(
                notificationServiceUrl + "/api/reports/notificationStatuses",
                HttpMethod.GET,
                null,
                responseType
        );
        if(responseEntity.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(401))){
            throw new UnauthorizedException("Not authorized");
        }
        if (responseEntity.getStatusCode().isError()) {
            throw new RuntimeException("ERROR IN SENDING REQUEST: " + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }
    public Map<String,Long> getPreferedNotificationStatistics(){
        ParameterizedTypeReference<Map<String,Long>> responseType = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<Map<String,Long>> responseEntity = restTemplate.exchange(
                notificationServiceUrl + "/api/reports/notificationPreferences",
                HttpMethod.GET,
                null,
                responseType
        );
        if(responseEntity.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(401))){
            throw new UnauthorizedException("Not authorized");
        }
        if (responseEntity.getStatusCode().isError()) {
            throw new RuntimeException("ERROR IN SENDING REQUEST: " + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }
}
