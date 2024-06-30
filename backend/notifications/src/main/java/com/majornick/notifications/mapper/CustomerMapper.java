package com.majornick.notifications.mapper;

import com.majornick.notifications.domain.Customer;
import com.majornick.notifications.dto.CustomerDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper {
    private final ModelMapper modelMapper;

    public Customer toCustomer(CustomerDTO customerDTO){
        return modelMapper.map(customerDTO,Customer.class);
    }
    public CustomerDTO toDTO(Customer customer){
        return modelMapper.map(customer,CustomerDTO.class);
    }
}
