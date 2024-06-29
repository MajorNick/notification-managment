package com.majornick.notifications.service;

import com.majornick.notifications.dto.CustomerDTO;
import com.majornick.notifications.exception.CustomerNotFoundException;
import com.majornick.notifications.mapper.CustomerMapper;
import com.majornick.notifications.repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;

    public List<CustomerDTO> getAllCustomer() {
        return customerRepo
                .findAll()
                .stream()
                .map(customerMapper::toDTO)
                .toList();
    }

    public CustomerDTO findById(Long id) {
        return customerRepo.findById(id)
                .map(customerMapper::toDTO)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("customer  with the id: %d not Found", id)));
    }

    public CustomerDTO save(CustomerDTO customerDto){
        return customerMapper.toDTO(customerRepo.save(customerMapper.toCustomer(customerDto)));
    }

}
