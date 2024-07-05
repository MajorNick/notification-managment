package com.majornick.notifications.service;

import com.majornick.notifications.domain.Address;
import com.majornick.notifications.domain.Customer;
import com.majornick.notifications.dto.AddressDTO;
import com.majornick.notifications.exception.CustomerNotFoundException;
import com.majornick.notifications.mapper.AddressMapper;
import com.majornick.notifications.repository.AddressRepo;
import com.majornick.notifications.repository.CustomerRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final CustomerRepo customerRepo;
    private final AddressRepo addressRepo;
    private final AddressMapper addressMapper;

    public List<AddressDTO> getCustomerAddresses(Long customerId) {
        Customer customer = findCustomerById(customerId);
        return customer.getAddresses().stream().map(addressMapper::toDto).toList();
    }

    @Transactional
    public AddressDTO addAddressToCustomer(Long customerId, AddressDTO addressDTO) {
        Customer customer = findCustomerById(customerId);
        Address address = addressMapper.toAddress(addressDTO);
        address.setCustomer(customer);
        return addressMapper.toDto(addressRepo.save(address));
    }

    private Customer findCustomerById(Long customerId) {
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Customer with id: %d not found", customerId)
                ));
    }
}
