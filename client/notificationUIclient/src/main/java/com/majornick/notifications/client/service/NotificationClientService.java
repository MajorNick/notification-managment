package com.majornick.notifications.client.service;

import com.majornick.notifications.client.dto.CustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationClientService {
    @Value("${notification.service.url}")
    private String notificationServiceUrl;

    private final RestTemplate restTemplate;

    public List<CustomerDTO> getAllCustomer() {

        ParameterizedTypeReference<List<CustomerDTO>> responseType = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<CustomerDTO>> responseEntity = restTemplate.exchange(
                notificationServiceUrl + "/api/customers",
                HttpMethod.GET,
                null,
                responseType
        );
        if (responseEntity.getStatusCode().isError()) {
            throw new RuntimeException("ERROR IN SENDING REQq: " + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }


    public CustomerDTO getCustomer(Long id) {

        ParameterizedTypeReference<CustomerDTO> responseType = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<CustomerDTO> responseEntity = restTemplate.exchange(
                String.format("%s/api/customers/%d", notificationServiceUrl, id),
                HttpMethod.GET,
                null,
                responseType
        );
        if (responseEntity.getStatusCode().isError()) {
            throw new RuntimeException("ERROR IN SENDING REQUEST: " + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }

    public void deleteCustomer(Long id) {
        ParameterizedTypeReference<CustomerDTO> responseType = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<CustomerDTO> responseEntity = restTemplate.exchange(
                String.format("%s/api/customers/%d", notificationServiceUrl, id),
                HttpMethod.DELETE,
                null,
                responseType
        );
        if (responseEntity.getStatusCode().isError()) {
            throw new RuntimeException("ERROR IN SENDING REQUEST: " + responseEntity.getStatusCode());
        }
    }

}
