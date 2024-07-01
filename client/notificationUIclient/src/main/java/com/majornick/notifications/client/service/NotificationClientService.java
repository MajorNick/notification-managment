package com.majornick.notifications.client.service;

import com.majornick.notifications.client.dto.CustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class NotificationClientService {
    @Value("${notification.service.url}")
    private String notificationServiceUrl;
    private final RestTemplate restTemplate;

    public List<CustomerDTO> getAllCustomer(){
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ParameterizedTypeReference<List<CustomerDTO>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<CustomerDTO>> responseEntity = restTemplate.exchange(
                notificationServiceUrl + "/api/customers",
                HttpMethod.GET,
                requestEntity,
                responseType
        );
        if(responseEntity.getStatusCode().isError()){
            throw  new RuntimeException("ERROR IN SENDING REQq: "+ responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }


    public CustomerDTO getCustomer(Long id){
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ParameterizedTypeReference<CustomerDTO> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<CustomerDTO> responseEntity = restTemplate.exchange(
                String.format("%s/api/customers/%d",notificationServiceUrl,id),
                HttpMethod.GET,
                requestEntity,
                responseType
        );
        if(responseEntity.getStatusCode().isError()){
            throw  new RuntimeException("ERROR IN SENDING REQUEST: "+ responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }

    public void deleteCustomer(Long id){
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ParameterizedTypeReference<CustomerDTO> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<CustomerDTO> responseEntity = restTemplate.exchange(
                String.format("%s/api/customers/%d",notificationServiceUrl,id),
                HttpMethod.DELETE,
                requestEntity,
                responseType
        );
        if(responseEntity.getStatusCode().isError()){
            throw  new RuntimeException("ERROR IN SENDING REQUEST: "+ responseEntity.getStatusCode());
        }
    }
}
